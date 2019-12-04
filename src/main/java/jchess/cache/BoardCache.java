package jchess.cache;

import java.util.HashMap;
import java.util.Map;

import jchess.common.IBoard;

public class BoardCache
{
	private String m_stName;
	private IBoard m_oBoard;
	
    public BoardCache(String stName, IBoard oBoard) {
    	m_stName = stName;
    	m_oBoard = oBoard;
    }

    public String getName() {
    	return m_stName;
    }
    
    public IBoard getBoard() {
    	return m_oBoard;
    }
} 