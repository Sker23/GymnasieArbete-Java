package tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gameView.GameView;

public class TileLoader{
	public Tile tile[];
	public GameView gw;
	
	private int tileSize = 32;
	
	private BufferedImage tileset;

	public TileLoader(GameView gw) {
		this.gw = gw;
		
		getTileImage();
	}

	private void getTileImage() {
		try {
			tileset = ImageIO.read(new File("resources/tileset.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int columns = tileset.getWidth(null) / tileSize;
		int rows = tileset.getHeight(null) / tileSize;

		tile = new Tile[columns * rows];

		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				tile[index] = new Tile();

				tile[index].image = tileset.getSubimage(j * tileSize,i * tileSize, tileSize, tileSize);
				
				if (index < 48) {
					tile[index].collision = true;
				}
				index++;			
				
			}
		}
	}
	
	public Tile[] getTiles() {
		return tile;
	}
	
	
	
}
	