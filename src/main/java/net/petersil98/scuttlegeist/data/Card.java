package net.petersil98.scuttlegeist.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import net.petersil98.scuttlegeist.collection.Cards;
import net.petersil98.scuttlegeist.model.Deserializers;
import net.petersil98.scuttlegeist.util.LoRLoader;

import java.util.List;

@JsonDeserialize(using = Deserializers.CardDeserializer.class)
public class Card {

    private final String id;
    private final String name;
    private List<Card> associatedCards;
    private final List<Asset> assets;
    private final List<Region> regions;
    private final int attack;
    private final int cost;
    private final int health;
    private final String description;
    private final String levelUpDescription;
    private final String flavorText;
    private final String artistName;
    private final List<Keyword> keywords;
    private final SpellSpeed spellSpeed;
    private final Rarity rarity;
    private final List<String> subtypes;
    private final String supertype;
    private final String type;
    private final boolean collectible;
    private final Set set;
    private final List<Format> formats;

    public Card(String id, String name, List<Asset> assets, List<Region> regions, int attack, int cost, int health, String description, String levelUpDescription, String flavorText, String artistName, List<Keyword> keywords, SpellSpeed spellSpeed, Rarity rarity, List<String> subtypes, String supertype, String type, boolean collectible, Set set, List<Format> formats) {
        this.id = id;
        this.name = name;
        this.assets = assets;
        this.regions = regions;
        this.attack = attack;
        this.cost = cost;
        this.health = health;
        this.description = description;
        this.levelUpDescription = levelUpDescription;
        this.flavorText = flavorText;
        this.artistName = artistName;
        this.keywords = keywords;
        this.spellSpeed = spellSpeed;
        this.rarity = rarity;
        this.subtypes = subtypes;
        this.supertype = supertype;
        this.type = type;
        this.collectible = collectible;
        this.set = set;
        this.formats = formats;
    }

    public void postInit() {
        if(LoRLoader.ASSOCIATED_CARDS.containsKey(id)) {
            this.associatedCards = LoRLoader.ASSOCIATED_CARDS.get(id).stream().map(Cards::getCard).toList();
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Card> getAssociatedCards() {
        return associatedCards;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public int getAttack() {
        return attack;
    }

    public int getCost() {
        return cost;
    }

    public int getHealth() {
        return health;
    }

    public String getDescription() {
        return description;
    }

    public String getLevelUpDescription() {
        return levelUpDescription;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public String getArtistName() {
        return artistName;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public SpellSpeed getSpellSpeed() {
        return spellSpeed;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public List<String> getSubtypes() {
        return subtypes;
    }

    public String getSupertype() {
        return supertype;
    }

    public String getType() {
        return type;
    }

    public boolean isCollectible() {
        return collectible;
    }

    public Set getSet() {
        return set;
    }

    public List<Format> getFormats() {
        return formats;
    }

    public static class Asset {
        @JsonProperty("gameAbsolutePath")
        private String image;
        @JsonProperty("fullAbsolutePath")
        private String fullImage;

        public String getImage() {
            return image;
        }

        public String getFullImage() {
            return fullImage;
        }
    }
}
