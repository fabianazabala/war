package com.epam.war.service.card;

import com.epam.war.domain.Card;
import java.util.List;

public interface CardShuffler {
  void shuffle(List<Card> cards);
}
