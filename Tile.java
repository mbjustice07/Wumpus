/**
 *
 * @author Justice
 * 
 * [O] Hunter
[X] Hidden Room (not yet visited)
[S] Slime
[B] Blood
[G] Goop (blood and slime in the same room)
[W] Wumpus
[P] Pit
[ ] Visited room with nothing in it
 */


/**
*
* @author Justice
* 
* Tile enum is the tile class creates the tile class.  It has:
* 
[O] Hunter
[X] Hidden Room (not yet visited)
[S] Slime
[B] Blood
[G] Goop (blood and slime in the same room)
[W] Wumpus
[P] Pit
[ ] Visited room with nothing in it
* 
* it has a private constructor for setting the string value of the tile.
* It has a getValue() method to retrive the string representation of the tile 
* for the game board.
* It has a toString method is duplicate the getValue();
*/

public enum Tile {
    Blood("B"), Slime("S"), Goop("G"), bottomLessPits("P"), theWumpus("W"),
    Hunter("O"), Empty(" ");

    private String tileVal = " ";
   
    // Constructor for game tiles
    private Tile(String value){
        this.tileVal = value;
    }
   
    // Getter for the tile
    public String getValue(){
        return tileVal;
    }
    
    public String toString(){
    		return tileVal;
    }
   
}
