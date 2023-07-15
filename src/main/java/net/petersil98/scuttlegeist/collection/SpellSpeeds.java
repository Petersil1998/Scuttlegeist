package net.petersil98.scuttlegeist.collection;

import net.petersil98.scuttlegeist.data.SpellSpeed;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpellSpeeds {

    private static Map<String, SpellSpeed> spellSpeeds;

    public static SpellSpeed getSpellSpeed(String id) {
        return spellSpeeds.get(id);
    }

    public static List<SpellSpeed> getSpellSpeeds() {
        return new ArrayList<>(spellSpeeds.values());
    }
}
