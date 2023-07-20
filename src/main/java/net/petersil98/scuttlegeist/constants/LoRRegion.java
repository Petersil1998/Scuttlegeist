package net.petersil98.scuttlegeist.constants;

import net.petersil98.core.constant.Region;

public class LoRRegion extends Region {
    public static final LoRRegion AMERICA = new LoRRegion("americas");
    public static final LoRRegion EUROPE = new LoRRegion("europe");
    public static final LoRRegion APAC = new LoRRegion("asia");
    public static final LoRRegion SEA = new LoRRegion("sea");

    protected LoRRegion(String name) {
        super(name);
    }
}
