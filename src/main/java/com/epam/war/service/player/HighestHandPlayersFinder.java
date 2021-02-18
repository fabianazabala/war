package com.epam.war.service.player;

import com.epam.war.domain.Player;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class HighestHandPlayersFinder {
  /**
   * Finds the player or players with the highest hand count.
   *
   * @param players list of players to check
   * @return Optional which contains list of players with the highest hand count
   * or empty if no players has more card than others.
   */
  public Optional<List<Player>> findPlayers(List<Player> players) {
    List<Player> playersWithHighestHands = players.stream()
        .collect(Collectors.groupingBy(p -> p.getHand().size(),
            () -> new TreeMap<Integer, List<Player>>(Comparator.reverseOrder()),
            Collectors.toList()))
        .firstEntry()
        .getValue();

    return Optional.of(playersWithHighestHands)
        .filter(p -> p.size() != players.size());
  }
}
