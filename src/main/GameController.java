package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import entities.PlayerEntity;
import gameView.Camera;
import gameView.GameView;

public class GameController implements KeyListener{

	private boolean gameRunning = true;
	private long lastUpdateTime;

	private GameView gameScreen;
	private GameModel gameModel;
	private HashMap<String, Boolean> keyDown = new HashMap<>();

	public GameController(GameView gs, GameModel gameModel){
		this.gameScreen = gs;
		this.gameModel = gameModel;
		gameScreen.setKeyListener(this);

		keyDown.put("left", false); 
		keyDown.put("right", false);
		keyDown.put("down", false); 
		keyDown.put("up", false);
		keyDown.put("escape", false);
	}//sätter gameview gs till gameScreen variablen här
	//Sätter även upp hashmap som ska hålla koll om en nyckel är nedtryckt eller inte.

	public void update(long deltaTime){
		//Hämtar de klasser som ska uppdateras
		PlayerEntity player = gameModel.getPlayer();
		Camera camera = gameScreen.getCamera();
		
		/*
		 * Tar hand om förflyttning i x-led
		 * tangent right(d) nedtryck sätter direction till 1 och gör att karaktären föflyttas till höger
		 * tangent left(a) nedtryck, direction = -1, karaktären rör sig till vänster
		 * */
		if (keyDown.get("right")) {
			player.setDirectionX(1);
		} else if(keyDown.get("left")) {
			player.setDirectionX(-1);
		} else {
			player.setDirectionX(0);
		}
		
		/**hopp*/
		if (keyDown.get("up") ) {
			player.setJump(true);
		} else {
			player.setJump(false);
		}
		
		//flyttar alla entities i spriteList enligt deras move alghoritm/funktion
		gameModel.update(deltaTime);

		//updaterar kamera
		camera.update(player.getX(), player.getY());
		//System.out.println("xPos: " + camera.getXPos());
		//System.out.println("yPos: " + camera.getYPos());
		
		if (keyDown.get("escape")) {
			System.exit(0);
		}
	}//uppdateringsmetoden sköter alla ändringar, men inga som man kan se, anropas vid varje gameloop
	
	public void render(){
		//startar upp ritning
		gameScreen.beginRender();
		
		//lägger in allt man vill rita
		gameScreen.openRender(gameModel.getMap());
		gameScreen.openRender(gameModel.getSprites());
		
		//visa det som ritas
		gameScreen.show();
	}//renderar allt som behövs renderas, anropas vid varje gameloop

	public void gameLoop(){
		int fps = 60;//antalet fps som man ska som mest ha
		int updateTime = (int)(1.0/fps*1000000000.0);

		lastUpdateTime = System.nanoTime();

		while(gameRunning){
			long deltaTime = System.nanoTime() - lastUpdateTime;
			if(deltaTime > updateTime){
				lastUpdateTime = System.nanoTime();
				update(deltaTime);
				render();
			}
		}
	}//loop av spelet


	/** Spelets tangentbordslyssnare */
	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_D) {
			keyDown.put("right", true);
		}
		if(key == KeyEvent.VK_A) {
			keyDown.put("left", true);
		}
		
		if(key == KeyEvent.VK_W) {
			keyDown.put("up", true);
		}

		if (key == KeyEvent.VK_ESCAPE) {
			keyDown.put("escape", true);
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_D) {
			keyDown.put("right", false);
		}
		if(key == KeyEvent.VK_A) {
			keyDown.put("left", false);
		}
		
		if(key == KeyEvent.VK_W) {
			keyDown.put("up", false);
		}
	}
}