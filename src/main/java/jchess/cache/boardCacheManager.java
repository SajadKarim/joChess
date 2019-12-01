package jchess.cache;

import java.util.List;
import java.util.Map;

import jchess.common.BoardAgent;
import jchess.common.Player;
import jchess.common.PositionAgent;

public class boardCacheManager {
    private static boardCacheManager m_oInstance = null; 

    private boardCache m_o3PlayerGameCache;
    
    private boardCacheManager() {
    	m_o3PlayerGameCache = new boardCache();
    } 
  
    public static boardCacheManager getInstance() { 
        if (m_oInstance == null) 
        	m_oInstance = new boardCacheManager(); 
  
        return m_oInstance; 
    } 
    
    public PositionAgent getPosition(String stName) {
    	return m_o3PlayerGameCache.getPosition(stName);
    }
    
    public Map<String, PositionAgent> getAllPositions(){
    	return m_o3PlayerGameCache.getAllPositions();
    }
    
    public Player getPlayer1() {
    	return m_o3PlayerGameCache.getPlayer1();
    }

    public Player getPlayer2() {
    	return m_o3PlayerGameCache.getPlayer2();
    }

    public BoardAgent getBoard() {
    	return m_o3PlayerGameCache.getBoard();
    }
}
