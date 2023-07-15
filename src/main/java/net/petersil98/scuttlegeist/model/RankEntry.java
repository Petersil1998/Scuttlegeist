package net.petersil98.scuttlegeist.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RankEntry {

    @JsonProperty("name")
    private String playerName;
    private int rank;
    private int lp;

    public String getPlayerName() {
        return playerName;
    }

    public int getRank() {
        return rank;
    }

    public int getLp() {
        return lp;
    }

    @Override
    public String toString() {
        return String.format("%s [%d]: %dLP", playerName, rank, lp);
    }
}
