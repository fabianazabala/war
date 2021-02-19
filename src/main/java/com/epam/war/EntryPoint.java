package com.epam.war;

import com.epam.war.service.SpecialGame;
import com.epam.war.service.application.EntryWarApplication;
import com.epam.war.service.application.FileWarApplication;
import com.epam.war.service.application.InputWarApplication;

public class EntryPoint {

  public static void main(String[] args) {
    SpecialGame specialGame = new SpecialGame();
    EntryWarApplication entryWarApplication = new EntryWarApplication(specialGame,
        new FileWarApplication(specialGame),
        new InputWarApplication(args));
    entryWarApplication.start();
  }
}