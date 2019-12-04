package jchess.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Provides;
import com.google.inject.Singleton;

import jchess.common.Board;
import jchess.common.IBoard;
import jchess.common.IBoardData;
import jchess.common.Player;
import jchess.common.PlayerData;
import jchess.common.Position;
import jchess.service.STORAGE_TYPE;
import jchess.service.StorageService;

@Singleton
public class CacheManager implements ICacheManager{
    //private static CacheManager m_oInstance = null; 

    private Map<String, BoardCache> m_mpBoardCache;
    
    public CacheManager() {
    	m_mpBoardCache = new HashMap<String, BoardCache>();    	
    } 
  
    @Provides 
    @Singleton
    public ICacheManager provideCacheManager(){
    	return new CacheManager();
    }
 /*   public static CacheManager getInstance() { 
        if (m_oInstance == null) 
       	m_oInstance = new CacheManager(); 
 
        return m_oInstance; 
    }
   */ 
    public IBoard getBoard(String stName) {
    	if( m_mpBoardCache.containsKey(stName))
    		return m_mpBoardCache.get(stName).getBoard();
    	
    	return null;
    }
    
    public void loadBoardFromFile(String stName, String stFilePath) {
    	Board oBoard = new Board();
    	
    	StorageService oStorageService = StorageService.create(STORAGE_TYPE.FBDB);
    	oStorageService.populateBoard(stFilePath, (IBoardData)oBoard);
    	
    	m_mpBoardCache.put(stName, new BoardCache(stName, oBoard));
    }
}
