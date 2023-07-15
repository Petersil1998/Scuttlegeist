package net.petersil98.scuttlegeist.collection;

import net.petersil98.scuttlegeist.data.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cards {

    private static Map<String, Card> cards;

    public static Card getCard(String id) {
        return cards.get(id);
    }

    public static List<Card> getCards() {
        return new ArrayList<>(cards.values());
    }
}
