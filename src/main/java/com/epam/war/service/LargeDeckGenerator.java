package com.epam.war.service;

import com.epam.war.Deck;
import com.epam.war.domain.DeckSize;
import com.epam.war.domain.Card;
import com.epam.war.domain.CardSuit;
import com.epam.war.domain.CardValue;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LargeDeckGenerator implements DeckGenerator {

  @Override
  public Deck generateDeck() {
    return new Deck(generateCards());
  }

  @Override
  public DeckSize deckSize(){
    return DeckSize.LARGE;
  }

  private static List<Card> generateCards(){
  return Arrays.stream(CardValue.values())
      .flatMap(cardValue -> Arrays.stream(CardSuit.values())
      .map(suit -> new Card(cardValue, suit)))
      .collect(Collectors.toList());
  }
}
