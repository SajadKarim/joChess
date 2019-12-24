package jchess.cache;

import java.util.Map;

import org.javatuples.Pair;

import jchess.common.IBoardAgent;

/**
 * This interface allows other modules to interact with storage (DB or File).
 * Its main responsibility is to load Board details.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface ICacheManager {
	public Boolean init();
    public IBoardAgent getBoard(String stGameId);
    public void loadBoardFromFile(String stGameId, String stBoardName);
    public Map<String, Pair<String, Integer>> getPossiblePlayerInEachBoard();
}
