package com.epam.war.service.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;


import com.epam.war.domain.Player;
import com.epam.war.service.screen.SpecialCaseScreen;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
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
  SpecialCaseScreen screen;
  @Captor
  ArgumentCaptor<File> fileCaptor;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void givenExampleInputFile_thenPlayersAreGeneratedAsExpected() {
    SpecialCasePlayerGenerator generator = new SpecialCasePlayerGenerator(objectMapper, screen);

    List<Player> players = generator.generatePlayers();

    assertThat(players).hasSize(3);
    assertThat(players.stream().map(Player::getName).collect(Collectors.joining(", ")))
        .isEqualTo("Player1, Player2, Player3");
    assertThat(players).allSatisfy(player -> assertThat(player.getHand()).hasSize(8));
    verify(screen).showScreen(fileCaptor.capture());
    assertThat(fileCaptor.getValue().getName())
        .isEqualTo("ssc_test_scenario_123.json");
  }
}