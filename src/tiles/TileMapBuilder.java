package tiles;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import gameView.Drawable;
import gameView.GameView;
import helpClasses.Transform2DArrays;

public class TileMapBuilder implements Drawable{
	private Tile[] tiles;
	private int[][] tileMap;
	private GameView gw;

	private int roomRow;
	private int roomCol;
	private int tileSize = 32;


	public TileMapBuilder(String map[][], GameView gw, Tile[] tiles) {
		this.gw = gw;
		this.tiles = tiles;

		mapSize(map);
		tileMap = new int[map.length*roomRow][map[0].length*roomCol];

		buildTileMap(map);
	}

	private void mapSize(String[][] map) {
		ArrayList<String> lines = new ArrayList<>();

		String filePath = "/maps/Room_" + map[0][0] + ".txt";

		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;

			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			String lastLine = lines.get(lines.size() - 1);
			String[] numbers = lastLine.trim().split("\\s+");

			br.close();

			roomRow = lines.size();
			roomCol = numbers.length;
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void buildTileMap(String[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j].equals("0")) {
					placeTiles(Integer.parseInt(map[i][j]), i, j);
				} else {		
					String[] type = map[i][j].split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

					placeTiles(type[0], Integer.parseInt(type[1]), i, j);

				}
			}
		}
	}

	private void placeTiles(String type, int transform, int curR, int curC) {
		String filePath = "/maps/Room_" + type + ".txt";
		int[][] tempMap = new int[roomRow][roomCol];

		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			
			for (int row = 0; row < roomRow; row++) {
				String line = br.readLine();

				String[] numbers = line.trim().split("\\s+");

				for (int col = 0; col < roomCol; col++) {
					int num = Integer.parseInt(numbers[col]);
					
					tempMap[row][col] = num;
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("placeTiles tempMap");
		}

		switch (transform) {
		case 1://inget händer
			break;
		case 2://vänster och höger bytar plats, ex om det finns 5 kolumner blir kolumn 1 till 5 och 5 till 1
			Transform2DArrays.swapLeftRight(tempMap);
			break;
		case 3://topp och botten bytar plats, ex om det finns 5 rader blir rad 1 till 5 och 5 till 1
			Transform2DArrays.swapTopBottom(tempMap);
			break;
		case 4://bytar plats på vänster och höger, men också topp och botten.
			Transform2DArrays.swapAll(tempMap);
			break;
		default:
			break;
		}

		try {
			for (int i = 0; i < roomRow; i++) {
				for (int j = 0; j < roomCol; j++) {
					tileMap[i+curR*roomRow][j+curC*roomCol] = tempMap[i][j];
				}
			}
		} catch (Exception e) {
			
			System.out.println("Roomrow: " + roomRow);
			System.out.println("Roomcol: " + roomCol);
			System.out.println(e);
		}
	}

	private void placeTiles(int type, int curR, int curC) {
		String filePath = "/maps/Room_" + type + ".txt";
		int[][] tempMap = new int[roomRow][roomCol];

		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			for (int row = 0; row < roomRow; row++) {
				String line = br.readLine();

				String[] numbers = line.trim().split("\\s+");

				for (int col = 0; col < roomCol; col++) {
					int num = Integer.parseInt(numbers[col]);

					tempMap[row][col] = num;
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("placeTiles");
		}

		for (int i = 0; i < roomRow; i++) {
			for (int j = 0; j < roomCol; j++) {
				tileMap[i+curR*roomRow][j+curC*roomCol] = tempMap[i][j];
			}
		}	
	}

	public void draw(Graphics2D g) {
		int leftCol = gw.getCamera().getXPos()/tileSize;
		int rightCol = (gw.getCamera().getXPos()+gw.getWidth())/tileSize;
		int topRow = gw.getCamera().getYPos()/tileSize;
		int bottomRow = (gw.getCamera().getYPos()+gw.getHeight())/tileSize;

		for (int row = topRow; row <= bottomRow; row++) {
			for (int col = leftCol; col <= rightCol; col++) {
				int x = tileSize*col;
				int y = tileSize*row;

				int index = tileMap[row][col];
				g.drawImage(tiles[index].image, x, y, null);

			}
		}
	}

	public int getTileSize() {
		return tileSize;
	}

	public int[][] getTileMap() {
		return tileMap;
	}

	public Tile[] getTiles() {
		return tiles;
	}
	
	public int getRoomRow() {
		return roomRow;
	}
	
	public int getRoomCol() {
		return roomCol;
	}
}
