package levelCreator;


public class MapTest {

	/*
	 * testar att göra och printa ut kartan 
	 * */
	public static void main(String[] args){
		MapMaker mm =  new MapMaker(15, 17);
		
		String[][] map = mm.getMap();
		
		for (int i = 0; i < map.length; i++) {
		    for (int j = 0; j < map[i].length; j++) {
		    	if (map[i][j] != "0") {
		    		System.out.print(map[i][j] + " ");
				} else {
					System.out.print(map[i][j] + "  ");
				}
		    	//System.out.print(map[i][j] + " ");
		    }
		    System.out.print("\n");
		}
		
		System.out.println("mapPoints: " + mm.getPoints());
	}
}
