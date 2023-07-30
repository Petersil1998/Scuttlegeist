package net.petersil98.scuttlegeist.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.petersil98.scuttlegeist.collection.*;
import net.petersil98.scuttlegeist.data.*;
import net.petersil98.scuttlegeist.model.match.MatchDetails;
import net.petersil98.scuttlegeist.model.match.Player;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.StreamSupport;

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

    public static class CardDeserializer extends JsonDeserializer<Card> {

        @Override
        public Card deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode root = jp.getCodec().readTree(jp);

            List<Card.Asset> assets = MAPPER.readerForListOf(Card.Asset.class).readValue(root.get("assets"));
            List<Region> regions = StreamSupport.stream(root.get("regionRefs").spliterator(), false)
                    .map(JsonNode::asText).map(Regions::getRegion).toList();
            List<Keyword> keywords = StreamSupport.stream(root.get("keywordRefs").spliterator(), false)
                    .map(JsonNode::asText).map(Keywords::getKeyword).toList();
            List<Format> formats = null;
            if(root.has("formatRefs"))
                formats = StreamSupport.stream(root.get("formatRefs").spliterator(), false)
                        .map(JsonNode::asText).map(Formats::getFormat).toList();
            return new Card( root.get("cardCode").asText(), root.get("name").asText(), assets, regions, root.get("attack").asInt(),
                    root.get("cost").asInt(), root.get("health").asInt(), root.get("descriptionRaw").asText(),
                    root.get("levelupDescriptionRaw").asText(), root.get("flavorText").asText(), root.get("artistName").asText(),
                    keywords, SpellSpeeds.getSpellSpeed(root.get("spellSpeedRef").asText()),
                    Rarities.getRarity(root.get("rarityRef").asText()),
                    MAPPER.readerForListOf(String.class).readValue(root.get("subtypes")),
                    root.get("supertype").asText(), root.get("type").asText(), root.get("collectible").asBoolean(),
                    Sets.getSet(root.get("set").asText()), formats);
        }
    }

    public static class SetDeserializer extends JsonDeserializer<Set> {

        @Override
        public Set deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonNode root = jp.getCodec().readTree(jp);

            return new Set(root.get("nameRef").asText(), root.get("name").asText(), root.get("iconAbsolutePath").asText().replace("_crispmip", ""));
        }
    }
}
