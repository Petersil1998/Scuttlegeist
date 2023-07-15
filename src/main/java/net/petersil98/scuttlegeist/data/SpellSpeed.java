package net.petersil98.scuttlegeist.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpellSpeed {

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
