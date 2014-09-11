import java.util.ArrayList;
import java.util.Random;

//say that there is something on the floor isf there is something

public class GameMap {

	private Tile[][] map;
	private boolean[][] visibleMap;
	private int mapX, mapY;
	private int hunterX, hunterY;
	private int arrowCode = 0;
	Random rand = new Random();

	// private ArrayList<ArrayList<Tile>> Map;

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

		System.out.println("Map Before Population");
		System.out.println(displayMap(1));

		placeWumpus();

		// TODO get this loop working correctly
		placePits(4);
		placeHunter();

		System.out.println("Map After Population");
		System.out.println(displayMap(1));

		System.out.println("Normal Map View");
		System.out
				.println("KILL THE WUMPUS!\nValid Commands:\n\"go north\", \"go south\", \"go east\", \"go west\" "
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

	// places both the wumpus and then the blood around it
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
		// location 6y
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

	private void place(Tile tileType, int x, int y) {

		// if there is blood where we want to put slime, make goop
		if ((tileType == Tile.Slime) && (map[x][y] == Tile.Blood)) {
			map[x][y] = Tile.Goop;
		} else {
			map[x][y] = tileType; // store the tile type at that location
		}

	}

	// takes how many pits we want
	// not overriding wumpus, slime also cannot replace wumpus
	private void placePits(int num) {
		for (int i = 0; i < num; i++) {

			Random rand = new Random();
			boolean pitPlaced = false;
			int x = 0, y = 0;

			int[] w = wrapAround(x, y);

			while (!pitPlaced) {
				x = rand.nextInt(mapX - 1);
				y = rand.nextInt(mapY - 1);

				w = wrapAround(x, y);

				if (map[x][y] != Tile.bottomLessPits
						|| map[w[0]][y] != Tile.bottomLessPits
						|| map[w[2]][y] != Tile.bottomLessPits
						|| map[x][w[3]] != Tile.bottomLessPits
						|| map[x][w[1]] != Tile.bottomLessPits
						|| map[x][y] != Tile.theWumpus
						|| map[w[0]][y] != Tile.theWumpus
						|| map[w[2]][y] != Tile.theWumpus
						|| map[x][w[3]] != Tile.theWumpus
						|| map[x][w[1]] != Tile.theWumpus)
					pitPlaced = true;

			}

			/*
			 * [x][2] [1][y] [x][y] [3][y] [x][4]
			 */

			place(Tile.bottomLessPits, x, y);
			// location 1x
			place(Tile.Slime, w[0], y);
			// location 2y
			place(Tile.Slime, x, w[1]);
			// location 3x
			place(Tile.Slime, w[2], y);
			// location 4y
			place(Tile.Slime, x, w[3]);
		}

	}

	private void placeHunter() {

		boolean hunterPlaced = false; // boolean value for loop

		while (!hunterPlaced) {

			int x = rand.nextInt(mapX - 1); // get random x value
			int y = rand.nextInt(mapY - 1); // get random y value

			if (map[x][y] != Tile.theWumpus || map[x][y] != Tile.bottomLessPits) {

				this.hunterX = x;
				this.hunterY = y;
				this.visibleMap[x][y] = true;
				hunterPlaced = true;
			}

		}
	}

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

	// move the hunter based on the command
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
			for (int i = hunterY + 1; i < map[0].length; i++) {
				if (map[hunterX][i] == Tile.theWumpus) {
					System.out.println("WUMPUS HIT!");
					this.arrowCode = 1;
				} else if (i == hunterY) {
					System.out.println("YOU MISSED AN HIT YOURSELF!");
					this.arrowCode = 2;
				}
			}
		}

		if (command.equals("shoot east") || command.equals("shoot west")) {
			for (int i = hunterX + 1; i < map.length; i++) {
				if (map[i][hunterY] == Tile.theWumpus) {
					System.out.println("WUMPUS HIT!");
					this.arrowCode = 1;
				} else if (i == hunterX) {
					System.out.println("YOU MISSED AN HIT YOURSELF!");
					this.arrowCode = 2;
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
