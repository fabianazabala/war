package com.epam.war.service.player;

import static com.epam.war.support.CardGenerator.generateSameCard;
import static org.assertj.core.api.Assertions.assertThat;


import com.epam.war.domain.Player;
import java.util.List;
import java.util.Optional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HighestHandPlayersFinderTest {

  HighestHandPlayersFinder luckyPlayerFinder;

  @BeforeMethod
  public void setUp() {
    luckyPlayerFinder = new HighestHandPlayersFinder();
  }

  @Test
  public void givenPlayersWithDifferentHandSize_playersWithLargerHandsAreReturned() {
    Player lucky1 = new Player("Lucky1", generateSameCard(5));
    Player lucky2 = new Player("Lucky2", generateSameCard(5));
    Player player1 = new Player("Player1", generateSameCard(4));
    Player player2 = new Player("Player2", generateSameCard(4));

    Optional<List<Player>> luckyPlayers = luckyPlayerFinder.findPlayers(List.of(player1, player2, lucky1, lucky2));

    assertThat(luckyPlayers)
        .isPresent()
        .hasValueSatisfying(value -> assertThat(value)
            .hasSize(2)
            .contains(lucky1, lucky2));
  }

  @Test
  public void givenPlayersWithSameHandSize_emptyIsReturned() {
    Player player1 = new Player("Lucky1", generateSameCard(5));
    Player player2 = new Player("Lucky2", generateSameCard(5));
    Player player3 = new Player("Player1", generateSameCard(5));
    Player player4 = new Player("Player2", generateSameCard(5));

    Optional<List<Player>> luckyPlayers = luckyPlayerFinder.findPlayers(List.of(player1, player2, player3, player4));

    assertThat(luckyPlayers)
        .isEmpty();
  }
}