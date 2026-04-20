package entities;

import java.awt.Image;

import main.CollisionChecker;

public abstract class Enemy extends Entity{
	protected int hp;
	protected int contactDmg;
	protected int weaponDmg;
	
	public Enemy(Image image, double xPos, double yPos, int speed) {
		super(image, xPos, yPos, speed);
	}
	
	public abstract void patroll();
	
	public abstract void chase();

	protected void scan() {
		
	}
}
