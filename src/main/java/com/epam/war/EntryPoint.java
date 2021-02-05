package com.epam.war;

import com.epam.war.service.Input;
import com.epam.war.service.InputHandler;
import com.epam.war.service.screen.Usage;

public class EntryPoint {

  public static void main(String[] args) {
    Usage usage = new Usage();
    InputHandler inputHandler = new InputHandler(usage);

    Input input = inputHandler.handleArguments(args);
    //inicializar un deck generator dependiento del deck size que me pasaron


  }
}