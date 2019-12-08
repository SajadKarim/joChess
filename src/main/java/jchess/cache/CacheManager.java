package jchess.cache;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Provides;
import com.google.inject.Singleton;

import jchess.common.IBoard;
import jchess.common.IBoardAgent;
import jchess.gamelogic.BoardAgent;
import jchess.service.STORAGE_TYPE;
import jchess.service.StorageService;

/**
 * Implements ICacheManager.
 * Its main responsibility is to load Board details from secondary storage (DB or File).
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

@Singleton
public class CacheManager implements ICacheManager{
    private static CacheManager m_oInstance = null; 

    private Map<String, BoardCache> m_mpBoardCache;
    
    public CacheManager() {
    	m_mpBoardCache = new HashMap<String, BoardCache>();    	
    } 
  
    @Provides 
    @Singleton
    public ICacheManager provideCacheManager(){
        if (m_oInstance == null) 
       	m_oInstance = new CacheManager(); 
 
        return m_oInstance; 
    }
 /*   public static CacheManager getInstance() { 
        if (m_oInstance == null) 
       	m_oInstance = new CacheManager(); 
 
        return m_oInstance; 
    }
   */ 
    public IBoardAgent getBoard(String stName) {
    	if( m_mpBoardCache.containsKey(stName))
    		return m_mpBoardCache.get(stName).getBoard();
    	
    	return null;
    }
    
    public void loadBoardFromFile(String stName, String stFilePath) {
    	StorageService oStorageService = StorageService.create(STORAGE_TYPE.FBDB);
    	IBoardAgent oBoard = oStorageService.getBoardAgent(stFilePath);
    	
    	m_mpBoardCache.put(stName, new BoardCache(stName, oBoard));
    }
}
