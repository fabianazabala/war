package com.epam.war.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class SpecialGame {

  /**
   * Retrieves {@link Path} of special file.
   *
   * @return Optional with Path of special file, or empty if there's none.
   * @throws URISyntaxException
   * @throws IOException
   */
  public static Optional<Path> getSpecialFile() throws URISyntaxException, IOException {
    return Files.walk(Paths.get(SpecialGame.class.getResource("/").toURI()), 1)
        .filter(f -> f.getFileName().toString().startsWith("ssc") && f.getFileName().toString().endsWith(".json"))
        .findFirst();
  }

  /**
   * Checks whether current game is a special game (using input ssc file) or a normal game.
   *
   * @return boolean
   */
  public static boolean isSpecialGame() {
    try {
      return getSpecialFile().isPresent();
    } catch (URISyntaxException | IOException e) {
      return false;
    }
  }
}
