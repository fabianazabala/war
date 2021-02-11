package com.epam.war.service;

import static com.epam.war.support.CardGenerator.generateSameCard;
import static org.assertj.core.api.Assertions.assertThat;


import com.epam.war.domain.Card;
import com.epam.war.domain.Deck;
import com.epam.war.domain.Player;
import com.epam.war.service.card.RoundRobinCardDealer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RoundRobinCardDealerTest {

  List<Player> playerList;
  Deck deck;
  List<Card> cards;

  RoundRobinCardDealer cardDealer;

  @BeforeMethod
  public void setUp() {
    cardDealer = new RoundRobinCardDealer();
  }


  @Test
  public void givenTenCardsAndTwoPlayers_thenEachPlayerHasFive() {
    cards = generateSameCard(10);
    playerList = generatePlayers(2);
    deck = new Deck(cards);

    cardDealer.deal(deck, playerList);

    assertThat(playerList)
        .allMatch(player -> player.getHand().size() == 5);
    assertThat(deck.hasCards())
        .isFalse();
  }

  @Test
  public void givenElevenCardsAndTwoPlayers_thenFirstPlayerHasSixAndSecondHasFive() {
    cards = generateSameCard(11);
    playerList = generatePlayers(2);
    deck = new Deck(cards);

    cardDealer.deal(deck, playerList);

    assertThat(playerList.get(0).getHand())
        .hasSize(6);
    assertThat(playerList.get(1).getHand())
        .hasSize(5);
    assertThat(deck.hasCards())
        .isFalse();
  }

  @Test
  public void givenNoCardsAndOnePlayer_thenPlayerHandIsEmpty() {
    cards = generateSameCard(0);
    playerList = generatePlayers(1);
    deck = new Deck(cards);

    cardDealer.deal(deck, playerList);

    assertThat(playerList.get(0).getHand())
        .isEmpty();
  }

  private List<Player> generatePlayers(int playerNumber) {
    return IntStream.range(0, playerNumber)
        .mapToObj(i -> new Player("Player_" + i, new ArrayList<>()))
        .collect(Collectors.toList());
  }
}