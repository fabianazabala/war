package com.epam.war.domain;

import java.util.Arrays;

public enum DeckSize {
  LARGE("large", 52), SMALL("small", 24);

  private final String code;
  private final int cardCount;

  DeckSize(String code, int cardCount) {
    this.code = code;
    this.cardCount = cardCount;
  }

  public static DeckSize fromCode(String code) {
    return Arrays.stream(values())
        .filter(deckSize -> deckSize.code.equalsIgnoreCase(code))
        .findFirst()
        .orElseThrow();
  }

  @Override
  public String toString() {
    return code.toUpperCase();
  }

  public int getCardCount() {
    return cardCount;
  }
}
