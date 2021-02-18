package com.epam.war.support;

import com.epam.war.domain.Card;
import com.epam.war.domain.CardSuit;
import com.epam.war.domain.CardValue;
import com.epam.war.domain.Player;
import java.util.List;

public class TestPlayerGenerator {
  public static Player withHand(int playerNr, List<Card> hand) {
    return new Player("Player" + playerNr, hand);
  }

  public static Player withHighHand(int playerNr, int cardCount) {
    return withHand(playerNr, CardGenerator.generateSameCard(cardCount, CardValue.A, CardSuit.DIAMONDS));
  }

  public static Player withHand(int playerNr, CardValue cardValue, int cardCount) {
    return withHand(playerNr, CardGenerator.generateSameCard(cardCount, cardValue, CardSuit.DIAMONDS));
  }

  public static Player withLowHand(int playerNr, int cardCount) {
    return withHand(playerNr, CardGenerator.generateSameCard(cardCount, CardValue.TWO, CardSuit.DIAMONDS));
  }
}
