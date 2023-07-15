package net.petersil98.scuttlegeist.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VocabTerm {

    @JsonProperty("nameRef")
    private String id;
    private String name;
    private String description;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
