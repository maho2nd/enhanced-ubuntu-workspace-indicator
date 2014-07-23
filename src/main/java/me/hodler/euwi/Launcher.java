package me.hodler.euwi;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.io.IOException;

/**
 * Dependency: - wmctrl
 **/
public class Launcher implements WorkspaceIconListener, Runnable{

  private final WorkspaceManager workspaceManager;
  private final WorkspaceIndicator workspaceIndicator;

  public Launcher() throws IOException, AWTException {
    this.workspaceManager = new WorkspaceManager();

    this.workspaceManager.loadSettings();

    this.workspaceIndicator = new WorkspaceIndicator(
        workspaceManager.getHorizontalWorkspaces(),
        workspaceManager.getVerticalWorkspaces());

    workspaceIndicator.initialize();
    workspaceIndicator.addWorkspaceClickedListener(this);
  }

  public void run() {

    int previousSelectedWorkspace = 0;

    while (true) {

      try {
        workspaceManager.loadSettings();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

      int selectedWorkspace = workspaceManager.getSelectedWorkspace();

      if (selectedWorkspace != previousSelectedWorkspace) {
        workspaceIndicator.workspaceChanged(selectedWorkspace);
        previousSelectedWorkspace = selectedWorkspace;
      }

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException,
      AWTException, IOException {

    if (!SystemTray.isSupported()) {
      throw new RuntimeException("Tray icons is not supported");
    }
    
    new Thread(new Launcher()).start();
  }

  @Override
  public void workspaceClicked(int workspaceNo) {
    try {
      this.workspaceManager.switchWorkspace(workspaceNo);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
