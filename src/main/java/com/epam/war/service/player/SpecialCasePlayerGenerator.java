package com.epam.war.service.player;


import com.epam.war.domain.Card;
import com.epam.war.domain.CardSuit;
import com.epam.war.domain.CardValue;
import com.epam.war.domain.Player;
import com.epam.war.service.SpecialGame;
import com.epam.war.service.screen.SpecialCaseScreen;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SpecialCasePlayerGenerator implements PlayerGenerator {

  private final ObjectMapper objectMapper;
  private final SpecialCaseScreen specialCaseScreen;
  private int currentPlayerNumber = 1;

  public SpecialCasePlayerGenerator(ObjectMapper objectMapper,
                                    SpecialCaseScreen specialCaseScreen) {
    this.objectMapper = objectMapper;
    this.specialCaseScreen = specialCaseScreen;
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
      return SpecialGame.getSpecialFile()
          .map(Path::toFile)
          .map(this::jsonToPlayers)
          .orElseThrow();
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private List<Player> jsonToPlayers(File file) {
    specialCaseScreen.showScreen(file);
    try {
      return StreamSupport.stream(objectMapper.readTree(file).spliterator(), false)
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
