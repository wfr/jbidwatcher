package com.jbidwatcher.ui;

import static org.junit.Assert.assertTrue;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.ApplicationLauncher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.jbidwatcher.app.JBidWatch;

public abstract class ApplicationTestBase {

  private Robot robot;
  
  protected FrameFixture mainFrameFixture;
  
  @BeforeClass
  public static void setUpOnce() {
    // we have an EDT violation in the runtime code, so need to fix that before we can uncomment this...
    FailOnThreadViolationRepaintManager.install();
  }
  
  @Before
  public void setup() throws IOException, InterruptedException {
    // set up clean configuration directory, so it doesn't interfere with any other install of JBidwatcher
    File homeDirectory = File.createTempFile("jbidwatchertesthome", null);
    homeDirectory.delete();
    assertTrue(homeDirectory.mkdirs());
    
    // ensure proper cleanup at test exit
    homeDirectory.deleteOnExit();
    
    // change home directory in JVM, because that is what JBidwatcher uses to construct it's data directory
    System.setProperty("user.dir", homeDirectory.getAbsolutePath());
    
    final File configDirectory = new File(homeDirectory, ".jbidwatcher");
    assertTrue(configDirectory.mkdirs());
    
    // call setup strategy from implementor
    setupApplicationTest(configDirectory);
    
    robot = BasicRobot.robotWithNewAwtHierarchy();
    
    // launch JBidwatcher, making sure to use the new custom user directory
    ApplicationLauncher.application(JBidWatch.class).withArgs("--usb").start();
    
    // should wait for the JBidwater frame to appear before returning
    //Thread.sleep(10000);
    mainFrameFixture = WindowFinder.findFrame(new GenericTypeMatcher<Frame>(Frame.class) {
      @Override
      protected boolean isMatching(Frame frame) {
        return "JBidwatcher".equals(frame.getTitle()) && frame.isShowing();
      }
    }).using(robot);
  }
  
  protected abstract void setupApplicationTest(File configurationDirectory);
  
  @After
  public void teardown() {
    robot.cleanUp();
  }
}
