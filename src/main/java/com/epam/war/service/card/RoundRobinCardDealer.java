package com.epam.war.service.card;

import com.epam.war.domain.Deck;
import com.epam.war.domain.Player;
import java.util.List;

public class RoundRobinCardDealer implements CardDealer {

  @Override
  public void deal(Deck deck, List<Player> playerList) {
    while (deck.hasCards()) {
      playerList.forEach(player -> deck.pickCard().ifPresent(player::takeCard));
    }
  }
}