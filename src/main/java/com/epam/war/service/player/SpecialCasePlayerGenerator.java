package com.epam.war.service.player;


import com.epam.war.domain.Card;
import com.epam.war.domain.CardSuit;
import com.epam.war.domain.CardValue;
import com.epam.war.domain.Player;
import com.epam.war.service.SpecialGame;
import com.epam.war.service.screen.SpecialCaseScreen;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SpecialCasePlayerGenerator implements PlayerGenerator {

  private final ObjectMapper objectMapper;
  private final SpecialCaseScreen specialCaseScreen;
  private final SpecialGame specialGame;
  private int currentPlayerNumber = 1;

  public SpecialCasePlayerGenerator(ObjectMapper objectMapper,
                                    SpecialCaseScreen specialCaseScreen,
                                    SpecialGame specialGame) {
    this.objectMapper = objectMapper;
    this.specialCaseScreen = specialCaseScreen;
    this.specialGame = specialGame;
  }

  /**
   * Generates as many players as found in the special case file, with pre-populated hands.
   *
   * @return list of generated players.
   * @throws RuntimeException if there's no special file available, or if it cannot be parsed.
   */
  @Override
  public List<Player> generatePlayers() {
    try {
      return specialGame.getSpecialFile()
          .map(this::jsonToPlayers)
          .orElseThrow();
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private List<Player> jsonToPlayers(SpecialGame.InputFile inputFile) {
    specialCaseScreen.showScreen(inputFile.getFileName());
    try {
      return StreamSupport.stream(objectMapper.readTree(inputFile.getInputStream()).spliterator(), false)
          .map(this::nodeToPlayer)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Player nodeToPlayer(JsonNode node) {
    return new Player(PLAYER_NAME_PREFIX + currentPlayerNumber++,
        StreamSupport.stream(node.spliterator(), false)
            .map(JsonNode::asInt)
            .map(this::intToCard)
            .collect(Collectors.toList()));
  }

  private Card intToCard(int number) {
    return new Card(CardValue.fromValue(number), CardSuit.any());
  }
}
