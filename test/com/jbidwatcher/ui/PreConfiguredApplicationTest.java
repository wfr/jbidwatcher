package com.jbidwatcher.ui;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class PreConfiguredApplicationTest extends ApplicationTestBase {
  
  @Override
  protected void setupApplicationTest(File configurationDirectory) {
    // install pre-configured settings
    try {
      final String toRun = "rsync -av /home/traack/.jbidwatcher.working/ " + configurationDirectory.getAbsolutePath() + "/";
      Runtime.getRuntime().exec(toRun);
    } catch (IOException e) {
      throw new RuntimeException("Could not copy pre-configured jbidwatcher configuration directory", e);
    }
  }

  @Test
  public void canSeeMainWindowWithCommonFeatures() {
    assertThat(mainFrameFixture).isNotNull();  
  }
}
