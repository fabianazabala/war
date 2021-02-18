package com.epam.war.domain;

import java.util.Arrays;

public enum CardSuit {
  CLUBS,
  DIAMONDS,
  HEARTS,
  SPADES;

  /**
   * Returns any {@link CardSuit}
   *
   * @return CardSuit.
   */
  public static CardSuit any() {
    return Arrays.stream(values()).findFirst().orElseThrow();
  }
}
