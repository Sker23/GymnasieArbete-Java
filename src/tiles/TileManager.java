package tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import gameView.Drawable;
import gameView.GameView;

public class TileManager implements Drawable{
	private int x;
	private int y;
	private int sizeRow;
	private int sizeCol;
	
	public Tile tile[];
	public GameView gw;
	
	private int tileSize = 32;
	
	private BufferedImage tileset;
	private int mapTileNum[][];

	public TileManager(GameView gw, Tile tile[]) {
		this.gw = gw;
		sizeRow = 16;
		sizeCol = 32;
		
		mapTileNum = new int[sizeRow][sizeCol];
		
		this.tile = tile;
		loadMap("/maps/TestMap_01.txt");
	}
	
	private void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			for (int row = 0; row < sizeRow; row++) {
				String line = br.readLine();
				
				String[] numbers = line.trim().split("\\s+");
				
				for (int col = 0; col < sizeCol; col++) {
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[row][col] = num;
				}
			}
			
			br.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void draw(Graphics2D g) {
		for (int row = 0; row < sizeRow; row++) {
			for (int col = 0; col < sizeCol; col++) {
				
				int index = mapTileNum[row][col];
				
				x = tileSize*col;
				y = tileSize*row;
				
				if (x > gw.getCamera().getXPos()-tileSize && 
						x < gw.getWidth()+gw.getCamera().getXPos() &&
						y > gw.getCamera().getYPos()-tileSize &&
						y < gw.getHeight()+gw.getCamera().getYPos()) {
					g.drawImage(tile[index].image, x, y, null);
				}
			}
		}
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public int[][] getMapTileNum() {
		return mapTileNum;
	}
	
	public Tile[] getTiles() {
		return tile;
	}
}
