package com.epam.war.deal;

import com.epam.war.Deck;

public interface DeckGenerator {
  Deck generateDeck();
  DeckSize deckSize();
}
