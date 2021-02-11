package com.epam.war.domain;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.testng.annotations.Test;

public class DeckTest {

  @Test
  public void givenDeckWithCards_hasCardsReturnTrue() {
    Deck deck = new Deck(List.of(new Card(CardValue.A, CardSuit.DIAMONDS)));

    assertThat(deck.hasCards()).isTrue();
  }

  @Test
  public void givenDeckWithNoCards_hasCardsReturnTrue() {
    Deck deck = new Deck(List.of());

    assertThat(deck.hasCards()).isFalse();
  }

  @Test
  public void givenDeckWithCards_pickCardReturnsCard() {
    Card expectedCard = new Card(CardValue.A, CardSuit.DIAMONDS);
    List<Card> cards = new ArrayList<>();
    cards.add(expectedCard);
    Deck deck = new Deck(cards);

    Optional<Card> card = deck.pickCard();

    assertThat(card)
        .isPresent()
        .hasValue(expectedCard);
  }

  @Test
  public void givenDeckWithNoCards_pickCardReturnsCard() {
    Deck deck = new Deck(List.of());

    Optional<Card> card = deck.pickCard();

    assertThat(card)
        .isEmpty();
  }
}