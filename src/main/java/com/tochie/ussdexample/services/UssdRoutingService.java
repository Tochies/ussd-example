package com.tochie.ussdexample.services;

import com.tochie.ussdexample.dto.Menu;
import com.tochie.ussdexample.dto.MenuOption;
import com.tochie.ussdexample.enums.MenuOptionAction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UssdRoutingService {
    @Autowired
    private MenuService menuService;

    @Autowired
    private SessionService sessionService;

    /**
     *
     * @param sessionId
     * @param serviceCode
     * @param phoneNumber
     * @param text
     * @return
     * @throws IOException
     */
    public String menuLevelRouter(String sessionId, String serviceCode, String phoneNumber, String text)
            throws IOException {
        Map<String, Menu> menus = menuService.loadMenus();
        UssdSession session = checkAndSetSession(sessionId, serviceCode, phoneNumber, text);
        /**
         * Check if response has some value
         */
        if (text.length() > 0) {
            return getNextMenuItem(session, menus);
        } else {
            return menus.get(session.getCurrentMenuLevel()).getText();
        }
    }

    /**
     *
     * @param session
     * @param menus
     * @return
     * @throws IOException
     */
    public String getNextMenuItem(UssdSession session, Map<String, Menu> menus) throws IOException {
        String[] levels = session.getText().split("\\*");
        String lastValue = levels[levels.length - 1];
        Menu menuLevel = menus.get(session.getCurrentMenuLevel());

        if (Integer.parseInt(lastValue) <= menuLevel.getMaxSelections()) {
            MenuOption menuOption = menuLevel.getMenuOptions().get(Integer.parseInt(lastValue) - 1);
            return processMenuOption(session, menuOption);
        }

        return "CON ";
    }

    public String getMenu(String menuLevel) throws IOException {
        Map<String, Menu> menus = menuService.loadMenus();
        return menus.get(menuLevel).getText();
    }

    public String processMenuOption(UssdSession session, MenuOption menuOption) throws IOException {
        if (menuOption.getType().equals("response")) {
            return processMenuOptionResponses(menuOption);
        } else if (menuOption.getType().equals("level")) {
            updateSessionMenuLevel(session, menuOption.getNextMenuLevel());
            return getMenu(menuOption.getNextMenuLevel());
        } else {
            return "CON ";
        }
    }

    public String processMenuOptionResponses(MenuOption menuOption) {
        String response = menuOption.getResponse();
        Map<String, String> variablesMap = new HashMap<>();

        if (menuOption.getAction() == MenuOptionAction.PROCESS_ACC_BALANCE) {
            variablesMap.put("account_balance", "10000");
            response = replaceVariable(variablesMap, response);
        } else if (menuOption.getAction() == MenuOptionAction.PROCESS_ACC_NUMBER) {
            variablesMap.put("account_number", "123412512");
            response = replaceVariable(variablesMap, response);
        } else if (menuOption.getAction() == MenuOptionAction.PROCESS_ACC_PHONE_NUMBER) {
            variablesMap.put("phone_number", "254702759950");
            response = replaceVariable(variablesMap, response);
        }

        return response;
    }

    public String replaceVariable(Map<String, String> variablesMap,
                                  String response) {
        StringSubstitutor sub = new StringSubstitutor(variablesMap);
        return sub.replace(response);
    }

    public UssdSession updateSessionMenuLevel(UssdSession session,
                                              String menuLevel) {
        session.setPreviousMenuLevel(session.getCurrentMenuLevel());
        session.setCurrentMenuLevel(menuLevel);
        return sessionService.update(session);
    }

    public UssdSession checkAndSetSession(String sessionId, String
            serviceCode, String phoneNumber, String text) {
        UssdSession session = sessionService.get(sessionId);
        log.error("Existing session id is : " + session);

        if (session != null) {
            session.setText(text);
            return sessionService.update(session);
        }

        session = new UssdSession();
        session.setCurrentMenuLevel("1");
        session.setPreviousMenuLevel("1");
        session.setId(sessionId);
        session.setPhoneNumber(phoneNumber);
        session.setServiceCode(serviceCode);
        session.setText(text);

        return sessionService.createUssdSession(session);
    }

}
