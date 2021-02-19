package com.epam.war.service.gameplay;

import static com.epam.war.support.TestPlayerGenerator.withHand;
import static com.epam.war.support.TestPlayerGenerator.withHighHand;
import static com.epam.war.support.TestPlayerGenerator.withLowHand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.epam.war.domain.Card;
import com.epam.war.domain.CardValue;
import com.epam.war.domain.Player;
import com.epam.war.service.card.CardsFinder;
import com.epam.war.service.screen.GameScreen;
import com.epam.war.service.screen.WarScreen;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GameplayTest {

  @Mock
  CardsFinder cardsFinder;
  @Mock
  GameScreen gameScreen;
  @Mock
  WarScreen warScreen;
  @Captor
  ArgumentCaptor<Map<Card, Player>> tableCaptor;
  @Captor
  ArgumentCaptor<List<Pair<Optional<Card>, Player>>> warCardsCaptor;
  @Captor
  ArgumentCaptor<List<Card>> sakura;

  List<Player> players;

  Gameplay gameplay;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    players = new ArrayList<>();
    gameplay = new Gameplay(cardsFinder, warScreen, gameScreen);
  }

  @Test
  public void givenTurnWithClearWinner_turnIncreasesAndWinnerTakesAllCards() {
    int initialCardCount = 5;
    Player winner = withHighHand(1, initialCardCount);
    Card firstWinnerCard = winner.getHand().get(0);
    Player loser = withLowHand(2, initialCardCount);
    Card firstLoserCard = loser.getHand().get(0);
    players.add(winner);
    players.add(loser);
    when(cardsFinder.findCard(any())).thenReturn(List.of(winner.getHand().get(0)));

    gameplay.playTurn(players);

    assertThat(gameplay.getTurn()).isEqualTo(1);
    assertThat(winner.getHand()).hasSize(initialCardCount + 1);
    assertThat(loser.getHand()).hasSize(initialCardCount - 1);
    verify(gameScreen).showScreen(eq(players), tableCaptor.capture(), eq(1));
    Map<Card, Player> capturedTable = tableCaptor.getValue();
    assertThat(capturedTable).hasSize(2);
    assertThat(capturedTable)
        .containsEntry(firstLoserCard, loser)
        .containsEntry(firstWinnerCard, winner);
  }

  @Test
  public void givenTurnWithWinner_thenCardsAreTakenFromLowestToHighest() {
    int initialCardCount = 5;
    Player winner = withHighHand(1, initialCardCount);
    Player mediumLoser = withHand(2, CardValue.FIVE, initialCardCount);
    Player bigLoser = withLowHand(3, initialCardCount);
    Card mediumLoserCard = mediumLoser.getHand().get(0);
    Card bigLoserCard = bigLoser.getHand().get(0);
    Card winnerCard = winner.getHand().get(0);
    players.add(winner);
    players.add(mediumLoser);
    players.add(bigLoser);
    when(cardsFinder.findCard(any())).thenReturn(List.of(winner.getHand().get(0)));

    gameplay.playTurn(players);

    assertThat(gameplay.getTurn()).isEqualTo(1);
    assertThat(winner.getHand()).hasSize(initialCardCount + 2);
    assertThat(mediumLoser.getHand()).hasSize(initialCardCount - 1);
    assertThat(bigLoser.getHand()).hasSize(initialCardCount - 1);
    assertThat(winner.getHand().get(4)).isEqualTo(bigLoserCard);
    assertThat(winner.getHand().get(5)).isEqualTo(mediumLoserCard);
    assertThat(winner.getHand().get(6)).isEqualTo(winnerCard);

    verify(gameScreen).showScreen(eq(players), tableCaptor.capture(), eq(1));
    Map<Card, Player> capturedTable = tableCaptor.getValue();
    assertThat(capturedTable).hasSize(3);
    assertThat(capturedTable)
        .containsEntry(mediumLoserCard, mediumLoser)
        .containsEntry(bigLoserCard, bigLoser)
        .containsEntry(winnerCard, winner);
  }

  @Test
  public void givenPlayersWithSameHand_thenWarIsPlayedUntilEoC() {
    int initialCardCount = 8;
    Player loser1 = withHighHand(1, initialCardCount);
    Player loser2 = withHighHand(2, initialCardCount);
    Player winner = withHighHand(3, initialCardCount);
    List<Card> firstRound = List.of(loser1.getHand().get(0), loser2.getHand().get(0), winner.getHand().get(0));

    players.add(loser1);
    players.add(loser2);
    players.add(winner);
    when(cardsFinder.findCard(any()))
        .thenAnswer(inv -> List.copyOf(inv.getArgument(0)));

    gameplay.playTurn(players);

    verify(warScreen).printHeader(eq(players), eq(1));
    verify(warScreen).printTurn(warCardsCaptor.capture(), eq(winner));
    verify(warScreen).endWar(eq(initialCardCount / 2));

    List<Pair<Optional<Card>, Player>> capturedWarCards = warCardsCaptor.getValue();
    assertThat(capturedWarCards)
        .hasSize(initialCardCount * players.size() + players.size());
    assertThat(capturedWarCards.stream()
        .map(Pair::getLeft)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList()))
        .contains(firstRound.toArray(new Card[0]));
  }
}