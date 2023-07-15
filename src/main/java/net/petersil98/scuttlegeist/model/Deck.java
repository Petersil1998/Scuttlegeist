package net.petersil98.scuttlegeist.model;

import net.petersil98.scuttlegeist.collection.Cards;
import net.petersil98.scuttlegeist.data.Card;
import net.petersil98.scuttlegeist.util.Base32;
import net.petersil98.scuttlegeist.util.Utils;
import net.petersil98.scuttlegeist.util.VarInt;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Deck {

    private static final int MAX_KNOWN_VERSION = 5;
    private static final Map<Integer, String> ID_TO_FACTION_CODE = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(0, "DE"),
            new AbstractMap.SimpleEntry<>(1, "FR"),
            new AbstractMap.SimpleEntry<>(2, "IO"),
            new AbstractMap.SimpleEntry<>(3, "NX"),
            new AbstractMap.SimpleEntry<>(4, "PZ"),
            new AbstractMap.SimpleEntry<>(5, "SI"),
            new AbstractMap.SimpleEntry<>(6, "BW"),
            new AbstractMap.SimpleEntry<>(7, "SH"),
            new AbstractMap.SimpleEntry<>(9, "MT"),
            new AbstractMap.SimpleEntry<>(10, "BC"),
            new AbstractMap.SimpleEntry<>(12, "RU")
    );

    public static String encodeDeck(Map<Card, Integer> deck) {
        List<Integer> result = new ArrayList<>();

        if (deck.containsKey(null) || deck.values().stream().anyMatch(integer -> integer < 1))
            throw new IllegalArgumentException("The provided deck contains non-positive card counts");

        int format = 1;
        int version = deck.keySet().stream()
                .flatMapToInt(card -> card.getRegions().stream().mapToInt(region -> Faction.getFactionByName(region.getAbbreviation()).version))
                .max().orElse(1);
        result.add(format << 4 | (version & 0xF));

        Map<Integer, List<Card>> groupedCount = deck.entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));

        List<List<Card>> groupedOf3s = groupAndSort(groupedCount.remove(3));
        List<List<Card>> groupedOf2s = groupAndSort(groupedCount.remove(2));
        List<List<Card>> groupedOf1s = groupAndSort(groupedCount.remove(1));

        result.addAll(encodeGroup(groupedOf3s));
        result.addAll(encodeGroup(groupedOf2s));
        result.addAll(encodeGroup(groupedOf1s));
        result.addAll(encodeNofs(groupedCount));

        byte[] bytes = new byte[result.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = result.get(i).byteValue();
        }
        return Base32.encode(bytes);
    }

    public static String encodeDeck(List<Card> cards) {
        return encodeDeck(cards.stream().collect(Collectors.groupingBy(card -> card, Collectors.summingInt(c -> 1))));
    }

    public static Map<Card, Integer> decodeDeck(String encodedDeck) {
        HashMap<Card, Integer> cards = new HashMap<>();
        byte[] bytes;
        try {
            bytes = Base32.decode(encodedDeck);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid deck code");
        }

        List<Byte> byteList = IntStream.range(0, bytes.length).mapToObj(i -> bytes[i]).collect(Collectors.toList());

        //grab format and version
        int format = bytes[0] >> 4;
        int version = bytes[0] & 0xF;
        byteList.remove(0);

        if (version > MAX_KNOWN_VERSION) {
            throw new IllegalArgumentException("The provided code requires a higher version of this library. Please update");
        }

        for (int size = 3; size > 0; size--) {
            int groups = VarInt.pop(byteList);

            for (int j = 0; j < groups; j++) {
                int numOfsInThisGroup = VarInt.pop(byteList);
                int set = VarInt.pop(byteList);
                int faction = VarInt.pop(byteList);

                for (int k = 0; k < numOfsInThisGroup; k++) {
                    int card = VarInt.pop(byteList);
                    cards.put(getCardFromCode(set, faction, card), size);
                }
            }
        }

        while (byteList.size() > 0) {
            int fourPlusCount = VarInt.pop(byteList);
            int fourPlusSet = VarInt.pop(byteList);
            int fourPlusFaction = VarInt.pop(byteList);
            int fourPlusCard = VarInt.pop(byteList);

            cards.put(getCardFromCode(fourPlusSet, fourPlusFaction, fourPlusCard), fourPlusCount);
        }
        return cards;
    }

    private static List<List<Card>> groupAndSort(List<Card> cards) {
        List<List<Card>> result = new ArrayList<>();
        while (cards.size() > 0) {
            List<Card> currentSet = new ArrayList<>();

            Card firstCard = cards.remove(0);
            String firstFaction = firstCard.getId().substring(2,4);
            currentSet.add(firstCard);

            for (int i = cards.size() - 1; i >= 0; i--) {
                Card currentCard = cards.get(i);
                String faction = currentCard.getId().substring(2,4);

                if (firstCard.getSet().equals(currentCard.getSet()) && firstFaction.equals(faction)) {
                    currentSet.add(currentCard);
                    cards.remove(i);
                }
            }
            result.add(currentSet);
        }

        Comparator<List<Card>> c  = Comparator.comparing(List::size);
        Comparator<List<Card>> c2 = Comparator.comparing((List<Card> card) -> card.get(0).getId());
        result.sort(c.thenComparing(c2));
        for (List<Card> group : result) {
            group.sort(Comparator.comparing(Card::getId));
        }

        return result;
    }

    private static List<Integer> encodeGroup(List<List<Card>> group) {
        List<Integer> result = new ArrayList<>(VarInt.get(group.size()));

        for (List<Card> list : group) {
            result.addAll(VarInt.get(list.size()));

            Card firstCard = list.get(0);
            Faction faction = Faction.getFactionByName(firstCard.getId().substring(2,4));
            result.addAll(VarInt.get(Integer.parseInt(firstCard.getId().substring(0,2))));
            result.addAll(VarInt.get(faction.getId()));

            for (Card card: list) {
                result.addAll(VarInt.get(Integer.parseInt(card.getId().substring(4))));
            }
        }

        return result;
    }

    private static List<Integer> encodeNofs(Map<Integer, List<Card>> nOfs) {
        List<Integer> result = new ArrayList<>();

        for (Map.Entry<Integer, List<Card>> cc : nOfs.entrySet()) {
            for (Card card: cc.getValue()) {
                result.addAll(VarInt.get(cc.getKey()));
                result.addAll(VarInt.get(Integer.parseInt(card.getId().substring(0, 2))));
                result.addAll(VarInt.get(Faction.getFactionByName(card.getId().substring(2, 4)).id));
                result.addAll(VarInt.get(Integer.parseInt(card.getId().substring(4))));
            }
        }

        return result;
    }

    private static Card getCardFromCode(int set, int faction, int card) {
        String setString = Utils.padLeft('0', 2, Integer.toString(set));
        String factionString = ID_TO_FACTION_CODE.get(faction);
        String cardString = Utils.padLeft('0', 3, Integer.toString(card));
        return Cards.getCard(setString + factionString + cardString);
    }

    private enum Faction {
        DEMACIA("DE", 0, 1),
        FRELJORD("FR", 1, 1),
        IONIA("IO", 2, 1),
        NOXUS("NX", 3, 1),
        PILTOVER_AND_ZAUN("PZ", 4, 1),
        SHADOW_ILES("SI", 5, 1),
        BILGEWATER("BW", 6, 2),
        MOUNT_TARGON("MT", 9, 2),
        SHURIMA("SH", 7, 3),
        BANDLE_CITY("BC", 10, 4),
        RUNETERRA("RU", 12, 5);

        final String name;
        final int id;
        final int version;

        Faction(String name, int id, int version) {
            this.name = name;
            this.id = id;
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public int getVersion() {
            return version;
        }

        public static Faction getFactionByName(String name) {
            return Arrays.stream(values()).filter(faction -> faction.name.equals(name)).findAny().orElse(null);
        }

        public static Faction getFactionById(int id) {
            return Arrays.stream(values()).filter(faction -> faction.id == id).findAny().orElse(null);
        }
    }
}
