package com.epam.war.service.card;

import static org.assertj.core.api.Assertions.assertThat;


import com.epam.war.domain.Deck;
import org.testng.annotations.Test;

public class LargeDeckGeneratorTest {

  LargeDeckGenerator largeDeckGenerator = new LargeDeckGenerator();

  @Test
  public void generatedDeck_hasExpectedSize() {
    Deck deck = largeDeckGenerator.generateDeck();

    assertThat(deck.getCards())
        .hasSize(52);
  }
}