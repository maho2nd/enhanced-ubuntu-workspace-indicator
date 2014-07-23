package me.hodler.euwi;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Formatter;

public class WorkspaceManager {

  private static String infoCommand = "wmctrl -d";
  private static String switchCommand = "wmctrl -o %d,%d";
  private Formatter formatter = new Formatter();
  private int virtualResolutionWidth;
  private int virtualResolutionHeight;
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
    return virtualResolutionHeight / screenHeight;
  }

  public int getSelectedWorkspace() {

    int currentColumn = ((getHorizontalWorkspaces() + 1) - ((virtualResolutionWidth - xCurrent) / screenWidth));

    int currentRow = ((getVerticalWorkspaces() + 1) - ((virtualResolutionHeight - yCurrent) / screenHeight));

    return (currentColumn * getVerticalWorkspaces())
        - (getVerticalWorkspaces() - currentRow);
  }
  
  public void switchWorkspace(int workspaceNo) throws IOException{

    int row = 1;
    int col = 1;
    
    if(workspaceNo < getHorizontalWorkspaces()){
      col = workspaceNo;
    }else{
      col = (workspaceNo % getHorizontalWorkspaces());
      row = (int)Math.ceil(1.0 * workspaceNo / getHorizontalWorkspaces());
      
      if(col == 0){
        col = getHorizontalWorkspaces();
      }
    }
    
    int xPosition = (col-1) *  screenWidth;
    int yPosition = (row-1) *  screenHeight;
    
    String command = formatter.format(switchCommand, xPosition, yPosition).toString();
    Runtime.getRuntime().exec(command);
  }

  public void loadSettings() throws IOException {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    screenWidth = (int) screenSize.getWidth();
    screenHeight = (int) screenSize.getHeight();

    Process child = Runtime.getRuntime().exec(infoCommand);
    InputStream in = child.getInputStream();

    String outString = new BufferedReader(new InputStreamReader(in)).readLine();

    String virtualResolution = outString.split(" ")[4];
    virtualResolutionWidth = Integer.parseInt(virtualResolution.split("x")[0]);
    virtualResolutionHeight = Integer.parseInt(virtualResolution.split("x")[1]);

    String currentPosition = outString.split(" ")[7];
    xCurrent = Integer.parseInt(currentPosition.split(",")[0]);
    yCurrent = Integer.parseInt(currentPosition.split(",")[1]);
  }

}
