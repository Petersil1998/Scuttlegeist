package net.petersil98.scuttlegeist.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Set {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Set set = (Set) o;
        return Objects.equals(id, set.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
