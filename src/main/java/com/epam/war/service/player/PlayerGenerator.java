package com.epam.war.service.player;

import com.epam.war.domain.Player;
import java.util.List;

public interface PlayerGenerator {
  String PLAYER_NAME_PREFIX = "Player";

  List<Player> generatePlayers();
}
