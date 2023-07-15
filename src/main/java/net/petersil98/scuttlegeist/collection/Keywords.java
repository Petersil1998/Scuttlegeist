package net.petersil98.scuttlegeist.collection;

import net.petersil98.scuttlegeist.data.Keyword;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Keywords {

    private static Map<String, Keyword> keywords;

    public static Keyword getKeyword(String id) {
        return keywords.get(id);
    }

    public static List<Keyword> getKeywords() {
        return new ArrayList<>(keywords.values());
    }
}
