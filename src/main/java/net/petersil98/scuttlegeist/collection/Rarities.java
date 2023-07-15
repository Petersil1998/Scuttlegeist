package net.petersil98.scuttlegeist.collection;

import net.petersil98.scuttlegeist.data.Rarity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Rarities {

    private static Map<String, Rarity> rarities;

    public static Rarity getRarity(String id) {
        return rarities.get(id);
    }

    public static List<Rarity> getRarities() {
        return new ArrayList<>(rarities.values());
    }
}
