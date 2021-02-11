package com.epam.war.service.card;

import com.epam.war.domain.Card;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class HighestCardsFinder implements CardsFinder {

  @Override
  public List<Card> findCard(Collection<Card> cards) {
    return cards.stream()
        .collect(Collectors.groupingBy(c -> c.getValue().getValue(),
            () -> new TreeMap<Integer, List<Card>>(Comparator.reverseOrder()),
            Collectors.toList()))
        .firstEntry()
        .getValue();
  }
}
