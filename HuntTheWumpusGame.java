/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.KeyEvent;
import java.util.Scanner;

public class HuntTheWumpusGame {

    public static void main(String args[]){
       
    	GameMap Map = new GameMap();
        /*
         * initialize the game board
         *
         */
       
        // Set up wumpus position and attribute positions
    	Map.displayMap(1);
    	Scanner input = new Scanner(System.in);
//        while(){ // Game not over
//            // Display game board
//            
//        }
    }
   
    /*
     * directionKey() method takes user from the input as to the direction of where the hunter will move
     * or which direction the arrow will be shot
     *
     * It returns a string corresponding the compass directions of up, down, left, and right, and North, South
     * East, and West respectively.
     */
    public String directionKey (KeyEvent arrowKey){
        int direction = arrowKey.getKeyCode();
       
        // A while loop running until a directional key is hit or the exit key to end the game
        while (direction != KeyEvent.VK_UP || direction != KeyEvent.VK_DOWN || direction != KeyEvent.VK_LEFT ||
                direction != KeyEvent.VK_RIGHT || direction != KeyEvent.VK_E)
            System.out.println("Incorrect Key, press up, down, left or right on the directional pad or 'E' to Exit");
       
        if ( direction == KeyEvent.VK_UP)
            return "N"; // returns the string for North for the hunter to move
        else if (direction == KeyEvent.VK_DOWN)
            return "S"; // returns the string for South for the hunter to move
        else if (direction == KeyEvent.VK_LEFT)
            return "E";    // returns the string for East for the hunter to move
        else if (direction == KeyEvent.VK_RIGHT)
            return "W"; // returns the string for West for the hunter to move
        else if (direction == KeyEvent.VK_E)
            System.exit(1); return "W";// returns 1 code for game exited      
    }  
}
