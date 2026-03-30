package levelCreator;

import java.util.ArrayList;
import java.util.Random;

public class MapMaker {
	private String map[][];
	private int row;
	private int col;
	private int roomCount;

	private ArrayList<Point> rooms;
	private Point start;
	private Point end;

	public MapMaker(int row, int col) {
		this.row = row;
		this.col = col;

		this.roomCount = (row-1)*(col-2)/3;
		map = new String[row][col];

		rooms = new ArrayList<>();

		createMap();
	}

	private void createMap() {
		//sätter alla värden till 0 i arrayen vilket är basrummet
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				map[i][j] = "0";
			}
		}

		generateRooms();
		placeStartAndEnd();
		placeRoomTypes();
	}

	private void generateRooms() {
		map[row/2][col/2] = "1";
		rooms.add(new Point(row/2, col/2));

		int newR = 0;
		int newC = 0;

		Random rand = new Random();

		for (int i = 0; i < roomCount; i++) {
			int randomRoom = rand.nextInt(rooms.size()); //tar ett random rum

			int count = rand.nextInt(4)+1;
			newR = rooms.get(randomRoom).getRow();
			newC = rooms.get(randomRoom).getCol();

			switch (count) {
			case 1:
				newC--;
				break;
			case 2:
				newR--;
				break;
			case 3:
				newC++;
				break;
			case 4:
				newR++;
				break;
			default:
				break;
			}

			//om den är validerad läggs den till som ett rum annars börjar den om och försöker hitta en ny giltig plats
			if (isValid(newR, newC)) {
				map[newR][newC] = "1";
				rooms.add(new Point(newR, newC));
				//System.out.println(i);
			} else {
				i--;
			}
		}
	}

	/*
	 * Validerar om ett nytt rum är godkänt
	 * */
	private boolean isValid(int newR, int newC) {
		if (newR >= row-1 || newR < 1 || newC >= col-2 || newC < 2) {
			return false;
		}

		if (map[newR][newC] != "0") {
			return false;
		}
		
		int maxNeighbours = 1;
		int neighbours = 0;

		if (newR > 0 && map[newR-1][newC] != "0") {
			neighbours++;
		}
		if (newR < row-1 && map[newR+1][newC] != "0") {
			neighbours++;
		}
		if (newC > 0 && map[newR][newC-1] != "0") {
			neighbours++;
		}
		if (newC < col-1 && map[newR][newC+1] != "0") {
			neighbours++;
		}

		if(neighbours > maxNeighbours) {
			return false;
		}
		return true;
	}

	/*
	 * placerar start och slut av banan
	 * */
	private void placeStartAndEnd() {	
		//hittar och sätter ut start
		Point fromCenter = findFarthest(row/2, col/2); //hittar längsta path från center

		if (map[fromCenter.getRow()][fromCenter.getCol()-1] != "1") {
			map[fromCenter.getRow()][fromCenter.getCol()-1] = "S2";
			start = new Point(fromCenter.getRow(),fromCenter.getCol()-1);
		} else {
			map[fromCenter.getRow()][fromCenter.getCol()+1] = "S1";
			start = new Point(fromCenter.getRow(),fromCenter.getCol()+1);
		}

		//hittar och sätter ut slutet baserat på start
		Point fromStart =  findFarthest(start.getRow(), start.getCol()); //hittar längsta path från start

		if (map[fromStart.getRow()][fromStart.getCol()+1] != "1") {
			map[fromStart.getRow()][fromStart.getCol()+1] = "E1";
			end = new Point(fromStart.getRow(),fromStart.getCol()+1);
		} else {
			map[fromStart.getRow()][fromStart.getCol()-1] = "E2";
			end = new Point(fromStart.getRow(),fromStart.getCol()-1);
		}
	}

	/*
	 * hittar punkten längst ifrån förbestämd startpunkt
	 * @param startRow vilket är startpunktens rad, startcol vilket är startpunktens kolumn
	 * */
	private Point findFarthest(int startRow, int startCol) {
		ArrayList<Distance> dist = new ArrayList<>();
		dist.add(new Distance());
		dist.get(0).addRoom(startRow, startCol);

		int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}}; //riktningar som rumen kan befinna sig i vänster, höger, upp och ner
		boolean[][] visited = new boolean[row][col]; //håller koll på rum redan besökta
		visited[startRow][startCol] = true;

		boolean found = false;
		int index = 0; //håller bara koll på hur långt in i arrayen jag är där högre index betyder högre distans från startpunkt

		//letar rum tills den inte kan hitta fler och då är det rum med längst path från start hittad
		while(found == false) {
			ArrayList<Point> rooms = dist.get(index).getRooms();
			dist.add(new Distance());

			index++;
			found = true;

			for (Point point : rooms) { //tar alla rum från ett index och loppar igenom dem för att finna giltiga vägar
				for (int[] d  : dir) {
					int newR = point.getRow() + d[0];
					int newC = point.getCol() + d[1];

					//om den hittar att det är ett rum lägger till det som en punkt på det nuvarande indexet och lägger till att det är besökt
					if (map[newR][newC] == "1" && !visited[newR][newC]) { 
						found = false;
						dist.get(index).addRoom(newR, newC);
						visited[newR][newC] = true;
					}
				}
			}
		}

		Point farthest = dist.get(index-1).getRoom(0);

		return farthest;
	}
	
	/*
	 * placerar ut rumstyper och hur de ska transformerars
	 */
	private void placeRoomTypes() {
		
		for (Point point : rooms) {
			int curR = point.getRow();
			int curC = point.getCol();
					
			int neighbours = 0;
			boolean left = false;
			boolean right = false;
			boolean top = false;
			boolean bottom = false;
			
			if (curR > 0 && map[curR-1][curC] != "0") {
				neighbours++;
				top = true;
			}
			if (curR < row-1 && map[curR+1][curC] != "0") {
				neighbours++;
				bottom = true;
			}
			if (curC > 0 && map[curR][curC-1] != "0") {
				neighbours++;
				left = true;
			}
			if (curC < col-1 && map[curR][curC+1] != "0") {
				neighbours++;
				right = true;
			}

			switch (neighbours) {
			case 1:
				if (left) {
					map[curR][curC] = "H1";
				} else if(top) {
					map[curR][curC] = "I1";
				} else if(right) {
					map[curR][curC] = "H2";
				} else {
					map[curR][curC] = "I3";
				}
				break;
			case 2:
				if(left && right) {
					map[curR][curC] = "A1";
				} else if(top && bottom) {
					map[curR][curC] = "B1";
				} else if(left && top) {
					map[curR][curC] = "C1";
				} else if(top && right) {
					map[curR][curC] = "C2";
				} else if (left && bottom) {
					map[curR][curC] = "C3";
				} else {
					map[curR][curC] = "C4";
				}
				break;
			case 3:
				if (left && right) {
					if(top) {
						map[curR][curC] = "D1";
					} else {
						map[curR][curC] = "D3";
					}
				} else if(top && bottom) {
					if(left) {
						map[curR][curC] = "F1";
					} else {
						map[curR][curC] = "F2";
					}
				}
				break;
			case 4:
				map[curR][curC] = "G1";
				break;

			default:
				break;
			}
		}
	}

	public String[][] getMap() {
		return map;
	}

	public int getPoints() {
		return roomCount;
	}
	
	public Point getStart() {
		return start;
	}
	
	public Point getEnd() {
		return end;
	}
}