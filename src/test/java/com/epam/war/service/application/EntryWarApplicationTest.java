package com.epam.war.service.application;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import com.epam.war.service.SpecialGame;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EntryWarApplicationTest {

  @Mock
  SpecialGame specialGame;
  @Mock
  InputWarApplication inputWarApplication;
  @Mock
  FileWarApplication fileWarApplication;

  EntryWarApplication entryWarApplication;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    entryWarApplication = new EntryWarApplication(specialGame, fileWarApplication, inputWarApplication);
  }

  @Test
  public void givenSpecialGame_thenFileWarApplicationStarts() {
    when(specialGame.isSpecialGame()).thenReturn(true);

    entryWarApplication.start();

    verify(fileWarApplication).start();
  }

  @Test
  public void givenNonSpecialGame_thenInputWarApplicationStarts() {
    when(specialGame.isSpecialGame()).thenReturn(false);

    entryWarApplication.start();

    verify(inputWarApplication).start();
  }
}