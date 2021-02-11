package com.epam.war.service.player;

import com.epam.war.domain.Player;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class HighestHandPlayersFinder {
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
