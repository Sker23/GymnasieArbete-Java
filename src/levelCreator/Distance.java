package levelCreator;

import java.util.ArrayList;

public class Distance {
	private ArrayList<Point> rooms = new ArrayList<>();
	
	public void addRoom(int row, int col) {
		rooms.add(new Point(row, col));
	}
	
	public ArrayList<Point> getRooms() {
		return rooms;
	}
	
	public Point getRoom(int index) {
		return rooms.get(index);
	}
	
}
