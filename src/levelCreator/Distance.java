package levelCreator;

import java.util.ArrayList;

public class Distance {
	private ArrayList<Place> rooms = new ArrayList<>();
	
	public void addRoom(int row, int col) {
		rooms.add(new Place(row, col));
	}
	
	public ArrayList<Place> getRooms() {
		return rooms;
	}
	
	public Place getRoom(int index) {
		return rooms.get(index);
	}
	
}
