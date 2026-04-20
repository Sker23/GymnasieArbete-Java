package entities;

import java.awt.Image;

import main.CollisionChecker;
import weapons.Spear;

public class PlayerEntity extends Entity {
	private boolean jump = false;	
	private double jumpHeight;
	private double jumpVelocity;
	private Spear spear;

	public PlayerEntity(Image img, double xPos, double yPos, int speed) {
		super(img, xPos, yPos, speed);
		jumpHeight = 100;
		jumpVelocity = -Math.sqrt(2 * gravity * jumpHeight); //räknar ut hur stor hastigheten uppåt är för att den ska hoppa lika högt  oavsett om andra värden än hopphöjden ändras
	}

	/**
	 * Metod som gör förflyttningen, dvs ändrar xPos och yPos
	 * Ärvs från Entity
	 * @param deltatime antal nanosekunder sedan förra anropet 
	 */
	public void move(long deltaTime, CollisionChecker cChecker) {	

		//Hastiget på Förflyttning i x-led
		velocityX = speed*dx;

		//Hastiget på Förflyttning i y-led
		velocityY += gravity*deltaTime/1_000_000_000.0;

		if(jump) {
			if (isOnFloor) {
				velocityY = jumpVelocity;
				isOnFloor = false;
			} 
		}

		double nx = this.xPos + (deltaTime/1_000_000_000.0)*velocityX;
		double ny = this.yPos + velocityY*deltaTime/1_000_000_000.0;

		cChecker.tileCollision(this, nx, ny);

		if (collisionX) {
			dx =0;
			collisionX = false;
		}

		if (collisionY) {
			velocityY = 0;
			collisionY = false;
		}

		//Förflyttning i x-led
		this.xPos = this.xPos + (deltaTime/1_000_000_000.0)*speed*dx;

		//Förflyttning i y-led
		this.yPos += velocityY*deltaTime/1_000_000_000.0;
		
        //System.out.println("PlayerX: " + xPos);
        //System.out.println("PlayerY: " + yPos);

		hitboxSetPos();

	}

	/**
	 * Metod som bestämmer om hopp är sant eller falskt
	 * @param jump är sann eller falsk
	 * */
	public void setJump(boolean jump) {
		this.jump = jump;
	}

}