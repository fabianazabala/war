package com.epam.war.service;

import com.epam.war.Deck;
import com.epam.war.domain.DeckSize;

public interface DeckGenerator {
  Deck generateDeck();
  DeckSize deckSize();
}
