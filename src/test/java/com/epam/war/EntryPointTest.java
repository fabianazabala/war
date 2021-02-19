package com.epam.war;

import org.testng.annotations.Test;

public class EntryPointTest {

  @Test
  public void givenSpecialFile_thenGameIsPlayed() {
    EntryPoint.main(new String[] {"4", "small"});
  }
}