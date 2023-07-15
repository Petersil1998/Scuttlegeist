package net.petersil98.scuttlegeist.collection;

import net.petersil98.scuttlegeist.data.Set;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sets {

    private static Map<String, Set> sets;

    public static Set getSet(String id) {
        return sets.get(id);
    }

    public static List<Set> getSets() {
        return new ArrayList<>(sets.values());
    }
}
