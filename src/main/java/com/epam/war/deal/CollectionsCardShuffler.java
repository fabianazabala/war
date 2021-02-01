package com.epam.war.deal;

import com.epam.war.card.Card;
import java.util.Collections;
import java.util.List;

public class CollectionsCardShuffler implements CardShuffler {
  @Override
  public void shuffle(List<Card> cards) {
    Collections.shuffle(cards);
  }
}
