package com.epam.war.service.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.epam.war.domain.Player;
import com.epam.war.domain.SpecialCodeFileMangledException;
import com.epam.war.service.SpecialGame;
import com.epam.war.service.screen.SpecialCaseScreen;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SpecialCasePlayerGeneratorTest {

  ObjectMapper objectMapper = new ObjectMapper();

  @Mock
  SpecialGame specialGame;
  @Mock
  SpecialCaseScreen screen;
  @Captor
  ArgumentCaptor<String> fileNameCaptor;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void givenExampleInputFile_thenPlayersAreGeneratedAsExpected() throws IOException, URISyntaxException {
    SpecialCasePlayerGenerator generator = new SpecialCasePlayerGenerator(objectMapper, screen, specialGame);
    String fileName = "ssc_a_test_scenario_123.json";
    when(specialGame.getSpecialFile()).thenReturn(Optional.of(new SpecialGame.InputFile(fileName,
        getClass().getResourceAsStream("/" + fileName))));

    List<Player> players = generator.generatePlayers();

    assertThat(players).hasSize(3);
    assertThat(players.stream().map(Player::getName).collect(Collectors.joining(", ")))
        .isEqualTo("Player1, Player2, Player3");
    assertThat(players).allSatisfy(player -> assertThat(player.getHand()).hasSize(8));
    verify(screen).showScreen(fileNameCaptor.capture());
    assertThat(fileNameCaptor.getValue())
        .isEqualTo(fileName);
  }

  @Test
  public void givenBrokenFile_thenIllegalStateExceptionIsThrown() throws IOException, URISyntaxException {
    SpecialCasePlayerGenerator generator = new SpecialCasePlayerGenerator(objectMapper, screen, specialGame);
    String fileName = "ssc_broken_file.json";
    when(specialGame.getSpecialFile()).thenReturn(Optional.of(new SpecialGame.InputFile(fileName,
        getClass().getResourceAsStream("/" + fileName))));

    assertThatThrownBy(generator::generatePlayers)
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("Failed to read input JSON file");
  }

  @Test
  public void givenExceptionWhileReadingFile_thenIllegalStateExceptionIsThrown()
      throws IOException, URISyntaxException {
    SpecialCasePlayerGenerator generator = new SpecialCasePlayerGenerator(objectMapper, screen, specialGame);
    String fileName = "ssc_broken_file.json";
    when(specialGame.getSpecialFile()).thenThrow(new FileNotFoundException());

    assertThatThrownBy(generator::generatePlayers)
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("Failed to read input JSON file");
  }

  @Test
  public void givenIncompleteNumberOfPlayers_thenSpecialCodeFileMangledExceptionIsThrown()
      throws IOException, URISyntaxException {
    SpecialCasePlayerGenerator generator = new SpecialCasePlayerGenerator(objectMapper, screen, specialGame);
    String fileName = "ssc_insufficient_players.json";
    when(specialGame.getSpecialFile()).thenReturn(Optional.of(new SpecialGame.InputFile(fileName,
        getClass().getResourceAsStream("/" + fileName))));

    assertThatThrownBy(generator::generatePlayers)
        .isInstanceOf(SpecialCodeFileMangledException.class)
        .hasMessage("Incorrect number of players in input file, should be more than 1 and less than 5");
  }
}