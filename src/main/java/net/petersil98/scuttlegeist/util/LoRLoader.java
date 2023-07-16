package net.petersil98.scuttlegeist.util;

import com.fasterxml.jackson.databind.JsonNode;
import net.petersil98.core.util.Loader;
import net.petersil98.core.util.settings.Language;
import net.petersil98.core.util.settings.Settings;
import net.petersil98.scuttlegeist.Scuttlegeist;
import net.petersil98.scuttlegeist.collection.*;
import net.petersil98.scuttlegeist.constants.LoRConstants;
import net.petersil98.scuttlegeist.data.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static net.petersil98.core.Core.MAPPER;

public class LoRLoader extends Loader {

    private static final Pattern PATTERN = Pattern.compile("https?://dd.b.pvp.net/(\\w+)/*");

    public static java.util.Map<String, List<String>> ASSOCIATED_CARDS = new HashMap<>();

    private static String latestDDragonVersion;
    private static Language usedLanguage;

    @Override
    protected void load() {
        if(latestDDragonVersion == null) loadLatestVersions();
        usedLanguage = getLanguageToUse();
        loadVocabTerms();
        loadKeywords();
        loadRegions();
        loadSpellSpeeds();
        loadRarities();
        loadSets();
        loadFormats();
        loadCards();
    }

    @Override
    protected boolean shouldReloadData() {
        if(usedLanguage != getLanguageToUse()) return true;
        String globalsUrl = LoRConstants.DDRAGON_LOR_BASE_PATH + "latest/core/en_us/data/globals-en_us.json";
        try(InputStream lorVersion = URI.create(globalsUrl).toURL().openConnection().getInputStream()) {
            JsonNode root = MAPPER.readTree(lorVersion);
            Matcher matcher = PATTERN.matcher(root.get("regions").get(0).get("iconAbsolutePath").asText());
            if(matcher.find()) LoRConstants.DDRAGON_LOR_VERSION = matcher.group(1);
            if(!latestDDragonVersion.equals(LoRConstants.DDRAGON_LOR_VERSION)) {
                latestDDragonVersion = LoRConstants.DDRAGON_LOR_VERSION;
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private Language getLanguageToUse() {
        return Settings.getLanguage().isAvailableForLor() ? Settings.getLanguage() : Language.EN_US;
    }

    private static void loadLatestVersions() {
        String globalsUrl = LoRConstants.DDRAGON_LOR_BASE_PATH + "latest/core/en_us/data/globals-en_us.json";
        try(InputStream lorVersion = URI.create(globalsUrl).toURL().openConnection().getInputStream()) {
            JsonNode root = MAPPER.readTree(lorVersion);
            Matcher matcher = PATTERN.matcher(root.get("regions").get(0).get("iconAbsolutePath").asText());
            if(matcher.find()) LoRConstants.DDRAGON_LOR_VERSION = matcher.group(1);
            latestDDragonVersion = LoRConstants.DDRAGON_LOR_VERSION;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadVocabTerms() {
        String lang = usedLanguage.toString().toLowerCase();
        try(InputStream in = new URI(String.format("%s%s/core/%s/data/globals-%s.json", LoRConstants.DDRAGON_LOR_BASE_PATH, LoRConstants.DDRAGON_LOR_VERSION, lang, lang)).toURL().openStream()) {
            List<VocabTerm> vocabTerms = MAPPER.readerForListOf(VocabTerm.class).readValue(MAPPER.readTree(in).get("vocabTerms"));
            setFieldInCollection(VocabTerms.class, vocabTerms.stream().collect(Collectors.toMap(VocabTerm::getId, vocabTerm -> vocabTerm)));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void loadKeywords() {
        String lang = usedLanguage.toString().toLowerCase();
        try(InputStream in = new URI(String.format("%s%s/core/%s/data/globals-%s.json", LoRConstants.DDRAGON_LOR_BASE_PATH, LoRConstants.DDRAGON_LOR_VERSION, lang, lang)).toURL().openStream()) {
            List<Keyword> keywords = MAPPER.readerForListOf(Keyword.class).readValue(MAPPER.readTree(in).get("keywords"));
            setFieldInCollection(Keywords.class, keywords.stream().collect(Collectors.toMap(Keyword::getId, keyword -> keyword)));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void loadRegions() {
        String lang = usedLanguage.toString().toLowerCase();
        try(InputStream in = new URI(String.format("%s%s/core/%s/data/globals-%s.json", LoRConstants.DDRAGON_LOR_BASE_PATH, LoRConstants.DDRAGON_LOR_VERSION, lang, lang)).toURL().openStream()) {
            List<Region> regions = MAPPER.readerForListOf(Region.class).readValue(MAPPER.readTree(in).get("regions"));
            setFieldInCollection(Regions.class, regions.stream().collect(Collectors.toMap(Region::getId, region -> region)));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void loadSpellSpeeds() {
        String lang = usedLanguage.toString().toLowerCase();
        try(InputStream in = new URI(String.format("%s%s/core/%s/data/globals-%s.json", LoRConstants.DDRAGON_LOR_BASE_PATH, LoRConstants.DDRAGON_LOR_VERSION, lang, lang)).toURL().openStream()) {
            List<SpellSpeed> spellSpeeds = MAPPER.readerForListOf(SpellSpeed.class).readValue(MAPPER.readTree(in).get("spellSpeeds"));
            setFieldInCollection(SpellSpeeds.class, spellSpeeds.stream().collect(Collectors.toMap(SpellSpeed::getId, spellSpeed -> spellSpeed)));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void loadRarities() {
        String lang = usedLanguage.toString().toLowerCase();
        try(InputStream in = new URI(String.format("%s%s/core/%s/data/globals-%s.json", LoRConstants.DDRAGON_LOR_BASE_PATH, LoRConstants.DDRAGON_LOR_VERSION, lang, lang)).toURL().openStream()) {
            List<Rarity> rarities = MAPPER.readerForListOf(Rarity.class).readValue(MAPPER.readTree(in).get("rarities"));
            setFieldInCollection(Rarities.class, rarities.stream().collect(Collectors.toMap(Rarity::getId, rarity -> rarity)));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void loadSets() {
        String lang = usedLanguage.toString().toLowerCase();
        try(InputStream in = new URI(String.format("%s%s/core/%s/data/globals-%s.json", LoRConstants.DDRAGON_LOR_BASE_PATH, LoRConstants.DDRAGON_LOR_VERSION, lang, lang)).toURL().openStream()) {
            List<Set> sets = MAPPER.readerForListOf(Set.class).readValue(MAPPER.readTree(in).get("sets"));
            setFieldInCollection(Sets.class, sets.stream().collect(Collectors.toMap(Set::getId, set -> set)));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void loadFormats() {
        String lang = usedLanguage.toString().toLowerCase();
        try(InputStream in = new URI(String.format("%s%s/core/%s/data/globals-%s.json", LoRConstants.DDRAGON_LOR_BASE_PATH, LoRConstants.DDRAGON_LOR_VERSION, lang, lang)).toURL().openStream()) {
            List<Format> formats = MAPPER.readerForListOf(Format.class).readValue(MAPPER.readTree(in).get("formats"));
            setFieldInCollection(Formats.class, formats.stream().collect(Collectors.toMap(Format::getId, format -> format)));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void loadCards() {
        String lang = usedLanguage.toString().toLowerCase();
        ASSOCIATED_CARDS.clear();
        Map<String, Card> cards = new HashMap<>();
        for(Set set: Sets.getSets()) {
            if(set.getId().contains("Event")) continue;
            String setName = set.getId().toLowerCase();
            try(InputStream in = new URI(String.format("%s%s/%s/%s/data/%s-%s.json", LoRConstants.DDRAGON_LOR_BASE_PATH, LoRConstants.DDRAGON_LOR_VERSION, setName, lang, setName, lang)).toURL().openStream()) {
                for (JsonNode cardNode: MAPPER.readTree(in)) {
                    Card card = MAPPER.readerFor(Card.class).readValue(cardNode);
                    cards.put(card.getId(), card);
                    ASSOCIATED_CARDS.put(card.getId(), MAPPER.readerForListOf(String.class).readValue(cardNode.get("associatedCardRefs")));
                }
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
        setFieldInCollection(Cards.class, cards);
        Cards.getCards().forEach(Card::postInit);
    }

    private void setFieldInCollection(Class<?> collectionClass, Map<?, ?> elements) {
        try {
            char[] fieldName = collectionClass.getSimpleName().toCharArray();
            fieldName[0] += 32;
            Field field = collectionClass.getDeclaredField(new String(fieldName));
            field.setAccessible(true);
            field.set(null, elements);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Scuttlegeist.LOGGER.error("Couldn't set collection Type of class " + collectionClass.getSimpleName(), e);
        }
    }
}
