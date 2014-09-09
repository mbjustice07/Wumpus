import java.util.ArrayList;
import java.util.Random;


public class GameMap {
	private int mapX, mapY;
	private ArrayList<ArrayList<Tile>> Map;
	
	// default constructor will produce a 10 x 10 map
	public GameMap(){
		mapX = 10;
		mapY = 10;
		Map = new ArrayList<ArrayList<Tile>>(10);
		for(ArrayList t : Map){
			t = new ArrayList<Tile>(10);
		}
		
		populateMap();
	}

	// secondary constructor will produce an X x Y map
	public GameMap(int X, int Y){
		mapX = X;
		mapY = Y;
		Map = new ArrayList<ArrayList<Tile>>(X);
		for(ArrayList t : Map){
			t = new ArrayList<Tile>(Y);
		}
		
		populateMap();
	}
	
	// display the map, if type 0, display only visible, if type 1, display all
	public String displayMap(int type){
		String returnString = "";
		if(type == 1){
			for(ArrayList<Tile> x : Map){
				for(Tile y : x){
					returnString += "[" + y.toString() + "]";
				}
				returnString += "\n";
			}
		}
		
		return returnString;
		
	}
	
	private void populateMap() {
		// TODO Auto-generated method stub
		//placeWumpus();
		//placePits(4);
		//placeHunter();
	}
	
	private void placeWumpus() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int x = rand.nextInt(Map.size());
		int y = rand.nextInt(Map.get(0).size());
		Map.get(x - 1).add(y, Tile.Blood);
		Map.get(x).add(y, Tile.theWumpus);
		Map.get(x + 1).add(y, Tile.Blood);
		Map.get(x).add(y - 1, Tile.Blood);
		Map.get(x).add(y + 1, Tile.Blood);
	}
	
	// takes how many pits we want
	private void placePits(int num) {
		// TODO Auto-generated method stub	
	}
	
	private void placeHunter() {
		// TODO Auto-generated method stub
	}

	// display the map with all of the values showing
	public String gameLoss(){
		// TODO display correct text for game loss
		return displayMap(1) + "\n" + "Game Over, You Lose";
	}
	
	// display the map with all of the values showing
	public String gameWin(){
		// TODO display correct text for game win
		return displayMap(1) + "\n" + "Game Over, You Win";
	}
}
