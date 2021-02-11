package com.epam.war.service.player;

import com.epam.war.domain.Card;
import com.epam.war.domain.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimplePlayerGenerator implements PlayerGenerator {

  private static final String PLAYER_NAME_PREFIX = "Player";

  @Override
  public List<Player> generatePlayers(int playerNumber) {
    return IntStream.rangeClosed(1, playerNumber)
        .mapToObj(i -> new Player(PLAYER_NAME_PREFIX + i, initialHand()))
        .collect(Collectors.toList());
  }

  private List<Card> initialHand() {
    return new ArrayList<>();
  }
}
