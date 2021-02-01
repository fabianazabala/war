package com.epam.war.deal;

import com.epam.war.card.Card;
import com.epam.war.card.CardSuit;
import com.epam.war.card.CardValue;
import com.epam.war.Deck;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SmallDeckGenerator implements DeckGenerator {

  @Override
  public Deck generateDeck() {
    return new Deck(generateCards());
  }

  @Override
  public DeckSize deckSize() {
    return DeckSize.SMALL;
  }

  private static List<Card> generateCards() {
    return Arrays.stream(CardValue.values())
        .filter(cardValue -> cardValue.getValue() >= 9)
        .flatMap(cardValue -> Arrays.stream(CardSuit.values())
            .map(suit -> new Card(cardValue, suit)))
        .collect(Collectors.toList());
  }
}
