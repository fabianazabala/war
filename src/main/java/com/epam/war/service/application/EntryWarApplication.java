package com.epam.war.service.application;

import com.epam.war.service.SpecialGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntryWarApplication implements WarApplication {

  private static final Logger logger = LoggerFactory.getLogger(EntryWarApplication.class);

  private final SpecialGame specialGame;
  private final FileWarApplication fileWarApplication;
  private final InputWarApplication inputWarApplication;

  public EntryWarApplication(SpecialGame specialGame,
                             FileWarApplication fileWarApplication,
                             InputWarApplication inputWarApplication) {
    this.specialGame = specialGame;
    this.fileWarApplication = fileWarApplication;
    this.inputWarApplication = inputWarApplication;
  }

  @Override
  public void start() {
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> logger.error("Uncaught exception happened!", e));
    if (specialGame.isSpecialGame()) {
      fileWarApplication.start();
    } else {
      inputWarApplication.start();
    }
  }
}
