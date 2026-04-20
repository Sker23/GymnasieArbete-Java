package weapons;

import java.awt.Graphics2D;
import java.awt.Image;

import gameView.Drawable;

public abstract class Weapon implements Drawable{
	private Image image;           // Sprite/Bild
	protected double xPos, yPos;   // Positionen
	

	public Weapon(Image image, double xPos, double yPos) {
		this.image = image;
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public abstract void action();
	
	public void draw(Graphics2D g) {
		g.drawImage(image,(int)xPos,(int)yPos,null);
		
		//g.setColor(Color.red);
		//g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}
}
