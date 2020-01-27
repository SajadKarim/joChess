package jchess.cache;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.javatuples.Pair;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jchess.common.IBoardAgent;
import jchess.common.ICacheManager;
import jchess.gamelogic.BoardAgentFactory;
import jchess.service.StorageType;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;
import jchess.service.StorageService;

/**
 * This class is responsbile to load Board details from secondary storage (DB or File).
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

@Singleton
public final class CacheManager implements ICacheManager {
	/**
	 * Path for Chess-board XML files.
	 */
    private final static String BOARDLAYOUTS_DIRECTORY = getJarPath() + File.separator;
    
    /**
     * Hashmap to maintain active Chess-boards.
     */
    private Map<String, BoardCache> m_mpBoardCache;

    // TODO: #REFACTOR need to remove this map to a suitable place.
    private Map<String, Pair<String, Integer>> m_mpPlayerAllowedInBoard;
    
    /**
     * Instance of Logging class.
     */
    private IAppLogger m_oLogger;
    
    @Inject
    /**
     * Constructor.
     * 
     * @param oLogger Instance of Logging class.
     */
    public CacheManager(IAppLogger oLogger) {
    	m_oLogger = oLogger;
    	m_mpBoardCache = new HashMap<String, BoardCache>();
    	m_mpPlayerAllowedInBoard = new HashMap<String, Pair<String, Integer>>();
    
    	m_oLogger.writeLog(LogLevel.INFO, "Instantiating CacheManager.", "CacheManager", "CacheManager");

        init();
    } 
  
    // TODO: #REFACTOR move to utils class
    static String getJarPath() {
    	String path = CacheManager.class.getClassLoader().getResource("boardlayout").getPath();
        //String path = CacheManager.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        path = path.replaceAll("[a-zA-Z0-9%!@#$%^\\-&*\\(\\)\\[\\]\\{\\}\\.\\,\\s]+\\.jar[a-zA-Z0-9%!\\-@#$%^&*\\\\(\\\\)\\\\[\\\\]\\\\{\\\\}\\\\.\\\\,\\\\s]*/", "");
        path = path.replaceAll("file:/", "");
        int lastSlash = path.lastIndexOf(File.separator); 
        if (path.length() - 1 == lastSlash) {
            path = path.substring(0, lastSlash);
        }
        path = path.replace("%20", " ");
        return path;
    }
    
    /**
     * Returns Chess-board data class object.
     */
    public IBoardAgent getBoard(String stGameId) {
    	if (m_mpBoardCache.containsKey(stGameId)) {
    		return m_mpBoardCache.get(stGameId).getBoard();
    	}
    	
    	return null;
    }
    
    /**
     * Initializes cache manager.
     */
    public Boolean init() {
    	try {
	    	StorageService oStorageService = StorageService.create(StorageType.FBDB, m_oLogger);
	    	m_mpPlayerAllowedInBoard = oStorageService.getPlayersInEachBoard(BOARDLAYOUTS_DIRECTORY);
	
	    	if (m_mpPlayerAllowedInBoard == null) {
	    		return false;
	    	}
    	} catch (java.lang.Exception ex) {
    		m_oLogger.writeLog(LogLevel.ERROR, ex.toString(), "init", "CacheManager");
    	}

    	return false;
    }
    
    /**
     * Loads Chess-board from XML file.
     */
    public void loadBoardFromFile(String stGameId, String stBoardName) {
    	StorageService oStorageService = StorageService.create(StorageType.FBDB, m_oLogger);
    	IBoardAgent oBoard = (IBoardAgent) oStorageService.getBoard(new BoardAgentFactory(), BOARDLAYOUTS_DIRECTORY + stBoardName);
    	
    	m_mpBoardCache.put(stGameId, new BoardCache(stBoardName, oBoard));
    }
    
    /**
     * Returns Players' information for each Chess-board.
     */
    public SortedMap<String, Pair<String, Integer>> getPossiblePlayerInEachBoard() {
    	return new TreeMap<String, Pair<String, Integer>>(m_mpPlayerAllowedInBoard);
    }
}
