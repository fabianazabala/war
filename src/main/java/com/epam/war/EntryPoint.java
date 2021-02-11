package com.epam.war;

import com.epam.war.domain.Deck;
import com.epam.war.domain.Player;
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
import com.epam.war.service.screen.Game;
import com.epam.war.service.screen.Usage;
import java.util.List;
import java.util.Random;

public class EntryPoint {

  public static void main(String[] args) {
    Usage usage = new Usage();
    InputHandler inputHandler = new InputHandler(usage);
    Input input = inputHandler.handleArguments(args);

    PlayerGenerator playerGenerator = new SimplePlayerGenerator();
    List<Player> players = playerGenerator.generatePlayers(input.getPlayerNumber());

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
    CardDealer dealer = new RoundRobinCardDealer();
    CardsFinder cardsFinder = new HighestCardsFinder();
    HighestHandPlayersFinder highestHandPlayersFinder = new HighestHandPlayersFinder();
    Game game = new Game(highestHandPlayersFinder, input);
    Gameplay gameplay = new Gameplay(new Random(), cardsFinder, game);

    WarGame warGame = new WarGame(players, deck, shuffler, dealer, gameplay);
    warGame.play();
  }
}