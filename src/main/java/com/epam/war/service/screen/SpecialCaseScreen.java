package com.epam.war.service.screen;

import java.io.File;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpecialCaseScreen extends Screen {

  private static final Logger logger = LoggerFactory.getLogger(SpecialCaseScreen.class);

  public void showScreen(File file) {
    String code = StringUtils.substringBetween(file.getName(), "ssc", ".json")
        .replace("_", " ").toUpperCase();

    logger.info("special scenario code: {}", code);
  }
}
