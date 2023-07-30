package net.petersil98.scuttlegeist.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import net.petersil98.scuttlegeist.model.Deserializers;

import java.util.Objects;

@JsonDeserialize(using = Deserializers.SetDeserializer.class)
public class Set {

    @JsonProperty("nameRef")
    private final String id;
    private final String name;
    @JsonProperty("iconAbsolutePath")
    private final String image;

    public Set(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

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
