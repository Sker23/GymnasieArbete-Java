package entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import gameView.Drawable;
import main.CollisionChecker;

public abstract class Entity implements Drawable {
	private Image image;           // Sprite/Bild
	protected double xPos, yPos;   // Positionen
	protected int dx = 0; // Rörelseriktning
	protected int speed; // Hastighet i px/sekund x-led
	private boolean active = true; // Gör alla nya objekt aktiva.
	protected double gravity = 1000;  //acceleration neråt
	protected double velocityY, velocityX; //Hastighet i px/sekund
	protected boolean collisionY = false, collisionX; //Om entitien kolliderar med en tile
	private Rectangle hitbox = null;  //kollisions area
	protected boolean isOnFloor = false; //om entity är på marken

	/**
	 * Konstruktor
	 */
	public Entity (Image image, double xPos, double yPos, int speed){
		this.image = image;   
		this.xPos = xPos;
		this.yPos = yPos;
		this.speed = speed;

		hitbox = new Rectangle((int)xPos, (int)yPos, image.getWidth(null), image.getHeight(null));
	}

	/**
	 * Ritar bilden på ytan g
	 */
	public void draw(Graphics2D g) {
		g.drawImage(image,(int)xPos,(int)yPos,null);
		
		//g.setColor(Color.red);
		//g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}

	/**
	 * Vilken riktning i x-led
	 * @param dx 0 = stilla, 1 = höger, -1 = vänster
	 */
	public void setDirectionX(int dx){
		this.dx = dx;
	}

	/**
	 * Metod som gör förflyttningen, dvs ändrar xPos och yPos
	 * Måste skapas i klasser som ärver entity
	 * @param antal nanosekunder sedan förra anropet 
	 */
	public abstract void move(long deltaTime, CollisionChecker cChecker);

	/**
	 * Returner entitiens xPos
	 */
	public double getX() {
		return xPos;
	}

	public void setX(double x) {
		this.xPos = x;
	}

	/**
	 * Returner entitiens yPos
	 */
	public double getY() {
		return yPos;
	}

	public void setY(double y) {
		this.yPos = y;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	/**
	 * Returner bildens höjd
	 */
	public double getHeight() {
		return image.getHeight(null);
	}

	/**
	 * Returenerar bildens längd
	 * 
	 */
	public double getWidth() {
		return image.getWidth(null);
	}
	
	public void setCollisionX(boolean collisionX) {
		this.collisionX = collisionX;
	}
	public void setCollisionY(boolean collisionY) {
		this.collisionY = collisionY;
	}
	
	public void setIsOnFloor() {
		isOnFloor = true;
	}
	
	public double getVelocityX() {
		return velocityX;
	}
	
	/**
	 * Returnerar entitetens omslutande rektangel/dess kollisions area.
	 */
	public Rectangle getHitbox(){
		hitbox.setLocation((int)xPos, (int)yPos);
		return hitbox;
	}
	
	public void hitboxSetPos() {
		hitbox.x = (int) xPos;
		hitbox.y = (int) yPos;
	}

}
