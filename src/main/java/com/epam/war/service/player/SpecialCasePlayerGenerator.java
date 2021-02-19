package com.epam.war.service.player;


import static com.epam.war.service.input.InputHandler.MAXIMUM_PLAYER_NUMBER;
import static com.epam.war.service.input.InputHandler.MINIMUM_PLAYER_NUMBER;


import com.epam.war.domain.Card;
import com.epam.war.domain.CardSuit;
import com.epam.war.domain.CardValue;
import com.epam.war.domain.Player;
import com.epam.war.domain.SpecialCodeFileMangledException;
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
          .filter(players -> players.size() < MAXIMUM_PLAYER_NUMBER)
          .filter(players -> players.size() > MINIMUM_PLAYER_NUMBER)
          .orElseThrow(() ->
              new SpecialCodeFileMangledException("Incorrect number of players in input file, should be more " +
                  "than " + MINIMUM_PLAYER_NUMBER + " and less than " + MAXIMUM_PLAYER_NUMBER));
    } catch (IOException | URISyntaxException e) {
      throw invalidJsonException(e);
    }
  }

  private List<Player> jsonToPlayers(SpecialGame.InputFile inputFile) {
    specialCaseScreen.showScreen(inputFile.getFileName());
    try {
      return StreamSupport.stream(objectMapper.readTree(inputFile.getInputStream()).spliterator(), false)
          .map(this::nodeToPlayer)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw invalidJsonException(e);
    }
  }

  private IllegalStateException invalidJsonException(Exception cause) {
    return new IllegalStateException("Failed to read input JSON file", cause);
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
