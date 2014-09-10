import java.util.ArrayList;
import java.util.Random;

public class GameMap {

	private Tile[][] map;
	private int mapX, mapY;
	private int hunterX, hunterY;

	// private ArrayList<ArrayList<Tile>> Map;

	// default constructor will produce a 10 x 10 map
	public GameMap() {
		mapX = 10;
		mapY = 10;
		map = new Tile[10][10];

		populateMap();
	}

	// secondary constructor will produce an X x Y map
	public GameMap(int X, int Y) {
		mapX = X;
		mapY = Y;
		map = new Tile[X][Y];

		populateMap();
	}

	// display the map, if type 0, display only visible, if type 1, display all
	public String displayMap(int type) {
		String returnString = "";
		if (type == 1) {
			for (Tile[] Array1D : map) {
				for (Tile y : Array1D) {
					returnString += "[" + y.toString() + "]";
				}
				returnString += "\n";
			}
		} else {
			for (Tile[] Array1D : map) {
				for (Tile y : Array1D) {
					returnString += "";
				}
			}
		}

		return returnString;

	}

	private void populateMap() {
		
		// TODO Auto-generated method stub
		for (Tile[] x : map) {
			for (Tile y : x) {
				y = Tile.Blood;
			}
		}
		
		placeWumpus();
		placePits(4);
		placeHunter();
	}

	// places both the wumpus and then the blood around it
	private void placeWumpus() {
		Random rand = new Random();
		int x = rand.nextInt(mapX - 1);
		int y = rand.nextInt(mapY - 1);
//		place(Tile.theWumpus, x, y);
//		place(Tile.Blood, x - 1, y);
//		place(Tile.Blood, x - 1, y + 1);
//		place(Tile.Blood, x - 1, y - 1);
//		place(Tile.Blood, x + 1, y);
//		place(Tile.Blood, x + 1, y + 1);
//		place(Tile.Blood, x + 1, y - 1);
//		place(Tile.Blood, x - 2, y);
//		place(Tile.Blood, x + 2, y);
//		place(Tile.Blood, x, y + 1);
//		place(Tile.Blood, x, y + 2);
//		place(Tile.Blood, x, y - 1);
//		place(Tile.Blood, x, y - 2);
	}

	private void place(Tile tileType, int xLoc, int yLoc) {
		// TODO need to implement the wrap arround in here

		// if there is blood where we want to put slime, make goop
		if ((tileType == Tile.Slime) && (map[xLoc][yLoc] == Tile.Blood)) {
			map[xLoc][yLoc] = Tile.Goop;
		}

	}

	// takes how many pits we want
	private void placePits(int num) {
		// TODO Auto-generated method stub
	}

	private void placeHunter() {
		// TODO Auto-generated method stub
	}

	private int[] wrapAround(int x, int y) {
		// the first 4 values in the array are the x coordinates
		// the last 4 values are the y coordinates

		// [x][y] = [1][y], [x][2], [3][y], [4][8]

		/*			[x][2]
		 *   [1][y] [x][y] [3][y] 
		 *  		[x][4]
		 */
		int[] coordinates = new int[4];

		// fixing the x coordinates
		coordinates[1] = (x - 1 + this.mapX) % this.mapX;

		coordinates[2] = (y + 1 + this.mapY) % this.mapY;

		coordinates[3] = (x + 1 + this.mapX) % this.mapX;

		coordinates[4] = (y - 1 + this.mapY) % this.mapY;

		return coordinates;
	}

	// move the hunter based on the command 
    public void moveHunter(String command){
    
    	int localX = hunterX;
    	int localY = hunterY;
    
    	switch(command){
    		case "go north":{
				localX = hunterX + 1;
    		}
    		case "go south":{
    			localX = hunterX - 1;
    		}
    		case "go east":{
    			localY = hunterY + 1;
    		}
    		case "go west":{
    			localY = hunterY + 1;
    		}
    	}
    	
    	// make sure that hunter position is valid, if not, correct it
    	if(localX > (mapX - 1)){
    		hunterX = 0;
    	}else if(localX < 0){
    		hunterX = mapX - 1;
    	}
    	
    	if(localY > (mapY - 1)){
    		hunterY = 0;
    	}else if(localY < 0){
    		hunterY = mapY - 1;
    	}
    	
    	
    	
    	// if command == shoot up || down, do the same loop
    	// if command == shoot right || left, do the same loop
    				
    }
    // dont forget the shoot arrow
    
    // get command
    // move hunter
    // set tile visible to true
    // check hunter position
    	// if hunter position is == wumpus || pit
    		// game over
   
    public boolean checkGameOver(){
    	// display based on an if
    	return false;
    }
}
