package com.epam.war.service.screen;

import java.io.File;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpecialCaseScreen extends Screen {

  private static final Logger logger = LoggerFactory.getLogger(SpecialCaseScreen.class);

  /**
   * Logs a screen which shows that game started out of a special case from a json file
   * and shows the case which we're playing.
   *
   * @param file who started this special case.
   */
  public void showScreen(File file) {
    String code = StringUtils.substringBetween(file.getName(), "ssc", ".json")
        .replace("_", " ").toUpperCase();

    logger.info("special scenario code:{}", code);
  }
}
