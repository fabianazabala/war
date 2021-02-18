package com.epam.war.service;

import com.epam.war.domain.Deck;
import com.epam.war.domain.Player;
import com.epam.war.service.card.CardDealer;
import com.epam.war.service.card.CardShuffler;
import com.epam.war.service.gameplay.Gameplay;
import com.epam.war.service.screen.EndingScreen;
import java.util.List;

public class DealingWarGame extends WarGame {

  private final CardShuffler shuffler;
  private final CardDealer dealer;
  private final Deck deck;

  public DealingWarGame(List<Player> players, Deck deck,
                        CardShuffler shuffler, CardDealer dealer,
                        Gameplay gameplay, EndingScreen endingScreen) {
    super(players, gameplay, endingScreen);
    this.shuffler = shuffler;
    this.dealer = dealer;
    this.deck = deck;
  }

  @Override
  public void play() {
    shuffler.shuffle(deck.getCards());
    dealer.deal(deck, players);

    super.play();
  }
}
