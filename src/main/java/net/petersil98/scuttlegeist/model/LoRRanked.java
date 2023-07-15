package net.petersil98.scuttlegeist.model;

import com.fasterxml.jackson.databind.type.TypeFactory;
import net.petersil98.core.constant.Region;
import net.petersil98.scuttlegeist.http.LoRAPI;

import java.util.List;
import java.util.Map;

public class LoRRanked {

    public static List<RankEntry> getLeaderboard(Region region) {
        TypeFactory tf = TypeFactory.defaultInstance();
        Map<String, List<RankEntry>> leaderboard = LoRAPI.requestLorRankedEndpoint("leaderboards", "", region,
                tf.constructMapType(Map.class, tf.constructType(String.class), tf.constructCollectionType(List.class, RankEntry.class)));
        return leaderboard.get("players");
    }
}
