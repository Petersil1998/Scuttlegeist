# Scuttlegeist

Scuttlegeist is an Object-Oriented Java Library, which takes over the Communication with the League of Legends API. It supports In-Memory caching and uses a (blocking) Rate Limiter. It makes retrieving Summoner Data, Match History,
etc. much easier.

Other Projects:
- [Thresh](https://github.com/Petersil1998/Thresh) for League of Legends
- [Spatula](https://github.com/Petersil1998/Spatula) for Teamfight Tactics
- [Fade](https://github.com/Petersil1998/Fade) for Valorant

## Usage

Scuttlegeist can be included like this using **Gradle**:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
  ...
  implementation 'com.github.Petersil1998:Core:v1.3'
  implementation 'com.github.Petersil1998:Scuttlegeist:v1.0'
}
```

or using **Maven**:

```XML
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.Petersil1998</groupId>
  <artifactId>Core</artifactId>
  <version>v1.3</version>
</dependency>
<dependency>
  <groupId>com.github.Petersil1998</groupId>
  <artifactId>Scuttlegeist</artifactId>
  <version>v1.0</version>
</dependency>
```

## Setup

In Order for Scuttlegeist to work properly there are a few things you need to set up
at the beginning of your application.

```JAVA
public class Example {
    public static void main(String[] args) {
        // First we need to provide our Riot API Key. Ideally the API Key is encrypted
        Settings.setAPIKey(() -> EncryptionUtil.encrypt(System.getenv("API_KEY")));
        // If the provided API Key is encrypted, we need to provide a function to decrypt the API Key
        Settings.setDecryptor(EncryptionUtil::decrypt);
        // We also need to provide a language. The language is used to static Data like Champions, Item, etc.
        // Not all Languages provided in the Language Enum are available for LoR, see Language#availableForLor
        Settings.setLanguage(Language.EN_US);
        // If we want to use caching we can enable it in the Settings. Caching is disabled by default
        Settings.useCache(true);
        // We also need to add the Loader for the static LoR Data
        Loader.addLoader(new LoRLoader());
        // Lastly we need to initialize the static Data
        Loader.init();
    }
}
```

Now Scuttlegeist is ready and set up!

## Examples

- **Account Data**

    ```JAVA
    public class Example {
        public static void main(String[] args) {
            // Setup code...
            
            // Get Account
            Account account = Account.getAccountByRiotId("Player", "Tag", Region.AMERICA);
            // Get the Tag (e.g. Faker#KR1)
            String tag = account.toString();
            // Get the PUUID
            String puuid = account.getPuuid();
        }
    } 
    ```

- **Leaderboards**

    ```JAVA
    public class Example {
        public static void main(String[] args) {
            // Setup code...
            
            // Get the Leaderboard for Europe
            List<RankEntry> players = LoRRanked.getLeaderboard(LoRRegion.EUROPE);
            for (RankEntry player: players) {
                // Get the Player Name
                String playerName = player.getPlayerName();
                // Get the LP
                int lp = player.getLp();
                // Get the Player's rank
                int rank = player.getRank();
            }
        }
    } 
    ```

- **Match History**

    ```JAVA
    public class Example {
        public static void main(String[] args) {
            // Setup code...
            
            // Get the Player
            Account me = Account.getAccountByRiotId("Player", "Tag", LoRRegion.EUROPE);
            // Get the Players Match History
            List<MatchDetails> matches = MatchDetails.getMatchHistory(me.getPuuid(), LoRRegion.EUROPE);
            for(MatchDetails match: matches) {
                // Get the Game Mode
                String gameMode = match.getGameMode();
                // Get the Game's start time
                long startTime = match.getGameStartTime();
                // Get the total number of Turns
                int totalTurnCount = match.getTotalTurnCount();
                // Get the Players
                List<Player> players = match.getPlayers();
                for(Player player: players) {
                    // Get the used Deck
                    String deckCode = player.getDeckCode();
                    // Decode the Deck into a map of Cards and the amount of copies of that card
                    Map<Card, Integer> cards = Deck.decodeDeck(deckCode);
                    // Get the games Outcome for that Player (Win, Lose, Draw, etc.)
                    String result = player.getGameOutcome();
                    // Get the order in which the Players played
                    int playOrder = player.getOrderOfPlay();
                }
            }
        } 
    } 
    ```

- **Decks**

    ```JAVA
    public class Example {
        public static void main(String[] args) {
            // Setup code...
            
            // Decode the Deck Code into a Map of Cards and the amount of Copies of that Card
            Map<Card, Integer> deck = Deck.decodeDeck("CEDQCAIBDYBQMAIEBMGACBQCDQAQMAARAEDAYCABA4AQSAIGAYOQIAQGAADBQAIBAECACBABBIAQMBQ3AMAQMCRPAEAQCKQBAYASO");
            // Create a Poro Deck
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
            // Get the Deck's code to import it into LoR e.g.
            String s = Deck.encodeDeck(poros);
        } 
    } 
    ```

- **Collections**

    The package [collection](https://github.com/Petersil1998/Scuttlegeist/blob/main/src/main/java/net/petersil98/scuttlegeist/collection/) contains a bunch of Collections for static Data including:
  
    - Cards
    - Formats
    - Rarities
    - Maps
    - Regions
    - Sets
    - Keywords
    - ...

### Feel free to give Feedback and add suggestions on how this library can be improved. <br>Thank you for using Scuttlegeist, you're awesome!
