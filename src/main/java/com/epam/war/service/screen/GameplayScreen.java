package com.epam.war.service.screen;

import com.epam.war.domain.Card;
import com.epam.war.domain.DeckSize;
import com.epam.war.domain.Player;
import com.epam.war.service.player.HighestHandPlayersFinder;
import java.util.List;

abstract class GameplayScreen extends Screen {

  protected final HighestHandPlayersFinder highestHandPlayersFinder;
  protected final DeckSize deckSize;

  protected GameplayScreen(HighestHandPlayersFinder highestHandPlayersFinder, DeckSize deckSize) {
    this.highestHandPlayersFinder = highestHandPlayersFinder;
    this.deckSize = deckSize;
  }

  protected String warHeaderMessage(List<Player> players, int turn) {
    return "WAR, " + players.size() + ", " +
        deckSize + " deck" + ", " +
        "turn #" + turn + " - " +
        getLeadMessage(players) + "\n";
  }

  private String getLeadMessage(List<Player> players) {
    return highestHandPlayersFinder.findPlayers(players)
        .map(p -> {
          if (p.size() == 1) {
            return p.get(0).getName() + " leads with " + handCountString(p.get(0).getHand());
          } else {
            return drawMessage(p);
          }
        })
        .orElseGet(() -> drawMessage(players));
  }

  private String drawMessage(List<Player> players) {
    return "DRAW with " + handCountString(players.get(0).getHand());
  }

  private String handCountString(List<Card> hand) {
    return hand.size() + "/" + deckSize.getCardCount() + " cards";
  }
}
