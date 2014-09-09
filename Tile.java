/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Justice
 */
import java.util.*;


public enum Tile {
    Empty(0), Blood(1), Slime(2), Goop(3), bottomLessPits(4), theWumpus(5);
   
    private int tileVal;
    
    private Tile(){
    	this.tileVal = 0;
    }
   
    // Constructor for game tiles
    private Tile(int value){
        this.tileVal = value;
    }
   
    // Getter for the tile
    public int getTile(){
        return tileVal;
    }
   
}