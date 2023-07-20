package net.petersil98.scuttlegeist;

import net.petersil98.core.util.EncryptionUtil;
import net.petersil98.core.util.Loader;
import net.petersil98.core.util.settings.Language;
import net.petersil98.core.util.settings.Settings;
import net.petersil98.scuttlegeist.collection.Cards;
import net.petersil98.scuttlegeist.constants.LoRRegion;
import net.petersil98.scuttlegeist.data.Card;
import net.petersil98.scuttlegeist.model.Deck;
import net.petersil98.scuttlegeist.model.LoRRanked;
import net.petersil98.scuttlegeist.model.match.MatchDetails;
import net.petersil98.scuttlegeist.util.LoRLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class Scuttlegeist {

    public static final Logger LOGGER = LogManager.getLogger(Scuttlegeist.class);

    public static void main(String[] args) {
        Settings.setAPIKey(() -> EncryptionUtil.encrypt(System.getenv("API_KEY")));
        Settings.setDecryptor(EncryptionUtil::decrypt);
        Settings.setLanguage(Language.EN_US);
        Settings.useCache(true);
        Loader.addLoader(new LoRLoader());
        Loader.init();
        Map<Card, Integer> deck = Deck.decodeDeck("CEDQCAIBDYBQMAIEBMGACBQCDQAQMAARAEDAYCABA4AQSAIGAYOQIAQGAADBQAIBAECACBABBIAQMBQ3AMAQMCRPAEAQCKQBAYASO");
        List<Card> poros = List.of(
                Cards.getCard("07RU015"),
                Cards.getCard("07RU015"),
                Cards.getCard("07RU015"),
                Cards.getCard("01NX034"),
                Cards.getCard("01NX034"),
                Cards.getCard("01NX034"),
                Cards.getCard("01PZ020"),
                Cards.getCard("04SH049"),
                Cards.getCard("01FR008"),
                Cards.getCard("01FR008"),
                Cards.getCard("01FR008"),
                Cards.getCard("01IO005"),
                Cards.getCard("01DE049"),
                Cards.getCard("02BW010"),
                Cards.getCard("02BW009"),
                Cards.getCard("02BW009"),
                Cards.getCard("02BW009"),
                Cards.getCard("03MT039"),
                Cards.getCard("01FR025"),
                Cards.getCard("01FR025"),
                Cards.getCard("01FR025"),
                Cards.getCard("04FR015"),
                Cards.getCard("04FR015"),
                Cards.getCard("01SI037"),
                Cards.getCard("05BC160"),
                Cards.getCard("06FR021"),
                Cards.getCard("06FR021"),
                Cards.getCard("06FR021"),
                Cards.getCard("06BC043"),
                Cards.getCard("06BC043"),
                Cards.getCard("06BC043"),
                Cards.getCard("01FR016"),
                Cards.getCard("01FR016"),
                Cards.getCard("01FR016"),
                Cards.getCard("02BW051"),
                Cards.getCard("02BW051"),
                Cards.getCard("02BW051"),
                Cards.getCard("07BW033"),
                Cards.getCard("07BW033"),
                Cards.getCard("07BW033")
        );
        String s = Deck.encodeDeck(poros);
        Map<Card, Integer> another = Deck.decodeDeck(s);
        List<MatchDetails> matchDetails = MatchDetails.getMatchHistory("WG-EdMhZnQITJsSz8gSlKtSRCRPaDs7Sl-tDaGQN39uNXCui4Kducxhgo5hdjj2FhXM6GYV6hDVVmg", LoRRegion.EUROPE);
        System.out.println(LoRRanked.getLeaderboard(LoRRegion.EUROPE));
    }
}
