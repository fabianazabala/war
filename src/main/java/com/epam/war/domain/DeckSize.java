package com.epam.war.domain;

import java.util.Arrays;
import java.util.Optional;

public enum DeckSize {
  LARGE("large"), SMALL("small");

  private final String code;

  DeckSize(String code) {
    this.code = code;
  }

  public static DeckSize fromCode(String code) {
    return Arrays.stream(values())
        .filter(deckSize -> deckSize.code.equalsIgnoreCase(code))
        .findFirst()
        .orElseThrow();
  }
}
