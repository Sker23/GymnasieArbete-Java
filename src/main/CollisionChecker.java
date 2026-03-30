package main;

import java.awt.Rectangle;

import entities.Entity;
import tiles.Tile;

public class CollisionChecker {
	GameModel gm;

	public CollisionChecker(GameModel gm) {
		this.gm = gm;
	}

	/*
	 * Kollisionhanterare mellan entity och tile
	 * @param Entity som checkas, nx som är nya position x för entity, ny som är nya position y för entity
	 *  */
	public void tileCollision(Entity entity, double nx, double ny){
		
		Rectangle hitbox = entity.getHitbox();
		int tileSize = gm.getMap().getTileSize();
		Tile[] tiles = gm.getMap().getTiles();

		int rightCol = (int) ((hitbox.x+hitbox.getWidth()-1)/tileSize);
		int leftCol = (int) ((hitbox.x/tileSize));
		int bottomRow = (int) ((hitbox.y+hitbox.getHeight()-1)/tileSize);
		int topRow = (int) ((hitbox.y/tileSize));

		int tileNum1, tileNum2;

		if (nx > entity.getX()) {
			int newRightCol = (int) ((nx+hitbox.getWidth()-1)/tileSize);
			
			tileNum1 = gm.getMap().getTileMap()[topRow][newRightCol];
			tileNum2 = gm.getMap().getTileMap()[bottomRow][newRightCol];
			
			if (tiles[tileNum1].collision || tiles[tileNum2].collision) {
				entity.setCollisionX(true);
			}
		} else if (nx < entity.getX()) {
			int newLeftCol = (int) (nx/tileSize);

			tileNum1 = gm.getMap().getTileMap()[topRow][newLeftCol];
			tileNum2 = gm.getMap().getTileMap()[bottomRow][newLeftCol];
			
			if (tiles[tileNum1].collision || tiles[tileNum2].collision ) {
				entity.setCollisionX(true);
			}
		}

		if(ny > entity.getY()) {
			int newBottomRow = (int) ((ny+hitbox.getHeight())/tileSize);
			
			tileNum1 = gm.getMap().getTileMap()[newBottomRow][rightCol];
			tileNum2 = gm.getMap().getTileMap()[newBottomRow][leftCol];
			if (tiles[tileNum1].collision || tiles[tileNum2].collision) {
				entity.setCollisionY(true);
				entity.setIsOnFloor();
				entity.setY(newBottomRow*tileSize-hitbox.height);
			}
		} else if (ny < entity.getY()) {
			int newTopRow =(int) ((ny/tileSize));	
			
			tileNum1 = gm.getMap().getTileMap()[newTopRow][rightCol];
			tileNum2 = gm.getMap().getTileMap()[newTopRow][leftCol];
			if (tiles[tileNum1].collision || tiles[tileNum2].collision) {
				entity.setCollisionY(true);
				entity.setY(newTopRow*tileSize+hitbox.height);
			}
		}

	}

}

