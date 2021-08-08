package com.tochie.ussdexample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tochie.ussdexample.enums.MenuOptionAction;
import lombok.Data;

@Data
public class MenuOption {

    private String type;// helps to determine if the next step should return a response or serve a different menu

    private String response;// response text to be returned

    @JsonProperty("next_menu_level")
    private String nextMenuLevel;// next Menu to be displayed if  the type should return a menu

    private MenuOptionAction action;// action router .i.e. What process should be performed to retrieve the correct set of data for display
}
