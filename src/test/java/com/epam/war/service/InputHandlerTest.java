package com.epam.war.service;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;


import com.epam.war.domain.DeckSize;
import com.epam.war.service.input.Input;
import com.epam.war.service.input.InputHandler;
import com.epam.war.service.screen.Usage;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InputHandlerTest {

  @Mock
  Usage usage;

  @BeforeMethod
  public void beforeMethod() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void inputHasInvalidNumberOfArguments_thenMessageIsPrinted() throws Exception {
    InputHandler inputHandler = new InputHandler(usage);
    int exitCode = catchSystemExit(() ->
        inputHandler.handleArguments(new String[] {"2", "3", "4"})
    );
    assertThat(exitCode).isEqualTo(1);
    verify(usage).message();
  }

  @Test
  public void givenValidParameters_thenReturnProperInputs() {
    InputHandler inputHandler = new InputHandler(usage);
    Input actual = inputHandler.handleArguments(new String[] {"2", "small"});

    assertThat(actual.getPlayerNumber()).isEqualTo(2);
    assertThat(actual.getDeckSize()).isEqualTo(DeckSize.SMALL);
  }

  @Test
  public void misspelledParameterSmall_thenReturnsProperInput() {
    InputHandler inputHandler = new InputHandler(usage);
    Input actual = inputHandler.handleArguments(new String[] {"2", "sMAlL"});

    assertThat(actual.getDeckSize()).isEqualTo(DeckSize.SMALL);
  }

  @Test
  public void misspelledParameterLarge_thenReturnsProperInput() {
    InputHandler inputHandler = new InputHandler(usage);
    Input actual = inputHandler.handleArguments(new String[] {"2", "lArGE"});

    assertThat(actual.getDeckSize()).isEqualTo(DeckSize.LARGE);
  }


  @Test
  public void whenUserInputIsNegative_thenNumberOfPlayersIsOne() {
    InputHandler inputHandler = new InputHandler(usage);
    Input actual = inputHandler.handleArguments(new String[] {"-1", "large"});

    assertThat(actual.getPlayerNumber()).isEqualTo(1);
  }

  @Test
  public void whenUserInputIsLargerThanFive_thenNumberOfPlayersIsFive() {
    InputHandler inputHandler = new InputHandler(usage);
    Input actual = inputHandler.handleArguments(new String[] {"123", "large"});

    assertThat(actual.getPlayerNumber()).isEqualTo(5);
  }

  @Test
  public void whenUserInputIsZero_ThenPlayerNumberIsOne() {
    InputHandler inputHandler = new InputHandler(usage);
    Input actual = inputHandler.handleArguments(new String[] {"0", "large"});

    assertThat(actual.getPlayerNumber()).isEqualTo(1);
  }

  @Test
  public void givenInvalidPlayerNumberAndInvalidDeck_thenReturnsDefaultInputs() {
    InputHandler inputHandler = new InputHandler(usage);
    Input actual = inputHandler.handleArguments(new String[] {"a", "xxx"});

    assertThat(actual.getPlayerNumber()).isEqualTo(Input.DEFAULT_PLAYER_NUMBER);
    assertThat(actual.getDeckSize()).isEqualTo(Input.DEFAULT_DECK_SIZE);
  }

  @Test
  public void givenInvalidPlayerNumberAndValidDeck_thenReturnsDefaultPlayerNumber() {
    InputHandler inputHandler = new InputHandler(usage);
    Input actual = inputHandler.handleArguments(new String[] {"a", "small"});

    assertThat(actual.getPlayerNumber()).isEqualTo(Input.DEFAULT_PLAYER_NUMBER);
    assertThat(actual.getDeckSize()).isEqualTo(DeckSize.SMALL);
  }

  @Test
  public void givenInvalidDeckSizeAndValidPlayerNumber_thenReturnsDefaultDeckSize() {
    InputHandler inputHandler = new InputHandler(usage);
    Input actual = inputHandler.handleArguments(new String[] {"3", "aaa"});

    assertThat(actual.getPlayerNumber()).isEqualTo(3);
    assertThat(actual.getDeckSize()).isEqualTo(Input.DEFAULT_DECK_SIZE);
  }
}