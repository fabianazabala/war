package com.epam.war.service.player;

import com.epam.war.domain.Player;
import java.util.List;

public interface PlayerGenerator {
  List<Player> generatePlayers(int playerNumber);
}
