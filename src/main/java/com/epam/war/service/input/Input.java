package com.epam.war.service.input;

import com.epam.war.domain.DeckSize;
import java.util.Objects;

public class Input {

  public static final int DEFAULT_PLAYER_NUMBER = 2;
  public static final DeckSize DEFAULT_DECK_SIZE = DeckSize.SMALL;

  private final int playerNumber;
  private final DeckSize deckSize;

  public Input(int playerNumber, DeckSize deckSize) {
    this.playerNumber = playerNumber;
    this.deckSize = deckSize;
  }

  public static Input create() {
    return create(DEFAULT_PLAYER_NUMBER, DEFAULT_DECK_SIZE);
  }

  public static Input create(int playerNumber) {
    return create(playerNumber, DEFAULT_DECK_SIZE);
  }

  public static Input create(DeckSize deckSize) {
    return create(DEFAULT_PLAYER_NUMBER, deckSize);
  }

  public static Input create(int playerNumber, DeckSize deckSize) {
    return new Input(playerNumber, deckSize);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Input)) {
      return false;
    }
    Input input = (Input) o;
    return playerNumber == input.playerNumber && deckSize == input.deckSize;
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerNumber, deckSize);
  }

  public int getPlayerNumber() {
    return playerNumber;
  }

  public DeckSize getDeckSize() {
    return deckSize;
  }
}
