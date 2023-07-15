package net.petersil98.scuttlegeist.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.petersil98.scuttlegeist.model.match.MatchDetails;
import net.petersil98.scuttlegeist.model.match.Player;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

public class Deserializers {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static class MatchDetailsDeserializer extends JsonDeserializer<MatchDetails> {

        @Override
        public MatchDetails deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode root = jp.getCodec().readTree(jp);
            JsonNode info = root.get("info");
            long startTimestamp = Instant.parse(info.get("game_start_time_utc").asText()).getEpochSecond();
            List<Player> players = MAPPER.readerForListOf(Player.class).readValue(info.get("players"));
            return new MatchDetails(info.get("game_mode").asText(), info.get("game_type").asText(), startTimestamp,
                    info.get("game_version").asText(), info.get("total_turn_count").asInt(), players);
        }
    }

    public static class PlayerDeserializer extends JsonDeserializer<Player> {

        @Override
        public Player deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode root = jp.getCodec().readTree(jp);
            List<String> factions = MAPPER.readerForListOf(String.class).readValue(root.get("factions"));
            return new Player(root.get("puuid").asText(), root.get("deck_id").asText(), root.get("deck_code").asText(),
                    factions, root.get("game_outcome").asText(), root.get("order_of_play").asInt());
        }
    }
}
