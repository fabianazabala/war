package com.epam.war.service;

import static org.assertj.core.api.Assertions.assertThat;


import com.epam.war.domain.DeckSize;
import com.epam.war.service.input.Input;
import org.testng.annotations.Test;

public class InputTest {
  @Test
  public void whenUserInputIsNegative_thenNumberOfPlayersIsOne() {
    Input input = Input.create(-1, DeckSize.LARGE);
    assertThat(input.getPlayerNumber()).isEqualTo(1);
  }

  @Test
  public void whenUserInputIsLargerThanFive_thenNumberOfPlayersIsFive() {
    Input input = Input.create(123, DeckSize.LARGE);
    assertThat(input.getPlayerNumber()).isEqualTo(5);
  }

  @Test
  public void whenUserInputIsZero_ThenPlayerNumberIsOne() {
    Input input = Input.create(0, DeckSize.LARGE);
    assertThat(input.getPlayerNumber()).isEqualTo(1);
  }

  @Test
  public void whenUserInputSizeIsThree_ThenNumberOfPlayersIsThree() {
    Input input = Input.create(3, DeckSize.SMALL);
    assertThat(input.getPlayerNumber()).isEqualTo(3);
  }
}