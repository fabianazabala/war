package com.epam.war.service.player;

import static org.assertj.core.api.Assertions.assertThat;


import com.epam.war.domain.Player;
import java.util.List;
import org.testng.annotations.Test;

public class SimplePlayerGeneratorTest {

  @Test
  public void givenPlayerNumber_generatePlayersGeneratesExpectedPlayers() {
    int playerNumber = 5;
    SimplePlayerGenerator simplePlayerGenerator = new SimplePlayerGenerator(playerNumber);

    List<Player> players = simplePlayerGenerator.generatePlayers();

    assertThat(players).hasSize(playerNumber);
    assertThat(players.get(0).getName()).isEqualTo("Player1");
    assertThat(players.get(4).getName()).isEqualTo("Player5");
    assertThat(players).allSatisfy(player -> assertThat(player.getHand()).isEmpty());
  }
}