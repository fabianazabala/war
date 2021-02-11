package com.epam.war.service.card;

import com.epam.war.domain.Card;
import java.util.Collection;
import java.util.List;

public interface CardsFinder {
  List<Card> findCard(Collection<Card> cards);
}
