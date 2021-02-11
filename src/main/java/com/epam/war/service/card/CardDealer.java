package com.epam.war.service.card;

import com.epam.war.domain.Deck;
import com.epam.war.domain.Player;
import java.util.List;

public interface CardDealer {
  void deal(Deck deck,
            List<Player> playerList);
}