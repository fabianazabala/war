package com.epam.war.service;

import com.epam.war.Deck;
import com.epam.war.domain.Card;
import com.epam.war.domain.Player;
import java.util.List;

public interface CardDealer {
  void deal(Deck deck,
            List<Player> playerList);
}