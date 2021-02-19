package com.epam.war.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


import com.epam.war.domain.Card;
import com.epam.war.domain.Deck;
import com.epam.war.domain.Player;
import com.epam.war.service.card.CardDealer;
import com.epam.war.service.card.CardShuffler;
import com.epam.war.service.gameplay.Gameplay;
import com.epam.war.service.screen.EndingScreen;
import com.epam.war.support.CardGenerator;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DealingWarGameTest {

  @Mock
  EndingScreen endingScreen;
  @Mock
  Gameplay gameplay;
  @Mock
  Player player;
  @Mock
  Player secondPlayer;
  @Mock
  Deck deck;
  @Mock
  CardShuffler cardShuffler;
  @Mock
  CardDealer cardDealer;

  List<Player> players;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    players = new ArrayList<>();
    players.add(player);
    players.add(secondPlayer);
  }


  @Test
  public void givenPlayersAndCards_dealingWarGameDealsThenPlays() {
    int expectedTurn = 1;
    List<Card> cards = CardGenerator.generateSameCard(5);

    when(player.hasCards()).thenReturn(true);
    when(secondPlayer.hasCards()).thenReturn(false);
    when(gameplay.getTurn()).thenReturn(expectedTurn);
    when(deck.getCards()).thenReturn(cards);

    DealingWarGame dealingWarGame = new DealingWarGame(players, deck, cardShuffler, cardDealer, gameplay, endingScreen);

    dealingWarGame.play();

    verify(cardShuffler).shuffle(cards);
    verify(cardDealer).deal(deck, players);

    assertThat(players).hasSize(1);

    verify(gameplay).playTurn(players);
    verify(gameplay).getTurn();
    verify(endingScreen).printEndingScreen(players.get(0), expectedTurn);
    verifyNoMoreInteractions(gameplay, endingScreen);
  }
}