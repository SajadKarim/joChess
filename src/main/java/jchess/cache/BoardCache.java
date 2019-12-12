package jchess.cache;

import jchess.common.IBoardAgent;

/**
 * This class is to store all the details regarding a Board.
 * It is also supposed to store all the movements (snapshots) made to Board.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class BoardCache
{
	private String m_stName;
	private IBoardAgent m_oBoard;
	
    public BoardCache(String stName, IBoardAgent oBoard) {
    	m_stName = stName;
    	m_oBoard = oBoard;
    }

    public String getName() {
    	return m_stName;
    }
    
    public IBoardAgent getBoard() {
    	return m_oBoard;
    }
} 