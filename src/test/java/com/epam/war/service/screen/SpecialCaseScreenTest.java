package com.epam.war.service.screen;

import static org.assertj.core.api.Assertions.assertThat;


import com.epam.war.support.LoggerTest;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SpecialCaseScreenTest extends LoggerTest {

  private static final Logger logger = LoggerFactory.getLogger(SpecialCaseScreen.class);

  @BeforeClass
  public void beforeClass() {
    setUpLogger(logger);
  }

  @Test
  public void givenTestScenarioFile_thenLogsExpectedMessage() {
    SpecialCaseScreen specialCaseScreen = new SpecialCaseScreen();
    specialCaseScreen.showScreen(Paths.get("/ssc_test_scenario_123.json").toFile());

    assertThat(getFormattedMessages()).hasSize(1);
    assertThat(getFormattedMessages().get(0)).isEqualTo("special scenario code: TEST SCENARIO 123");
  }
}