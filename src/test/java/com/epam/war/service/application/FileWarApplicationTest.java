package com.epam.war.service.application;

import static org.assertj.core.api.Assertions.assertThat;


import com.epam.war.service.SpecialGame;
import com.epam.war.support.LoggerTest;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FileWarApplicationTest extends LoggerTest {

  SpecialGame specialGame = new SpecialGame();

  @BeforeClass
  public void beforeClass() {
    setUpLogger(LoggerFactory.getLogger(""));
  }

  @Test
  public void givenInputFile_thenFileWarApplicationPlaysTheGame() {
    FileWarApplication fileWarApplication = new FileWarApplication(specialGame);

    fileWarApplication.start();

    assertThat(getFormattedMessages())
        .hasSize(146);
    assertThat(getFormattedMessages().get(0))
        .isEqualTo("special scenario code: A TEST SCENARIO 123");
    assertThat(getFormattedMessages().get(145))
        .isEqualTo("=========\n" +
            "WAR, 3 players, SMALL deck, turn #84 - player3 WON with 24/24 cards");

  }
}