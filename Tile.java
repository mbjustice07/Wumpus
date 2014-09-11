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
import java.util.*;


public enum Tile {
    Blood("B"), Slime("S"), Goop("G"), bottomLessPits("P"), theWumpus("W"),
    Hunter("O"), Empty(" ");

    private String tileVal = " ";
    private boolean isVisible = false;
   
    // Constructor for game tiles
    private Tile(String value){
        this.tileVal = value;
        this.isVisible = false;
    }
   
    // Getter for the tile
    public String getValue(){
        return tileVal;
    }
    
    public String toString(){
    	if(isVisible){
    		return tileVal;
    	}else{
    		return "X";
    	}
    }
   
}
