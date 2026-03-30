package main;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import entities.Entity;
import entities.PlayerEntity;
import gameView.GameView;
import levelCreator.MapMaker;
import tiles.TileLoader;
import tiles.TileManager;
import tiles.TileMapBuilder;

public class GameModel {
	private PlayerEntity player;//spelare
	private ArrayList<Entity> spriteList = new ArrayList<>();//sparar entities
	public Image background;
	
	private TileMapBuilder tileM;
	private TileLoader tiles;
	private CollisionChecker cChecker  = new CollisionChecker(this);
	private MapMaker dungeonMap;
	
	public GameModel(GameView gameScreen) {;
		
		loadTiles(gameScreen);
		loadImages(gameScreen);
	}
	
	private void loadTiles(GameView gameScreen) {
		dungeonMap = new MapMaker(16, 16);
		
		tiles = new TileLoader(gameScreen);
		tileM = new TileMapBuilder(dungeonMap.getMap(), gameScreen, tiles.getTiles());
	}
	
	private void loadImages(GameView gameScreen){
		Image ghostImg = new ImageIcon(getClass().getResource("/playerImg.png")).getImage();
		Image shipImg = new ImageIcon(getClass().getResource("/ship.png")).getImage();
		background = new ImageIcon(getClass().getResource("/background4a.png")).getImage();
		
		double x = dungeonMap.getStart().getCol()*tileM.getRoomCol()*32+96;
		double y = dungeonMap.getStart().getRow()*tileM.getRoomRow()*32+96;
		
		player = new PlayerEntity(ghostImg, x, y, 150);

		spriteList.add(player);
		spriteList.add(new PlayerEntity(shipImg, x, y, 100));

		x = dungeonMap.getEnd().getCol()*tileM.getRoomCol()*32+96;
		y = dungeonMap.getEnd().getRow()*tileM.getRoomRow()*32+160;
		spriteList.add(new PlayerEntity(shipImg, x, y, 100));
	}//laddar in bilder och skapar allt annat entitiesen behöver för att sparas i spritelisten
	
	public void update(long deltaTime) {
		//flyttar alla entities i spriteList enligt deras move alghoritm/funktion
		for (Entity entity : spriteList) {
			entity.move(deltaTime, cChecker);
		}
	}
	
	public PlayerEntity getPlayer() {
		return player;
	}
	
	public ArrayList<Entity> getSprites() {
		return spriteList;
	}
	
	public Image getBackground() {
		return background;
	}
	
	public TileMapBuilder getMap() {
		return tileM;
	}
	
 }
