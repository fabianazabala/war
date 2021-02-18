package com.epam.war.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


import com.epam.war.domain.Player;
import com.epam.war.service.gameplay.Gameplay;
import com.epam.war.service.screen.EndingScreen;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class WarGameTest {

  @Mock
  EndingScreen endingScreen;
  @Mock
  Gameplay gameplay;
  @Mock
  Player player;
  @Mock
  Player secondPlayer;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void givenTwoPlayersAndOneRunsOutOfCards_thenOneTurnIsPlayed() {
    List<Player> players = new ArrayList<>();
    int expectedTurn = 1;
    players.add(player);
    players.add(secondPlayer);
    when(player.hasCards()).thenReturn(true);
    when(secondPlayer.hasCards()).thenReturn(false);
    when(gameplay.getTurn()).thenReturn(expectedTurn);

    WarGame warGame = new WarGame(players, gameplay, endingScreen);

    warGame.play();

    assertThat(players).hasSize(1);

    verify(gameplay).playTurn(players);
    verify(gameplay).getTurn();
    verify(endingScreen).printEndingScreen(players.get(0), expectedTurn);
    verifyNoMoreInteractions(gameplay, endingScreen);
  }

  @Test
  public void givenTwoPlayersAndThreeTurnsWithCards_thenThreeTurnsArePlayed() {
    List<Player> players = new ArrayList<>();
    int expectedTurn = 3;
    players.add(player);
    players.add(secondPlayer);
    when(player.hasCards()).thenReturn(true, true, true);
    when(secondPlayer.hasCards()).thenReturn(true, true, false);
    when(gameplay.getTurn()).thenReturn(expectedTurn);

    WarGame warGame = new WarGame(players, gameplay, endingScreen);

    warGame.play();

    assertThat(players).hasSize(1);

    verify(gameplay, times(3)).playTurn(players);
    verify(gameplay).getTurn();
    verify(endingScreen).printEndingScreen(players.get(0), expectedTurn);
    verifyNoMoreInteractions(gameplay, endingScreen);
  }
}