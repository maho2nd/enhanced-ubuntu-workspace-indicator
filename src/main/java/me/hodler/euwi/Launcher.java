package me.hodler.euwi;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.io.IOException;

/**
 * Dependency: - wmctrl
 **/
public class Launcher {

	public static void main(String[] args) throws InterruptedException,
			AWTException, IOException {

		if (!SystemTray.isSupported()) {
			throw new RuntimeException("Tray icons is not supported");
		}

		WorkspaceManager workspaceManager = new WorkspaceManager();
		workspaceManager.loadSettings();

		WorkspaceIndicator workspaceIndicator = new WorkspaceIndicator(
				workspaceManager.getHorizontalWorkspaces(),
				workspaceManager.getVerticalWorkspaces());
		
		workspaceIndicator.initialize();

		int previousSelectedWorkspace = 0; 
		
		while (true) {
			
			workspaceManager.loadSettings();
			
			int selectedWorkspace = workspaceManager.getSelectedWorkspace();
			
			if(selectedWorkspace != previousSelectedWorkspace){
				workspaceIndicator.workspaceChanged(selectedWorkspace);
				previousSelectedWorkspace = selectedWorkspace;
			}
			
			Thread.sleep(100);
		}
	}
}
