package com.epam.war.service.screen;

import static com.epam.war.support.TestPlayerGenerator.withHand;
import static com.epam.war.support.TestPlayerGenerator.withHighHand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


import com.epam.war.domain.Card;
import com.epam.war.domain.CardSuit;
import com.epam.war.domain.CardValue;
import com.epam.war.domain.DeckSize;
import com.epam.war.domain.Player;
import com.epam.war.service.input.Input;
import com.epam.war.service.player.HighestHandPlayersFinder;
import com.epam.war.support.LoggerTest;
import java.util.List;
import java.util.Optional;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DealingTest extends LoggerTest {

  private static final Logger logger = LoggerFactory.getLogger(Dealing.class);

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
  public void givenInputParametersAndTwoPlayersWithHandsAndNoLuckyPlayers_thenScreenIsAsExpected() {
    Input input = Input.create(2, DeckSize.SMALL);
    List<Player> players = List.of(withHighHand(1, 3), withHighHand(2, 3));
    when(playersFinder.findPlayers(players)).thenReturn(Optional.empty());

    Dealing dealing = new Dealing(playersFinder, input);

    dealing.showScreen(players);

    assertThat(getFormattedMessages()).hasSize(1);
    assertThat(getFormattedMessages().get(0))
        .isEqualTo("WAR, 2 players, SMALL deck, DEALING\n" +
            "Player1 hand: [14, 14, 14]\n" +
            "Player2 hand: [14, 14, 14]\n" +
            "\n" +
            "Lucky players: none.\n" +
            "=========");
  }

  @Test
  public void givenLuckyPlayers_thenScreenIncludesLuckyPlayers() {
    Input input = Input.create(2, DeckSize.SMALL);
    List<Player> players = List.of(withHighHand(1, 3), withHighHand(2, 3));
    when(playersFinder.findPlayers(players)).thenReturn(Optional.of(players));

    Dealing dealing = new Dealing(playersFinder, input);

    dealing.showScreen(players);

    assertThat(getFormattedMessages()).hasSize(1);
    assertThat(getFormattedMessages().get(0))
        .isEqualTo("WAR, 2 players, SMALL deck, DEALING\n" +
            "Player1 hand: [14, 14, 14]\n" +
            "Player2 hand: [14, 14, 14]\n" +
            "\n" +
            "Lucky players: player1, player2.\n" +
            "=========");
  }

  @Test
  public void givenDifferentCardValue_thenPlayerHandsAreSortedDescending() {
    Input input = Input.create(2, DeckSize.SMALL);
    List<Card> cards = List.of(new Card(CardValue.EIGHT, CardSuit.DIAMONDS),
        new Card(CardValue.A, CardSuit.DIAMONDS),
        new Card(CardValue.TWO, CardSuit.DIAMONDS),
        new Card(CardValue.EIGHT, CardSuit.DIAMONDS));
    List<Player> players = List.of(withHand(1, cards));
    when(playersFinder.findPlayers(players)).thenReturn(Optional.empty());

    Dealing dealing = new Dealing(playersFinder, input);

    dealing.showScreen(players);

    assertThat(getFormattedMessages()).hasSize(1);
    assertThat(getFormattedMessages().get(0))
        .isEqualTo("WAR, 2 players, SMALL deck, DEALING\n" +
            "Player1 hand: [14, 8, 8, 2]\n" +
            "\n" +
            "Lucky players: none.\n" +
            "=========");
  }
}