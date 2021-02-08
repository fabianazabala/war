package com.epam.war.service;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;


import com.epam.war.domain.DeckSize;
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
    verify(usage).usageMessage();
  }

  @Test
  public void whenFirstParameterIsALetter_thenMessageIsPrinted() throws Exception {
    InputHandler inputHandler = new InputHandler(usage);
    int exitCode = catchSystemExit(() ->
        inputHandler.handleArguments(new String[] {"m", "small"})
    );
    assertThat(exitCode).isEqualTo(1);
    verify(usage).usageMessage();
  }

  @Test
  public void whenSecondParameterIsInvalid_thenMessageIsPrinted() throws Exception {
    InputHandler inputHandler = new InputHandler(usage);
    int exitCode = catchSystemExit(() ->
        inputHandler.handleArguments(new String[] {"3", "toko"})
    );
    assertThat(exitCode).isEqualTo(1);
    verify(usage).usageMessage();
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
}