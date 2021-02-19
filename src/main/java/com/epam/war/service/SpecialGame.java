package com.epam.war.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class SpecialGame {

  /**
   * Retrieves {@link Path} of special file.
   *
   * @return Optional with Path of special file, or empty if there's none.
   * @throws URISyntaxException
   * @throws IOException
   */
  public Optional<InputFile> getSpecialFile() throws URISyntaxException, IOException {
    return findAllFilesInsideJar()
        .orElseGet(() -> {
          try {
            return findAllFilesUsingIDE();
          } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
          }
        })
        .stream()
        .filter(f -> f.startsWith("ssc_") && f.endsWith(".json"))
        .map(s -> new InputFile(s, SpecialGame.class.getResourceAsStream("/" + s)))
        .findFirst();
  }

  private List<String> findAllFilesUsingIDE() throws IOException, URISyntaxException {
    return Files.walk(Paths.get(SpecialGame.class.getResource("/").toURI()), 1)
        .map(Path::getFileName)
        .map(Path::toString)
        .collect(Collectors.toList());
  }


  private Optional<List<String>> findAllFilesInsideJar() throws IOException {
    List<String> list = new ArrayList<>();
    CodeSource src = getClass().getProtectionDomain().getCodeSource();
    if (src != null) {
      URL jar = src.getLocation();
      ZipInputStream zip = new ZipInputStream(jar.openStream());
      while (true) {
        ZipEntry e = zip.getNextEntry();
        if (e == null) {
          break;
        }
        list.add(e.getName());
      }
    }
    return Optional.of(list).filter(Predicate.not(List::isEmpty));
  }

  /**
   * Checks whether current game is a special game (using input ssc file) or a normal game.
   *
   * @return boolean
   */
  public boolean isSpecialGame() {
    try {
      return getSpecialFile().isPresent();
    } catch (URISyntaxException | IOException e) {
      return false;
    }
  }

  public static final class InputFile {
    private final String fileName;
    private final InputStream inputStream;

    public InputFile(String fileName, InputStream inputStream) {
      this.fileName = fileName;
      this.inputStream = inputStream;
    }

    public String getFileName() {
      return fileName;
    }

    public InputStream getInputStream() {
      return inputStream;
    }
  }
}
