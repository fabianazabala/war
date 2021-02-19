package com.epam.war.service.application;

import com.epam.war.domain.Deck;
import com.epam.war.domain.Player;
import com.epam.war.service.DealingWarGame;
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
import com.epam.war.service.screen.Dealing;
import com.epam.war.service.screen.EndingScreen;
import com.epam.war.service.screen.GameScreen;
import com.epam.war.service.screen.Usage;
import com.epam.war.service.screen.WarScreen;
import java.util.List;

public class InputWarApplication implements WarApplication {

  private final String[] args;

  public InputWarApplication(String[] args) {
    this.args = args;
  }

  @Override
  public void start() {
    CardsFinder cardsFinder = new HighestCardsFinder();
    HighestHandPlayersFinder highestHandPlayersFinder = new HighestHandPlayersFinder();
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
    Gameplay gameplay = new Gameplay(cardsFinder, warScreen, gameScreen);
    EndingScreen endingScreen = new EndingScreen(input.getDeckSize(), input.getPlayerNumber());

    WarGame warGame = new DealingWarGame(players, deck, shuffler, dealer, gameplay, endingScreen);
    warGame.play();
  }
}
