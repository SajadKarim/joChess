package jchess.cache;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.javatuples.Pair;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jchess.common.IBoardAgent;
import jchess.service.StorageType;
import jchess.util.GUI;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;
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
    private static String BOARDLAYOUTS_DIRECTORY = GUI.getJarPath() + "boardlayout" + File.separator;
    
    private Map<String, BoardCache> m_mpBoardCache;
    private Map<String, Pair<String, Integer>> m_mpPlayerAllowedInBoard;
    
    private IAppLogger m_oLogger;
    
    @Inject
    public CacheManager(IAppLogger oLogger) {
    	m_oLogger = oLogger;
    	m_mpBoardCache = new HashMap<String, BoardCache>();
    	m_mpPlayerAllowedInBoard = new HashMap<String, Pair<String, Integer>>();
    
    	m_oLogger.writeLog(LogLevel.INFO, "Instantiating CacheManager.", "CacheManager", "CacheManager");

    	init();
    } 
  
    public IBoardAgent getBoard(String stGameId) {
    	if( m_mpBoardCache.containsKey(stGameId))
    		return m_mpBoardCache.get(stGameId).getBoard();
    	
    	return null;
    }
    
    public Boolean init() {
    	StorageService oStorageService = StorageService.create(StorageType.FBDB);
    	m_mpPlayerAllowedInBoard = oStorageService.getPossiblePlayerInEachBoard(BOARDLAYOUTS_DIRECTORY);

    	if( m_mpPlayerAllowedInBoard == null)
    		return false;
    	
    	return false;
    }
    
    public void loadBoardFromFile(String stGameId, String stBoardName) {
    	StorageService oStorageService = StorageService.create(StorageType.FBDB);
    	IBoardAgent oBoard = oStorageService.getBoardAgent(BOARDLAYOUTS_DIRECTORY + stBoardName + ".xml");
    	
    	m_mpBoardCache.put(stGameId, new BoardCache(stBoardName, oBoard));
    }
    
    public Map<String, Pair<String, Integer>> getPossiblePlayerInEachBoard(){
    	return new HashMap<String, Pair<String, Integer>>(m_mpPlayerAllowedInBoard);
    }

}