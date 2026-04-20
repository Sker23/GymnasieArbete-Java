package weapons;

import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JFrame;

public class Spear extends Weapon{
	private boolean action = false;
	int centerX;
	int centerY;

	public Spear(Image image, double xPos, double yPos, JFrame frame) {
		super(image, xPos, yPos);
		
		Point window = frame.getLocationOnScreen();
		int centerX = window.x + frame.getWidth() / 2;
		int centerY = window.y + frame.getHeight() / 2;
		
	}
	
	public void update(double xPos, double yPos, long deltatime) {
		this.xPos = xPos;
		this.yPos = yPos;
		
		Point mousePoint = MouseInfo.getPointerInfo().getLocation();
		double x = mousePoint.getX();
		double y = mousePoint.getY();
		
		double deltaX = x-centerX;
		double deltaY = y-centerY;
		
		
	}

	@Override
	public void action() {
		
		
	}
	
	public void setAction() {
		action = true;
	}	
}
