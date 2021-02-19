package com.epam.war.service.application;

import com.epam.war.domain.DeckSize;
import com.epam.war.domain.Player;
import com.epam.war.service.SpecialGame;
import com.epam.war.service.WarGame;
import com.epam.war.service.card.CardsFinder;
import com.epam.war.service.card.HighestCardsFinder;
import com.epam.war.service.gameplay.Gameplay;
import com.epam.war.service.player.HighestHandPlayersFinder;
import com.epam.war.service.player.PlayerGenerator;
import com.epam.war.service.player.SpecialCasePlayerGenerator;
import com.epam.war.service.screen.EndingScreen;
import com.epam.war.service.screen.GameScreen;
import com.epam.war.service.screen.SpecialCaseScreen;
import com.epam.war.service.screen.WarScreen;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class FileWarApplication implements WarApplication {

  private final SpecialGame specialGame;

  public FileWarApplication(SpecialGame specialGame) {
    this.specialGame = specialGame;
  }


  @Override
  public void start() {
    CardsFinder cardsFinder = new HighestCardsFinder();
    HighestHandPlayersFinder highestHandPlayersFinder = new HighestHandPlayersFinder();
    PlayerGenerator playerGenerator = new SpecialCasePlayerGenerator(new ObjectMapper(),
        new SpecialCaseScreen(), specialGame);
    List<Player> players = playerGenerator.generatePlayers();
    DeckSize deckSize = DeckSize.fromCardCount(players.stream()
        .map(Player::getHand)
        .mapToInt(List::size).sum());
    GameScreen gameScreen = new GameScreen(highestHandPlayersFinder, deckSize);
    WarScreen warScreen = new WarScreen(highestHandPlayersFinder, deckSize);
    Gameplay gameplay = new Gameplay(cardsFinder, warScreen, gameScreen);
    EndingScreen endingScreen = new EndingScreen(deckSize, players.size());

    WarGame warGame = new WarGame(players, gameplay, endingScreen);
    warGame.play();
  }
}
