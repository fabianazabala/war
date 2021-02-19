package com.epam.war.domain;

import java.util.Arrays;

public enum CardValue {
  A("a", 14),
  J("j", 11),
  Q("q", 12),
  K("k", 13),
  TWO("two", 2),
  THREE("three", 3),
  FOUR("four", 4),
  FIVE("five", 5),
  SIX("six", 6),
  SEVEN("seven", 7),
  EIGHT("eight", 8),
  NINE("nine", 9),
  TEN("ten", 10);

  private final int value;
  private final String cardName;

  CardValue(String cardName, int value) {
    this.cardName = cardName;
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public String getCardName() {
    return cardName;
  }

  /**
   * Returns a {@link CardValue} from its numeric value.
   *
   * @param value card's value.
   * @return CardValue
   */
  public static CardValue fromValue(int value) {
    return Arrays.stream(values())
        .filter(v -> v.getValue() == value)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Can't create a card with a value of " + value));
  }
}
