package jchess.cache;

import java.util.HashMap;
import java.util.Map;

import org.javatuples.Pair;

import com.google.inject.Provides;
import com.google.inject.Singleton;

import jchess.common.IBoardAgent;
import jchess.service.StorageType;
import jchess.service.StorageService;

/**
 * Implements ICacheManager.
 * Its main responsibility is to load Board details from secondary storage (DB or File).
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class CacheManager implements ICacheManager{
    private static String BOARDLAYOUTS_DIRECTORY = "D:\\git\\repositories\\joChess\\src\\main\\resources\\boardlayout\\"; 

    private static CacheManager m_oInstance = null; 

    private Map<String, BoardCache> m_mpBoardCache;
    private Map<String, Pair<String, Integer>> m_mpPlayerAllowedInBoard;
    
    public CacheManager() {
    	System.out.println("Cache manager new instance.");
    	m_mpBoardCache = new HashMap<String, BoardCache>();
    	m_mpPlayerAllowedInBoard = new HashMap<String, Pair<String, Integer>>();
    } 
  
    public static ICacheManager getInstance(){
        if (m_oInstance == null) 
        	m_oInstance = new CacheManager(); 
 
        return m_oInstance; 
    }

    public IBoardAgent getBoard(String stName) {
    	if( m_mpBoardCache.containsKey(stName))
    		return m_mpBoardCache.get(stName).getBoard();
    	
    	return null;
    }
    
    public Boolean init() {
    	StorageService oStorageService = StorageService.create(StorageType.FBDB);
    	m_mpPlayerAllowedInBoard = oStorageService.getPossiblePlayerInEachBoard(BOARDLAYOUTS_DIRECTORY);

    	if( m_mpPlayerAllowedInBoard == null)
    		return false;
    	
    	return false;
    }
    
    public void loadBoardFromFile(String stName, String stFilePath) {
    	StorageService oStorageService = StorageService.create(StorageType.FBDB);
    	IBoardAgent oBoard = oStorageService.getBoardAgent(BOARDLAYOUTS_DIRECTORY + stFilePath);
    	
    	m_mpBoardCache.put(stName, new BoardCache(stName, oBoard));
    }
    
    public Map<String, Pair<String, Integer>> getPossiblePlayerInEachBoard(){
    	return new HashMap<String, Pair<String, Integer>>(m_mpPlayerAllowedInBoard);
    }

}
