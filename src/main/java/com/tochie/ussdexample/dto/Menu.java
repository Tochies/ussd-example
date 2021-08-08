package com.tochie.ussdexample.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class Menu {
    @JsonProperty("id")
    private String id; // id of Record as stored in the database or whatever Datastore used

    @JsonProperty("menu_level")
    private String menuLevel; // Menu Level unique identifier

    @JsonProperty("text")
    private String text; // Text to be return for the exact response

    @JsonProperty("menu_options")
    private List<MenuOption> menuOptions;// Options available for this Menu Level

    @JsonProperty("action")
    private String action;

    @JsonProperty("max_selections")
    private Integer maxSelections;// Max selection identifier to enable diferentiation between value provided and option selections i.e if request data is greater than maxSelections then it is determined that that is an input.
}
