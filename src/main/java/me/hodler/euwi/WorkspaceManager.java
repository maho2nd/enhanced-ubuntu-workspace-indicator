package me.hodler.euwi;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WorkspaceManager {

	private static String command = "wmctrl -d";
	private int virtualResolutionWidth;
	private int VirtualResolutionHeight;
	private int xCurrent;
	private int yCurrent;
	private int screenWidth;
	private int screenHeight;

	public WorkspaceManager() {
	}

	public int getHorizontalWorkspaces() {
		return virtualResolutionWidth / screenWidth;
	}

	public int getVerticalWorkspaces() {
		return VirtualResolutionHeight / screenHeight;
	}

	public int getSelectedWorkspace() {

		int currentColumn = ((getHorizontalWorkspaces() + 1) - ((virtualResolutionWidth - xCurrent) / screenWidth));
		
		int currentRow = ((getVerticalWorkspaces() + 1) - ((VirtualResolutionHeight- yCurrent) / screenHeight));
		
		return (currentColumn * getVerticalWorkspaces()) - (getVerticalWorkspaces() -currentRow);
	}

	public void loadSettings() throws IOException {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();

		Process child = Runtime.getRuntime().exec(command);
		InputStream in = child.getInputStream();

		String outString = new BufferedReader(new InputStreamReader(in))
				.readLine();

		String virtualResolution = outString.split(" ")[4];
		virtualResolutionWidth = Integer.parseInt(virtualResolution.split("x")[0]);
		VirtualResolutionHeight = Integer.parseInt(virtualResolution.split("x")[1]);

		String currentPosition = outString.split(" ")[7];
		xCurrent = Integer.parseInt(currentPosition.split(",")[0]);
		yCurrent = Integer.parseInt(currentPosition.split(",")[1]);
	}

}
