package com.epam.war.service.card;

import com.epam.war.domain.Card;
import com.epam.war.domain.CardSuit;
import com.epam.war.domain.CardValue;
import com.epam.war.domain.Deck;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LargeDeckGenerator implements DeckGenerator {

  /**
   * Generates a deck with 52 cards where 2 is the lowest card and 14 the highest.
   *
   * @return Deck with cards.
   */
  @Override
  public Deck generateDeck() {
    return new Deck(generateCards());
  }

  private static List<Card> generateCards(){
  return Arrays.stream(CardValue.values())
      .flatMap(cardValue -> Arrays.stream(CardSuit.values())
      .map(suit -> new Card(cardValue, suit)))
      .collect(Collectors.toList());
  }
}