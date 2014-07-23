package me.hodler.euwi;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceIndicator {
  private final int horizonalWorkspaces;
  private final int verticalWorkspaces;
  private final SystemTray systemTray;
  private final Color activeWorkspaceColor = Color.decode("#FF4500");
  private final Color inactiveWorkspaceColor = Color.decode("#EEEEEE");
  private final Color lineColor = Color.BLACK;

  private final int width;
  private final int height;

  private List<WorkspaceIcon> workspaceIcons = new ArrayList<WorkspaceIcon>();

  public WorkspaceIndicator(int horizonalWorkspaces, int verticalWorkspaces) {
    systemTray = SystemTray.getSystemTray();

    this.horizonalWorkspaces = horizonalWorkspaces;
    this.verticalWorkspaces = verticalWorkspaces;

    this.width = systemTray.getTrayIconSize().width;
    this.height = systemTray.getTrayIconSize().height;
  }

  public void initialize() throws AWTException {

    for (int x = 1; x <= horizonalWorkspaces; x++) {

      BufferedImage bufferedImage = new BufferedImage(width, height,
          BufferedImage.TYPE_INT_ARGB);

      TrayIcon trayIcon = new TrayIcon(bufferedImage);

      int workspaceIconHeight = height / verticalWorkspaces;

      for (int y = verticalWorkspaces; y >= 1; y--) {

        int yOffset = workspaceIconHeight * (y - 1);
        int curentWorkspaceIconHeight = workspaceIconHeight;

        if (y == verticalWorkspaces) {
          curentWorkspaceIconHeight--;
        }

        WorkspaceIcon workspaceIcon = new WorkspaceIcon(trayIcon,
            bufferedImage, lineColor, activeWorkspaceColor,
            inactiveWorkspaceColor, width, curentWorkspaceIconHeight, yOffset);

        workspaceIcon.draw();
        workspaceIcons.add(0, workspaceIcon);
      }

      systemTray.add(trayIcon);
    }
  }

  public void workspaceChanged(int selectedWorkspace) {

    int currentWorkspace = 1;

    for (WorkspaceIcon workspaceIcon : workspaceIcons) {

      if (currentWorkspace == selectedWorkspace) {
        workspaceIcon.select();
      } else {
        workspaceIcon.deselect();
      }

      currentWorkspace++;
    }
  }
}
