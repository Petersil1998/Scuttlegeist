package net.petersil98.scuttlegeist.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Region {

    @JsonProperty("nameRef")
    private String id;
    private String name;
    @JsonProperty("iconAbsolutePath")
    private String image;
    private String abbreviation;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
