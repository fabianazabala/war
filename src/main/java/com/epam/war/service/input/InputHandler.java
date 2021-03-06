package com.epam.war.service.input;

import com.epam.war.domain.DeckSize;
import com.epam.war.service.screen.Usage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputHandler {

  private static final Logger logger = LoggerFactory.getLogger(InputHandler.class);
  public static final int MINIMUM_PLAYER_NUMBER = 2;
  public static final int MAXIMUM_PLAYER_NUMBER = 5;

  private final Usage usage;

  public InputHandler(Usage usage) {
    this.usage = usage;
  }

  /**
   * Parses and fixes initial input arguments. If there is an incorrect number of arguments
   * then shows usage screen and exits.
   *
   * @param args string array which contains initial program arguments.
   * @return Input which contains input parameters to use.
   */
  public Input handleArguments(String[] args) {
    if (hasInvalidNumberOfArguments(args)) {
      usage.message();
      System.exit(1);
    }

    boolean invalidPlayerNumber = isInvalidFirstParameter(args[0]);
    boolean invalidDeckSize = isInvalidSecondParameter(args[1]);

    if (invalidPlayerNumber && invalidDeckSize) {
      logger.info("Values were corrected to {} players on a {} deck",
          Input.DEFAULT_PLAYER_NUMBER, Input.DEFAULT_DECK_SIZE);
      return Input.create();
    } else if (invalidPlayerNumber) {
      logger.info("Number of players set to {}, you provided unparseable input {}",
          Input.DEFAULT_PLAYER_NUMBER, args[0]);
      return Input.create(DeckSize.fromCode(args[1]));
    }

    int playerNumber = correctFirstParameter(Integer.parseInt(args[0]));
    if (invalidDeckSize) {
      logger.info("Deck set to {}, you provided unrecognized value: {}",
          Input.DEFAULT_DECK_SIZE, args[1]);
      return Input.create(playerNumber);
    } else {
      return Input.create(playerNumber, DeckSize.fromCode(args[1]));
    }
  }

  private int correctFirstParameter(int parameter) {
    int newValue = parameter;
    if (parameter < MINIMUM_PLAYER_NUMBER) {
      newValue = MINIMUM_PLAYER_NUMBER;
    }

    if (parameter > MAXIMUM_PLAYER_NUMBER) {
      newValue = MAXIMUM_PLAYER_NUMBER;
    }

    if (newValue != parameter) {
      logger.info("Number of players corrected to {}, you provided {}",
          newValue,
          parameter);
    }
    return newValue;
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
