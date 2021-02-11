package com.epam.war.service.card;

import com.epam.war.domain.Card;
import java.util.Collections;
import java.util.List;

public class CollectionsCardShuffler implements CardShuffler {
  @Override
  public void shuffle(List<Card> cards) {
    Collections.shuffle(cards);
  }
}
