package jchess.cache;

import java.util.Hashtable;
import java.util.Map;

import jchess.common.IBoardMapping;

/**
 * This class holds Board mapping. It facilitates Rule logic by making Board layout similar at logic layer.
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

public final class BoardMapping implements IBoardMapping {
	/**
	 * Hashmap to maintain Players' piece-position mapping.
	 */
    private Map m_dictMappings = null; 

    /**
     * Constructor.
     */
    public BoardMapping() {
    	m_dictMappings = new Hashtable();
    }
    
    /**
     * Returns virtual mapping against the actual one.
     */
    public int getMapping(int nSource) {
    	Object oDestination = m_dictMappings.get((Object)nSource);
    	
    	if (oDestination == null) {
    		return nSource;
    	}
    	
    	return (int)oDestination;
    }
    
    /**
     * Adds mapping.
     */
    public void addMapping(int nSource, int nDestination) {
    	m_dictMappings.put(nSource, nDestination);
    }
}
