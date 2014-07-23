package me.hodler.euwi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;

public class WorkspaceIcon {

	private final TrayIcon trayIcon;
	private final BufferedImage bufferedImage;
	private final int offsetY;
	private final int width;
	private final int height;
	private final Color lineColor;
	private final Color selectedColor;
	private final Color deselectedColor;
	private boolean selected = false;

	public WorkspaceIcon(TrayIcon trayIcon, BufferedImage bufferedImage,
			Color lineColor, Color selectedColor, Color deselectedColor,
			int width, int height, int offsetY) {

		this.trayIcon = trayIcon;
		this.bufferedImage = bufferedImage;
		this.selectedColor = selectedColor;
		this.deselectedColor = deselectedColor;
		this.lineColor = lineColor;

		this.width = width;
		this.height = height;
		this.offsetY = offsetY;
	}

	public void select() {
		if (!this.selected) {
			this.selected = true;
			drawWorkspace(selectedColor);
		}
	}
	
	public void deselect(){
		if(this.selected){
			this.selected = false;
			drawWorkspace(deselectedColor);
		}
	}
	
	public void draw(){
		if(this.selected){
			drawWorkspace(selectedColor);
		}else{
			drawWorkspace(deselectedColor);
		}
	}
	
	private void drawWorkspace(Color backgroundColor){
		
		Graphics g = bufferedImage.getGraphics();

		// draw workspace rectangle
		g.setColor(backgroundColor);
		g.fillRect(0, offsetY, width, height);
		
		// draw border top
		g.setColor(lineColor);
		g.drawLine(0, offsetY, width, offsetY);
		
		// draw border bottom
		g.drawLine(0, offsetY+height, width, offsetY+height);
		
		g.finalize();
		this.bufferedImage.flush();
		this.trayIcon.setImage(bufferedImage);
	}
}
