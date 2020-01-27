package jchess.cache;

import jchess.common.IBoardAgent;

/**
 * This is a cache class that is responsible to store all details regarding a Chess-board.
 * It is also supposed to maintain all the movements (in the form of snapshots) made to Board.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public final class BoardCache {
	/**
	 * Name (unique) of the Chess-board.
	 */
	private String m_stBoardName;
	/**
	 * Instance of the Chess-board data class.
	 */
	private IBoardAgent m_oBoard;
	
	/**
	 * Constructor
	 * 
	 * @param stBoardName	Unique name for the Chess-board.
	 * @param oBoard	Instance of the Chess-board data class.
	 */
    public BoardCache(String stBoardName, IBoardAgent oBoard) {
    	m_stBoardName = stBoardName;
    	m_oBoard = oBoard;
    }

    /**
     * Getter for Chess-board name.
     * 
     * @return
     */
    public String getBoardName() {
    	return m_stBoardName;
    }
    
    /**
     * Getter for Chess-board data instance.
     * 
     * @return
     */
    public IBoardAgent getBoard() {
    	return m_oBoard;
    }
} 