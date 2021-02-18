package com.epam.war.service.screen;

import static com.epam.war.support.TestPlayerGenerator.withHighHand;
import static com.epam.war.support.TestPlayerGenerator.withLowHand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


import com.epam.war.domain.Card;
import com.epam.war.domain.DeckSize;
import com.epam.war.domain.Player;
import com.epam.war.service.player.HighestHandPlayersFinder;
import com.epam.war.support.CardGenerator;
import com.epam.war.support.LoggerTest;
import com.epam.war.support.TestPlayerGenerator;
import java.util.Collections;
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

public class WarScreenTest extends LoggerTest {

  private static final Logger logger = LoggerFactory.getLogger(WarScreen.class);

  @Mock
  HighestHandPlayersFinder playersFinder;
  DeckSize deckSize;

  WarScreen warScreen;

  @BeforeClass
  public void beforeClass() {
    setUpLogger(logger);
  }

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    deckSize = DeckSize.SMALL;

    warScreen = new WarScreen(playersFinder, deckSize);
  }

  @Test
  public void givenLeader_thenPrintHeaderLogsExpectedMessage() {
    Player leader = withHighHand(1, 5);
    List<Player> players = List.of(leader, withLowHand(2, 5));
    when(playersFinder.findPlayers(players)).thenReturn(Optional.of(List.of(leader)));

    warScreen.printHeader(players, 23);

    assertThat(getFormattedMessages()).hasSize(1);
    assertThat(getFormattedMessages().get(0))
        .isEqualTo("WAR, 2 players, SMALL deck, turn #23 - player1 leads with 5/24 cards\n" +
            "================================================================\n" +
            "                 WE HAVE A WAR LADIES AND GENTLEMEN!\n" +
            "================================================================");
  }

  @Test
  public void givenDraw_thenPrintHeaderLogsExpectedMessage() {
    List<Player> players = List.of(withHighHand(1, 5), withLowHand(2, 5));
    when(playersFinder.findPlayers(players)).thenReturn(Optional.empty());

    warScreen.printHeader(players, 23);

    assertThat(getFormattedMessages()).hasSize(1);
    assertThat(getFormattedMessages().get(0))
        .isEqualTo("WAR, 2 players, SMALL deck, turn #23 - DRAW with 5/24 cards\n" +
            "================================================================\n" +
            "                 WE HAVE A WAR LADIES AND GENTLEMEN!\n" +
            "================================================================");
  }

  @Test
  public void givenMultipleCardsPlayed_cardsAreConcatenated() {
    Map<Card, Player> played = new LinkedHashMap<>();
    Player player1 = TestPlayerGenerator.withHighHand(1, 5);
    Player player2 = TestPlayerGenerator.withHighHand(2, 5);

    played.put(CardGenerator.fromValue(10), player1);
    played.put(CardGenerator.fromValue(10), player2);
    played.put(CardGenerator.fromValue(11), player1);
    played.put(CardGenerator.fromValue(11), player2);

    warScreen.printTurn(played, player1.getHand());

    assertThat(getFormattedMessages())
        .hasSize(2);
    assertThat(getFormattedMessages().get(0))
        .isEqualTo("Player1 played: 10 ? 11");
    assertThat(getFormattedMessages().get(1))
        .isEqualTo("Player2 played: 10 ? 11");
  }

  @Test
  public void givenMultipleCardsPlayedAndAWinner_cardsAreConcatenatedAndWinnerAtTheEnd() {
    Map<Card, Player> played = new LinkedHashMap<>();
    Player player1 = TestPlayerGenerator.withHighHand(1, 5);
    Player player2 = TestPlayerGenerator.withHighHand(2, 5);
    Card winnerCard = CardGenerator.fromValue(12);

    played.put(CardGenerator.fromValue(10), player1);
    played.put(CardGenerator.fromValue(10), player2);
    played.put(CardGenerator.fromValue(11), player1);
    played.put(winnerCard, player2);

    warScreen.printTurn(played, List.of(winnerCard));

    assertThat(getFormattedMessages())
        .hasSize(2);
    assertThat(getFormattedMessages().get(0))
        .isEqualTo("Player1 played: 10 ? 11");
    assertThat(getFormattedMessages().get(1))
        .isEqualTo("Player2 played: 10 ? 12 -----------> WINNER FOUND!");
  }

  @Test
  public void givenMultipleCardsPlayedAndOutOfCard_cardsAreConcatenatedAndEoCAtTheEnd() {
    Map<Card, Player> played = new LinkedHashMap<>();
    Player player1 = new Player("Player1", Collections.emptyList());
    Player player2 = TestPlayerGenerator.withHighHand(2, 5);
    Card winnerCard = CardGenerator.fromValue(12);

    played.put(CardGenerator.fromValue(10), player1);
    played.put(CardGenerator.fromValue(10), player2);
    played.put(CardGenerator.fromValue(11), player1);
    played.put(winnerCard, player2);

    warScreen.printTurn(played, List.of(winnerCard));

    assertThat(getFormattedMessages())
        .hasSize(2);
    assertThat(getFormattedMessages().get(0))
        .isEqualTo("Player1 played: 10 ? 11 ? EoC");
    assertThat(getFormattedMessages().get(1))
        .isEqualTo("Player2 played: 10 ? 12 -----------> WINNER FOUND!");
  }

  @Test
  public void givenTotalTurnNumber_thenEndWarLogsExpectedMessage() {
    warScreen.endWar(23);

    assertThat(getFormattedMessages()).hasSize(1);
    assertThat(getFormattedMessages().get(0))
        .isEqualTo("War lasted 23 rounds.\n" +
            "=========");
  }
}