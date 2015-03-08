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
      // TODO - make this platform agnostic - rsync may not exist on Mac or Windows
      // TODO - pull from local test-resources
      final String toRun = "rsync -av /home/traack/.jbidwatcher/ " + configurationDirectory.getAbsolutePath() + "/";
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
