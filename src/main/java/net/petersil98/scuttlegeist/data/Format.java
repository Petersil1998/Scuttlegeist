package net.petersil98.scuttlegeist.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Format {

    @JsonProperty("nameRef")
    private String id;
    private String name;
    @JsonProperty("iconAbsolutePath")
    private String image;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
