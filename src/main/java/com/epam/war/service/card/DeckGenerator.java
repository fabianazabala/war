package com.epam.war.service.card;

import com.epam.war.domain.Deck;
import com.epam.war.domain.DeckSize;

public interface DeckGenerator {
  Deck generateDeck();
  DeckSize deckSize();
}
