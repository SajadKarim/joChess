package jchess.cache;

import jchess.common.IBoardAgent;

/**
 * This class is to store all the details regarding a Board.
 * It is also supposed to store all the movements (snapshots) made to Board.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public final class BoardCache {
	private String m_stBoardName;
	private IBoardAgent m_oBoard;
	
    public BoardCache(String stBoardName, IBoardAgent oBoard) {
    	m_stBoardName = stBoardName;
    	m_oBoard = oBoard;
    }

    public String getBoardName() {
    	return m_stBoardName;
    }
    
    public IBoardAgent getBoard() {
    	return m_oBoard;
    }
} 