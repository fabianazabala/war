package com.epam.war.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class SpecialGame {

  public static Optional<Path> getSpecialFile() throws URISyntaxException, IOException {
    return Files.walk(Paths.get(SpecialGame.class.getResource("/").toURI()), 1)
        .filter(f -> f.getFileName().toString().startsWith("ssc") && f.getFileName().toString().endsWith(".json"))
        .findFirst();
  }

  public static boolean isSpecialGame() {
    try {
      return getSpecialFile().isPresent();
    } catch (URISyntaxException | IOException e) {
      return false;
    }
  }
}
