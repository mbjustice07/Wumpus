import java.util.Random;

//say that there is something on the floor is there is something

public class GameMap {

	private Tile[][] map; // stores the map values
	private boolean[][] visibleMap; // stores the map that tells whether we can display a tile value
	private int mapX, mapY; //  stores the size of the map
	private int hunterX, hunterY; //  stores the hunters position
	private int arrowCode = 0; //    stores the arrow code
	Random rand = new Random();

	// test constructor will produce a 2D tile array, and place the hunter at xVal and yVal
	public GameMap(Tile[][] tilePreset, int xVal, int yVal) {
		map = tilePreset;
		visibleMap = new boolean[10][10];
		
	}

	// default constructor will produce a 10 x 10 map
	public GameMap() {
		mapX = 10;
		mapY = 10;
		map = new Tile[10][10];
		visibleMap = new boolean[10][10];

		populateMap();
	}

	// secondary constructor will produce an X x Y map
	public GameMap(int X, int Y) {
		mapX = X;
		mapY = Y;
		map = new Tile[X][Y];
		visibleMap = new boolean[X][Y];

		populateMap();
	}

	// initialize the map
	private void populateMap() {

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				visibleMap[i][j] = false;
			}
		}

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = Tile.Empty;
			}
		}

		// place Wumpus, Pits, Blood, Slime, Goop, and Hunter on the Game map
		placeWumpus();
		placePits(4);
		placeHunter();
		System.out.println(displayMap(1));
		System.out.println("KILL THE WUMPUS!\nValid Commands:\n\"go north\", \"go south\", \"go east\", \"go west\" "
						+ "\n\"shoot north\", \"shoot south\", \"shoot east\", \"shoot west\"");
		stateTileType();
		System.out.println(displayMap(0));

	}

	// display the map, if type 0, display only visible, if type 1, display all
	public String displayMap(int type) {
		String returnString = new String("");

		// display all contents
		if (type == 1) {
			for (int y = 0; y < mapY; y++) {
				for (int x = 0; x < mapX; x++) {
					if (x == hunterX && y == hunterY) {
						returnString += "[" + Tile.Hunter.getValue() + "]";
					} else {
						returnString += "[" + map[x][y].toString() + "]";
					}
				}
				returnString += "\n";
			}
		} else { // display only visible
			for (int y = 0; y < mapY; y++) {
				for (int x = 0; x < mapX; x++) {
					if (x == hunterX && y == hunterY) {
						returnString += "[" + Tile.Hunter.getValue() + "]";
					} else {
						if (visibleMap[x][y]) {
							returnString += "[" + map[x][y].toString() + "]";
						} else {
							returnString += "[X]";
						}
					}

				}
				returnString += "\n";
			}
		}

		return returnString;

	}

	/*
	 * PlaceWumpus() method takes no input. It makes a random number within the
	 * confines of the game map indices. It then calls the wrapAround() method
	 * that takes the random location and places blood into the correct
	 * location. It then calls the place() method giving it the tileType, and
	 * the coordinates of where to place the tile.
	 */
	private void placeWumpus() {
		Random rand = new Random();
		int x = rand.nextInt(mapX - 1);
		int y = rand.nextInt(mapY - 1);

		int[] w = wrapAround(x, y);

		/*
		 * location of corresponding array index and (x,y) coordinates [ ] [ ]
		 * [6] [ ] [ ] [ ] [1,2] [2] [3,2] [ ] [5] [1] [W] [3] [7] [ ][1,4] [4]
		 * [3,4] [ ] [ ] [ ] [8] [ ] [ ]
		 */

		place(Tile.theWumpus, x, y);
		// location 1x
		place(Tile.Blood, w[0], y);
		// location 2y
		place(Tile.Blood, x, w[1]);
		// location 3x
		place(Tile.Blood, w[2], y);
		// location 4y
		place(Tile.Blood, x, w[3]);
		// location 5x
		place(Tile.Blood, w[4], y);
		place(Tile.Blood, x, w[5]);
		// location 7x
		place(Tile.Blood, w[6], y);
		// location 8y
		place(Tile.Blood, x, w[7]);
		// location 1x,2y
		place(Tile.Blood, w[0], w[1]);
		// location 3x,2y
		place(Tile.Blood, w[2], w[1]);
		// location 3x,4y
		place(Tile.Blood, w[2], w[3]);
		// location 1x,4y
		place(Tile.Blood, w[0], w[3]);
	}

	/*
	 * Place tile method takes a tile type from the caller and the x and y
	 * coordinates of where to place it.
	 * 
	 * If a slime tile is being placed on a tile already containing blood it
	 * turns the tile into the goop type.
	 */
	private void place(Tile tileType, int x, int y) {

		// if there is blood where we want to put slime, make goop
		if ((tileType == Tile.Slime) && (map[x][y] == Tile.Blood)) {
			map[x][y] = Tile.Goop;
		} else {
			map[x][y] = tileType; // store the tile type at that location
		}

	}

	/*
	 * placePits method takes a number determined randomly and creates a number
	 * of pits corresponding. It takes a random spot and runs the wrap around
	 * method. If the pit or blood is being placed in a position already
	 * occupied it searches for a new location and will continue until it finds
	 * an unoccupied location.
	 */
    private void placePits(int num) {

        for (int i = 0; i < num; i++) {

            Random rand = new Random();
            boolean pitPlaced = false;
            int x = 0,
                y = 0;

                int [] w = wrapAround(x,y);
                
                /* 
                 * While the pit has not found a location a to place the pit
                 * check all the surround cells to makes sure the slime or pit
                 * cover another bottomless pit or the wumpus.  If it is then the 
                 * pit location is not found and so retry another location.
                 */
                while (!pitPlaced){
                    x = rand.nextInt(mapX - 1);
                    y = rand.nextInt(mapY - 1);
                
                    w = wrapAround(x,y);  // wrap around method calculates the wrapper
                    
                    if(map[x][y] != Tile.bottomLessPits
                            || map[w[0]][y] != Tile.bottomLessPits // left cell
                            || map[w[2]][y] != Tile.bottomLessPits  // right cell
                            || map[x][w[3]] != Tile.bottomLessPits // bottom cell
                            || map[x][w[1]] != Tile.bottomLessPits // top cell
                            || map[x][y]    != Tile.theWumpus       // the center cell
                            || map[w[0]][y] != Tile.theWumpus       // the left
                            || map[w[2]][y] != Tile.theWumpus       // right
                            || map[x][w[3]] != Tile.theWumpus       // bottom
                            || map[x][w[1]] != Tile.theWumpus)      // bottom
                        pitPlaced = true;
                
                }

                    /*          [x][2]
                     *   [1][y] [x][y] [3][y] 
                     *  	[x][4]
                     */
                // when the pit is found place it.  If there is slime and blood
                // place goop instead
                    place(Tile.bottomLessPits, x, y);
                    // location 1x
                    place(Tile.Slime, w[0], y);
                    // location 2y
                    place(Tile.Slime, x, w[1]);
                    //location 3x
                    place(Tile.Slime, w[2], y);
                    //location 4y
                    place(Tile.Slime, x, w[3]);
        }

    }
	
	
	/*
	 * placeHunter() method is the last method called when setting up the
	 * gameboard. It will run a while searching for a random location with where
	 * to place the hunter. It checks that the hunter isn't placed in a location
	 * containing the wumpus or a bottomless pit. When the hunter is placed
	 * hunterPlaced value is set to true, indicating that the hunter was placed.
	 */
	private void placeHunter() {

		boolean hunterPlaced = false; // boolean value for loop

		while (!hunterPlaced) {

			int x = rand.nextInt(mapX - 1); // get random x value
			int y = rand.nextInt(mapY - 1); // get random y value

			if (map[x][y] == Tile.Empty) {

				this.hunterX = x;
				this.hunterY = y;
				this.visibleMap[x][y] = true;
				hunterPlaced = true;
			}

		}
	}

	/*
	 * The wrapAround method takes an x and y coordinate and calculates the
	 * location of the tiles that surround the pits and the wumpus and creates a
	 * wrap around effect on the 2D array. It returns the values in an array of
	 * type int.
	 * 
	 * The order of the of the array is important to the corresponding location
	 * of the tiles. Please see diagrams associated. Please note the indices are
	 * off.
	 */
	private int[] wrapAround(int x, int y) {
		// the first 4 values in the array are the x coordinates
		// the last 4 values are the y coordinates

		// [x][y] = [1][y], [x][2], [3][y], [4][8]

		/*
		 * [x][2] [1][y] [x][y] [3][y] [x][4]
		 */
		/*
		 * [ ] [ ] [6] [ ] [ ] [ ] [1,2] [2] [3,2] [ ] [5] [1] [W] [3] [7] [
		 * ][1,4] [4] [3,4] [ ] [ ] [ ] [8] [ ] [ ]
		 */

		int[] coordinates = new int[8];
		int X = this.mapX, Y = this.mapY;
		// fixing the x coordinates
		coordinates[0] = (x - 1 + X) % X;

		coordinates[1] = (y + 1 + Y) % Y;

		coordinates[2] = (x + 1 + X) % X;

		coordinates[3] = (y - 1 + Y) % Y;

		coordinates[4] = (x - 2 + X) % X;

		coordinates[5] = (y + 2 + Y) % Y;

		coordinates[6] = (x + 2 + X) % X;

		coordinates[7] = (y - 2 + Y) % Y;

		return coordinates;
	}

	// move the hunter based on the command that is given
	public void moveHunter(String command) {

		if (!command.equals("go north") && !command.equals("go south")
				&& !command.equals("go east") && !command.equals("go west")
				&& !command.equals("shoot north")
				&& !command.equals("shoot south")
				&& !command.equals("shoot east")
				&& !command.equals("shoot west")) {
			System.out.println("Please enter a valid command");
			return;
		}

		this.visibleMap[hunterX][hunterY] = true;

		int localX = hunterX;
		int localY = hunterY;

		switch (command) {
		case "go north": {
			localY = hunterY - 1;
		}
			break;
		case "go south": {
			localY = hunterY + 1;
		}
			break;
		case "go east": {
			localX = hunterX + 1;
		}
			break;
		case "go west": {
			localX = hunterX - 1;
		}
			break;
		}

		// make sure that hunter position is valid, if not, correct it
		if (localX == (mapX)) {
			this.hunterX = 0;
		} else if (localX == -1) {
			this.hunterX = mapX - 1;
		} else if (localX < mapX) {
			this.hunterX = localX;
		}

		if (localY == (mapY)) {
			this.hunterY = 0;
		} else if (localY == -1) {
			this.hunterY = mapY - 1;
		} else if (localY < mapY) {
			this.hunterY = localY;
		}

		// check game over
		checkGameOver();

		// tell the player what tile they are on
		stateTileType();

		if (command.equals("shoot north") || command.equals("shoot south")) {
			for (int i = 0; i < mapY; i++) {
				if (map[hunterX][i] == Tile.theWumpus) {
					System.out.println("WUMPUS HIT!");
					this.arrowCode = 2;
					return;
				}
			}
			System.out.println("YOU MISSED AND HIT YOURSELF!");
			this.arrowCode = 1;
		}

		if (command.equals("shoot east") || command.equals("shoot west")) {
			for (int i = 0; i < mapX; i++) {
				if (map[i][hunterY] == Tile.theWumpus) {
					System.out.println("WUMPUS HIT!");
					this.arrowCode = 2;
					return;
				}
			}
			System.out.println("YOU MISSED AN HIT YOURSELF!");
			this.arrowCode = 1;
		}

		// if command == shoot up || down, do the same loop
		// if command == shoot right || left, do the same loop

	}

	// Print the tile that the hunter is on to the user
	private void stateTileType() {
		if (map[hunterX][hunterY].getValue().equals(Tile.Slime.getValue())) {
			System.out.println("We are standing on green slime");
		} else if (map[hunterX][hunterY].getValue().equals(
				Tile.Blood.getValue())) {
			System.out.println("We are standing on blood");
		} else if (map[hunterX][hunterY].getValue()
				.equals(Tile.Goop.getValue())) {
			System.out.println("We are standing on goop");
		} else {
			System.out.println("We are standing in an empty room");
		}
	}

	/*
	 * checkGameOver() it checks the current location of the hunter as compared
	 * to the 2D map. If the hunter is over the Wumpus or a bottom less pit, the
	 * game is over and it prints to screen what happened. It displays the same
	 * message for a pit and the wumpus. If the hunter shoots an arrow it
	 * changes the arrowcode method to a corresponding code. If the arrow hit
	 * the wumpus it sets the code to 2 and prints the message. If it is 1 it
	 * means the Wumpus got to the hunter.
	 */
	public boolean checkGameOver() {

		// hunters position
		String tile = map[hunterX][hunterY].getValue(); // get the tile value

		if (tile == Tile.theWumpus.getValue()
				|| tile == Tile.bottomLessPits.getValue()) {
			System.out
					.println("Omg!! The Wumpus ate you!!  Gobble Gobble. Play again?");
			return true;
		} else if (arrowCode == 1) {
			System.out.println("Once I took an arrow to the knee.  Game Over.");
			return true;
		} else if (arrowCode == 2) {
			System.out.println("Wow.  You got lucky.  Play again?");
			return true;
		}
		return false;
	}
}
