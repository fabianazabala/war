package com.epam.war;

import com.epam.war.domain.Player;
import com.epam.war.service.CardDealer;
import com.epam.war.service.CardShuffler;
import com.epam.war.service.CollectionsCardShuffler;
import com.epam.war.service.DeckGenerator;
import com.epam.war.service.Input;
import com.epam.war.service.InputHandler;
import com.epam.war.service.LargeDeckGenerator;
import com.epam.war.service.RoundRobinCardDealer;
import com.epam.war.service.SmallDeckGenerator;
import com.epam.war.service.screen.Usage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntryPoint {

  public static void main(String[] args) {
    Usage usage = new Usage();
    InputHandler inputHandler = new InputHandler(usage);

    Input input = inputHandler.handleArguments(args);
    //inicializar un deck generator dependiento del deck size que me pasaron

    List<Player> playerList = IntStream.range(0, input.getPlayerNumber())
        .mapToObj(i -> new Player("Player_" + i, new ArrayList<>()))
        .collect(Collectors.toList());

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
    CardShuffler shuffle = new CollectionsCardShuffler();
    shuffle.shuffle(deck.getCards());

    CardDealer roundRobinCardDealer = new RoundRobinCardDealer();
    roundRobinCardDealer.deal(deck, playerList);

  }
}