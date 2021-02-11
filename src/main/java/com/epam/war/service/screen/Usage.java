package com.epam.war.service.screen;

public class Usage {

  public void message() {
    System.out.println("First parameter is the number of players. Second is the type of the deck " +
        "(small - 24 cards, large - 52 cards, no other options are possible).\n" +
        "    Not exactly two parameters given? Program then prints this message and exits.\n" +
        "    Bad parameters given? Program corrects them to default values (2 and SMALL) and proceeds.\n" +
        "    You can write small, SmALl or SMALL (same with large) - case matters not.\n" +
        "    Valid integers for players: 1, 2, 3, 4, 5. Anything larger becomes a 5. " +
        "Anything smaller becomes a 1.");
  }
}
