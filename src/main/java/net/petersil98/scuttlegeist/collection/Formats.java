package net.petersil98.scuttlegeist.collection;

import net.petersil98.scuttlegeist.data.Format;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Formats {

    private static Map<String, Format> formats;

    public static Format getFormat(String id) {
        return formats.get(id);
    }

    public static List<Format> getFormats() {
        return new ArrayList<>(formats.values());
    }
}
