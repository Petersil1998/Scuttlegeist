package net.petersil98.scuttlegeist.model.match;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.petersil98.core.constant.Region;
import net.petersil98.scuttlegeist.http.LoRAPI;
import net.petersil98.scuttlegeist.model.Deserializers;

import java.util.List;

@JsonDeserialize(using = Deserializers.MatchDetailsDeserializer.class)
public class MatchDetails {

    private final String gameMode;
    private final String gameType;
    private final long gameStartTime;
    private final String gameVersion;
    private final int totalTurnCount;
    private final List<Player> players;

    public MatchDetails(String gameMode, String gameType, long gameStartTime, String gameVersion, int totalTurnCount, List<Player> players) {
        this.gameMode = gameMode;
        this.gameType = gameType;
        this.gameStartTime = gameStartTime;
        this.gameVersion = gameVersion;
        this.totalTurnCount = totalTurnCount;
        this.players = players;
    }

    public static MatchDetails getMatchDetails(String matchId, Region region) {
        return LoRAPI.requestLorMatchEndpoint("matches/", matchId, region, MatchDetails.class);
    }

    public static List<MatchDetails> getMatchHistory(String puuid, Region region) {
        List<String> matchIds = LoRAPI.requestLorMatchEndpoint("matches/by-puuid/", puuid + "/ids", region, TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));
        return matchIds.stream().map(matchId -> MatchDetails.getMatchDetails(matchId, region)).toList();
    }

    public String getGameMode() {
        return gameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public long getGameStartTime() {
        return gameStartTime;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public int getTotalTurnCount() {
        return totalTurnCount;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
