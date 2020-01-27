package jchess.common;

/**
 * This ensures that the class should facilitates Rule logic by making Board layout similar at logic layer.
 * 
 * Background: Each player in Chess-board is assigned different positions/cells. The pieces that can move in a restricted direction
 * move relative to the position of the player. E.g. Pawn pieces can move upwards. Considering 2-Players Chess-board, 
 * (if you see it from Player 1 positions) the moves Player 1 makes for Pawn piece are in upward direction, but from Player 2 perspective the piece
 * is moving in backward direction and Player 2 cannot move the Pawn in the same direction.
 * 
 * Considering above description, to make rule generic and to avoid maintaining relative position for each player, I assign the same position to every 
 * Player on its turn. Meaning, in 2-Players Chess-board both the Players are assigned Files from a-h and Rank from 1-2 when its their turn. 
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IBoardMapping {
    public int getMapping(int nSource);    
    public void addMapping(int nSource, int nDestination);
}
