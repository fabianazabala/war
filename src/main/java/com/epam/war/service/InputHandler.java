package com.epam.war.service;

import com.epam.war.domain.DeckSize;
import com.epam.war.service.screen.Usage;

public class InputHandler {

  private final Usage usage;

  public InputHandler(Usage usage) {
    this.usage = usage;
  }

  public Input handleArguments(String[] args) {
    if (hasInvalidNumberOfArguments(args)
        || isInvalidFirstParameter(args[0])
        || isInvalidSecondParameter(args[1])) {
      usage.usageMessage();
      System.exit(1);
    }

    return new Input(Integer.parseInt(args[0]), DeckSize.fromCode(args[1]));
  }


  private boolean hasInvalidNumberOfArguments(String[] args) {
    return args.length != 2;
  }

  private boolean isInvalidFirstParameter(String parameter) {
    try {
      Integer.parseInt(parameter);
      return false;
    } catch (NumberFormatException e) {
      return true;
    }
  }

  private boolean isInvalidSecondParameter(String parameter) {
    return !(parameter.equalsIgnoreCase("small")
        || parameter.equalsIgnoreCase("large"));
  }
}
