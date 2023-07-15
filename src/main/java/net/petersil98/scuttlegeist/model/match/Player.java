package net.petersil98.scuttlegeist.model.match;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import net.petersil98.scuttlegeist.model.Deserializers;

import java.util.List;

@JsonDeserialize(using = Deserializers.PlayerDeserializer.class)
public class Player {

    private final String puuid;
    private final String deckId;
    private final String deckCode;
    private final List<String> factions;
    private final String gameOutcome;
    private final int orderOfPlay;

    public Player(String puuid, String deckId, String deckCode, List<String> factions, String gameOutcome, int orderOfPlay) {
        this.puuid = puuid;
        this.deckId = deckId;
        this.deckCode = deckCode;
        this.factions = factions;
        this.gameOutcome = gameOutcome;
        this.orderOfPlay = orderOfPlay;
    }

    public String getPuuid() {
        return puuid;
    }

    public String getDeckId() {
        return deckId;
    }

    public String getDeckCode() {
        return deckCode;
    }

    public List<String> getFactions() {
        return factions;
    }

    public String getGameOutcome() {
        return gameOutcome;
    }

    public int getOrderOfPlay() {
        return orderOfPlay;
    }
}
