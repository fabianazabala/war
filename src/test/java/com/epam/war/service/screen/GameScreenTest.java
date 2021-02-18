package com.epam.war.service.screen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


import com.epam.war.domain.Card;
import com.epam.war.domain.DeckSize;
import com.epam.war.domain.Player;
import com.epam.war.service.player.HighestHandPlayersFinder;
import com.epam.war.support.LoggerTest;
import com.epam.war.support.TestPlayerGenerator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GameScreenTest extends LoggerTest {

  private static final Logger logger = LoggerFactory.getLogger(GameScreen.class);

  @Mock
  HighestHandPlayersFinder playersFinder;

  @BeforeClass
  public void beforeClass() {
    setUpLogger(logger);
  }

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void givenPlayersTableAndDeckSize_thenGameScreenIsShownAsExpected() {
    GameScreen gameScreen = new GameScreen(playersFinder, DeckSize.SMALL);
    Player firstPlayer = TestPlayerGenerator.withLowHand(1, 4);
    Player secondPlayer = TestPlayerGenerator.withHighHand(2, 5);
    List<Player> players = List.of(firstPlayer,
        secondPlayer);
    Map<Card, Player> table = new LinkedHashMap<>();
    table.put(firstPlayer.getHand().get(0), firstPlayer);
    table.put(secondPlayer.getHand().get(0), secondPlayer);

    int expectedTurn = 55;
    when(playersFinder.findPlayers(players)).thenReturn(Optional.of(List.of(secondPlayer)));

    gameScreen.showScreen(players, table, expectedTurn);

    assertThat(getFormattedMessages()).hasSize(1);
    assertThat(getFormattedMessages().get(0))
        .isEqualTo("WAR, 2 players, SMALL deck, turn #55 - player2 leads with 5/24 cards\n" +
            "Player1 played: 2\n" +
            "Player2 played: 14\n" +
            "=========");
  }

  @Test
  public void givenTie_thenGameScreenIsShownAsExpected() {
    GameScreen gameScreen = new GameScreen(playersFinder, DeckSize.SMALL);
    Player firstPlayer = TestPlayerGenerator.withLowHand(1, 5);
    Player secondPlayer = TestPlayerGenerator.withHighHand(2, 5);
    List<Player> players = List.of(firstPlayer,
        secondPlayer);
    Map<Card, Player> table = new LinkedHashMap<>();
    table.put(firstPlayer.getHand().get(0), firstPlayer);
    table.put(secondPlayer.getHand().get(0), secondPlayer);

    int expectedTurn = 55;
    when(playersFinder.findPlayers(players)).thenReturn(Optional.of(players));

    gameScreen.showScreen(players, table, expectedTurn);

    assertThat(getFormattedMessages()).hasSize(1);
    assertThat(getFormattedMessages().get(0))
        .isEqualTo("WAR, 2 players, SMALL deck, turn #55 - DRAW with 5/24 cards\n" +
            "Player1 played: 2\n" +
            "Player2 played: 14\n" +
            "=========");
  }
}