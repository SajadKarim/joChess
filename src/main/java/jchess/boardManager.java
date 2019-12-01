package jchess;

import java.util.List;
import java.util.Map;

import jchess.cache.boardCacheManager;
import jchess.common.BoardAgent;
import jchess.common.Player;
import jchess.common.PositionAgent;

public class boardManager {
    private static boardManager m_oInstance = null; 

    private boardManager() {
    } 
  
    public static boardManager getInstance() { 
        if (m_oInstance == null) 
        	m_oInstance = new boardManager(); 
  
        return m_oInstance; 
    }
    
    public PositionAgent getPosition(String stName) {
    	return boardCacheManager.getInstance().getPosition(stName);
    }
 
    public Map<String, PositionAgent> getAllPositions(){
    	return boardCacheManager.getInstance().getAllPositions();
    }
    
    public Player getPlayer1() {
    	return boardCacheManager.getInstance().getPlayer1();
    }

    public Player getPlayer2() {
    	return boardCacheManager.getInstance().getPlayer2();
    }

    public BoardAgent getBoard() {
    	return boardCacheManager.getInstance().getBoard();
    }
}