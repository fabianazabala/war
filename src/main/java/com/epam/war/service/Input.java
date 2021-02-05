package com.epam.war.service;

import com.epam.war.domain.DeckSize;
import java.util.Objects;

public class Input {
  private final int playerNumber;
  private final DeckSize deckSize;

  public Input(int playerNumber, DeckSize deckSize) {
    this.playerNumber = playerNumber;
    this.deckSize = deckSize;
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
