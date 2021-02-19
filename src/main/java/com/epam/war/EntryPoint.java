package com.epam.war;

import com.epam.war.domain.Deck;
import com.epam.war.domain.DeckSize;
import com.epam.war.domain.Player;
import com.epam.war.service.DealingWarGame;
import com.epam.war.service.SpecialGame;
import com.epam.war.service.WarGame;
import com.epam.war.service.card.CardDealer;
import com.epam.war.service.card.CardShuffler;
import com.epam.war.service.card.CardsFinder;
import com.epam.war.service.card.CollectionsCardShuffler;
import com.epam.war.service.card.DeckGenerator;
import com.epam.war.service.card.HighestCardsFinder;
import com.epam.war.service.card.LargeDeckGenerator;
import com.epam.war.service.card.RoundRobinCardDealer;
import com.epam.war.service.card.SmallDeckGenerator;
import com.epam.war.service.gameplay.Gameplay;
import com.epam.war.service.input.Input;
import com.epam.war.service.input.InputHandler;
import com.epam.war.service.player.HighestHandPlayersFinder;
import com.epam.war.service.player.PlayerGenerator;
import com.epam.war.service.player.SimplePlayerGenerator;
import com.epam.war.service.player.SpecialCasePlayerGenerator;
import com.epam.war.service.screen.Dealing;
import com.epam.war.service.screen.EndingScreen;
import com.epam.war.service.screen.GameScreen;
import com.epam.war.service.screen.SpecialCaseScreen;
import com.epam.war.service.screen.Usage;
import com.epam.war.service.screen.WarScreen;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(EntryPoint.class);

  public static void main(String[] args) {
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> logger.error("Uncaught exception happened!", e));

    SpecialGame specialGame = new SpecialGame();
    boolean isSpecialGame = specialGame.isSpecialGame();

    WarGame warGame;
    CardsFinder cardsFinder = new HighestCardsFinder();
    HighestHandPlayersFinder highestHandPlayersFinder = new HighestHandPlayersFinder();

    if (isSpecialGame) {
      PlayerGenerator playerGenerator = new SpecialCasePlayerGenerator(new ObjectMapper(),
          new SpecialCaseScreen(), specialGame);
      List<Player> players = playerGenerator.generatePlayers();
      DeckSize deckSize = DeckSize.fromCardCount(players.stream()
          .map(Player::getHand).mapToInt(List::size).sum());
      GameScreen gameScreen = new GameScreen(highestHandPlayersFinder, deckSize);
      WarScreen warScreen = new WarScreen(highestHandPlayersFinder, deckSize);
      Gameplay gameplay = new Gameplay(cardsFinder, gameScreen, warScreen);
      EndingScreen endingScreen = new EndingScreen(deckSize, players.size());
      warGame = new WarGame(players, gameplay, endingScreen);
    } else {
      Usage usage = new Usage();
      InputHandler inputHandler = new InputHandler(usage);
      Input input = inputHandler.handleArguments(args);
      PlayerGenerator playerGenerator = new SimplePlayerGenerator(input.getPlayerNumber());
      List<Player> players = playerGenerator.generatePlayers();
      DeckGenerator deckGenerator;
      switch (input.getDeckSize()) {
        case SMALL:
          deckGenerator = new SmallDeckGenerator();
          break;
        case LARGE:
          deckGenerator = new LargeDeckGenerator();
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + input.getDeckSize());
      }
      Deck deck = deckGenerator.generateDeck();
      CardShuffler shuffler = new CollectionsCardShuffler();
      Dealing dealing = new Dealing(highestHandPlayersFinder, input);
      CardDealer dealer = new RoundRobinCardDealer(dealing);
      GameScreen gameScreen = new GameScreen(highestHandPlayersFinder, input.getDeckSize());
      WarScreen warScreen = new WarScreen(highestHandPlayersFinder, input.getDeckSize());
      Gameplay gameplay = new Gameplay(cardsFinder, gameScreen, warScreen);
      EndingScreen endingScreen = new EndingScreen(input.getDeckSize(), input.getPlayerNumber());
      warGame = new DealingWarGame(players, deck, shuffler, dealer, gameplay, endingScreen);
    }

    warGame.play();
  }
}