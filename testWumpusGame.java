import static org.junit.Assert.*;
import org.junit.Test;


public class testWumpusGame {

	@Test
	public void testMapConstructor(){
		Tile[][] tempTileArray = { 	{Tile.Blood, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Goop, Tile.bottomLessPits, Tile.theWumpus, Tile.Blood}, 
									{Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Slime, Tile.Goop, Tile.Blood, Tile.Blood},
									{Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Slime, Tile.bottomLessPits, Tile.Slime, Tile.Blood, Tile.Empty},
									{Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Slime, Tile.bottomLessPits, Tile.Slime, Tile.Empty},
									{Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Slime, Tile.Empty, Tile.Empty},
									{Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty},
									{Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Slime, Tile.Empty},
									{Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Slime, Tile.bottomLessPits, Tile.Slime},
									{Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Goop, Tile.Empty},
									{Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Empty, Tile.Goop, Tile.Blood, Tile.Blood} };
		GameMap testMap = new GameMap(tempTileArray, 0, 1);
		String testMapString = "[B][ ][ ][ ][ ][ ][G][P][W][B]\n[O][ ][ ][ ][ ][ ][S][G][B][B]\n[ ][ ][ ][ ][ ][S][P][S][B][ ]\n[ ][ ][ ][ ][ ][ ][S][P][S][ ]\n"
				+ "[ ][ ][ ][ ][ ][ ][ ][S][ ][ ]\n[ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]\n[ ][ ][ ][ ][ ][ ][ ][ ][S][ ]\n[ ][ ][ ][ ][ ][ ][ ][S][P][S]\n"
				+ "[ ][ ][ ][ ][ ][ ][ ][ ][G][ ]\n[ ][ ][ ][ ][ ][ ][ ][G][B][B]";
		assertEquals(testMap.displayMap(1), testMapString);
		
	}
	
}
