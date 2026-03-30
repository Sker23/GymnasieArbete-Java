package main;

import gameView.GameView;

public class Game {

	public static void main(String[] args){
		GameView gw = new GameView(800, 600, "Game");
		GameModel gm = new GameModel(gw);
		GameController gc = new GameController(gw, gm);
		gc.gameLoop();
	}
}
