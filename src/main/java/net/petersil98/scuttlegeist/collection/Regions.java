package net.petersil98.scuttlegeist.collection;

import net.petersil98.scuttlegeist.data.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Regions {

    private static Map<String, Region> regions;

    public static Region getRegion(String id) {
        return regions.get(id);
    }

    public static List<Region> getRegions() {
        return new ArrayList<>(regions.values());
    }
}
