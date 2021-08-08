package com.tochie.ussdexample.resource;

import com.tochie.ussdexample.services.UssdRoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/rest/ussd")
public class RootController {

    /**
     * @param text: This shows the user input. It is an empty string in the first notification of a session. After that, it concatenates all the user input within the session with a "*" until the session ends
     * @param sessionId: A unique value generated when the session starts and sent every time a mobile subscriber response has been received.
     * @param serviceCode: This is the USSD code assigned to your application
     * @param phoneNumber: The number of mobile subscribers interacting with your ussd application.
     * @throws IOException
     * @return
     **/

    @Autowired
    UssdRoutingService ussdRoutingService;

    @ResponseBody
    @PostMapping("/ussd")
    public String ussdIngress(@RequestParam String sessionId, @RequestParam String serviceCode,
                              @RequestParam String phoneNumber, @RequestParam String text) throws IOException {
        try {
            return ussdRoutingService.menuLevelRouter(sessionId, serviceCode, phoneNumber, text);
        } catch (IOException e) {
            //Gracefully shut down in case of error
            return "END Service is temporarily down";
        }
    }


}
