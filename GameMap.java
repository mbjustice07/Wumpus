import java.util.ArrayList;
import java.util.Random;

public class GameMap {

	private Tile[][] map;
	private int mapX, mapY;
	private int hunterX, hunterY;
	Random rand = new Random();

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
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = Tile.Empty;
			}
		}
		System.out.println(displayMap(0));
		
		placeWumpus();
		placePits(4);
		placeHunter();
	}

    // places both the wumpus and then the blood around it
    private void placeWumpus() {
        Random rand = new Random();
        int x = rand.nextInt(mapX - 1);
        int y = rand.nextInt(mapY - 1);
                
                int [] w = wrapAround(x,y);
                
                  /* location of corresponding array index and (x,y) coordinates
                  * [  ] [ ]  [6] [  ] [ ]
                    [ ] [1,2] [2] [3,2] [ ]
                    [5]  [1]  [W]  [3] [7]
                    [  ][1,4] [4] [3,4] [  ]
                    [  ] [ ]  [8] [  ] [  ]
                  */
                
        place(Tile.theWumpus, x, y);
                // location 1x
        place(Tile.Blood, w[0], y);
                // location 2y
        place(Tile.Blood, x, w[1]);
                //location 3x
        place(Tile.Blood, w[2], y);
                //location 4y
        place(Tile.Blood, x, w[3]);
                //location 5x
        place(Tile.Blood, w[4], y);
                //location 6y
        place(Tile.Blood, x, w[5]);
                //location 7x
        place(Tile.Blood, w[6], y);
                //location 8y
        place(Tile.Blood, x, w[7]);
                //location 1x,2y
        place(Tile.Blood, w[0], w[1]);
                //location 3x,2y
        place(Tile.Blood, w[2], w[1]);
                // location 3x,4y
        place(Tile.Blood, w[2], w[3]);
                //location 1x,4y
        place(Tile.Blood, w[0], w[3]);
    }

	private void place(Tile tileType, int x, int y) {
		// TODO need to implement the wrap arround in here

		// if there is blood where we want to put slime, make goop
		if ((tileType == Tile.Slime) && (map[x][y] == Tile.Blood)) {
			map[x][y] = Tile.Goop;
		}
                
                map[x][y] = tileType; // store the tile type at that location

	}

	// takes how many pits we want
	// takes how many pits we want
	private void placePits(int num) {
            
                Random rand = new Random();
                boolean pitPlaced = false;
                int     x = 0,
                        y = 0;
                
                int [] w = wrapAround(x,y);;
                
                while (!pitPlaced){
                    x = rand.nextInt(mapX - 1);
                    y = rand.nextInt(mapY - 1);
                
                    w = wrapAround(x,y);
                    
                    if(map[x][y] != Tile.bottomLessPits
                            || map[w[1]][y] != Tile.bottomLessPits
                            || map[w[3]][y] != Tile.bottomLessPits
                            || map[x][w[4]] != Tile.bottomLessPits
                            || map[x][w[2]] != Tile.bottomLessPits
                            || map[x][y]    != Tile.theWumpus
                            || map[w[1]][y] != Tile.theWumpus
                            || map[w[3]][y] != Tile.theWumpus
                            || map[x][w[4]] != Tile.theWumpus
                            || map[x][w[2]] != Tile.theWumpus)
                        pitPlaced = true;
                
                }
                    /*          [x][2]
                     *   [1][y] [x][y] [3][y] 
                     *  	[x][4]
                     */
                
                    place(Tile.bottomLessPits, x, y);
                    // location 1x
                    place(Tile.Slime, w[1], y);
                    // location 2y
                    place(Tile.Slime, x, w[2]);
                    //location 3x
                    place(Tile.Slime, w[3], y);
                    //location 4y
                    place(Tile.Slime, x, w[4]);
                
            
	}

    private void placeHunter() {
        
        boolean hunterPlaced = false; // boolean value for loop
        
        while(!hunterPlaced){
            
            int x = rand.nextInt(mapX - 1);  // get random x value
            int y = rand.nextInt(mapY - 1);  // get random y value
            
            if(map[x][y] != Tile.theWumpus 
                    || map[x][y] != Tile.bottomLessPits){
                
                this.hunterX = x;
                this.hunterY = y;
                hunterPlaced = true;
            }
        
        }
    }

	private int[] wrapAround(int x, int y) {
		// the first 4 values in the array are the x coordinates
		// the last 4 values are the y coordinates

		// [x][y] = [1][y], [x][2], [3][y], [4][8]

		/*          [x][2]
		 *   [1][y] [x][y] [3][y] 
                 *  	    [x][4]
		 */
                 /*
                  * [  ] [ ]  [6] [  ] [ ]
                    [ ] [1,2] [2] [3,2] [ ]
                    [5]  [1]  [W]  [3] [7]
                    [  ][1,4] [4] [3,4] [  ]
                    [  ] [ ]  [8] [  ] [  ]
                  */
            
            
		int[] coordinates = new int[8];

		// fixing the x coordinates
		coordinates[0] = (x - 1 + this.mapX) % this.mapX;

		coordinates[1] = (y + 1 + this.mapY) % this.mapY;

		coordinates[2] = (x + 1 + this.mapX) % this.mapX;

		coordinates[3] = (y - 1 + this.mapY) % this.mapY;
                
                coordinates[4] = (x - 2 + this.mapX) % this.mapX;
                
                coordinates[5] = (y + 2 + this.mapY) % this.mapY;
                
                coordinates[6] = (x + 2 + this.mapX) % this.mapX;

                coordinates[7] = (y - 2 + this.mapY) % this.mapY;
               
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
    	
    	if(command.equals("shoot north") || command.equals("shoot south")){
    		for(int i = hunterY + 1; i < map[0].length; i++){
    			if(map[hunterX][i] == Tile.theWumpus){
    				// return checkGameOver false, you have won
    			}else if(i == hunterY){
    				// return checkGameOver true, you have lost
    			}
    		}
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
        
    	// hunters position
        String tile = map[hunterX][hunterY].getValue(); // get the tile value
        
        if (tile == Tile.theWumpus.getValue() || tile == Tile.bottomLessPits.getValue()){
            System.out.println("Omg!! The Wumpus ate you!!  Gobble Gobble. Play again?");
            return true;
        }
        else if (false){
            System.out.println("Wow.  You got lucky.  Play again?");
            return true;
        }
    	return false;
    }
}
