package com.epam.war.service.card;

import com.epam.war.domain.Card;
import java.util.Collections;
import java.util.List;

public class CollectionsCardShuffler implements CardShuffler {
  /**
   * Shuffles the list of cards using {@link Collections#shuffle(List)}
   *
   * @param cards Mutable list of cards to shuffle.
   */
  @Override
  public void shuffle(List<Card> cards) {
    Collections.shuffle(cards);
  }
}
