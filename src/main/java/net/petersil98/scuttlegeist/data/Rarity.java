package net.petersil98.scuttlegeist.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rarity {

    @JsonProperty("nameRef")
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
