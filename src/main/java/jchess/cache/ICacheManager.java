package jchess.cache;

import jchess.common.IBoardAgent;

/**
 * This interface allows other modules to interact with storage (DB or File).
 * Its main responsibility is to load Board details.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface ICacheManager {
    public IBoardAgent getBoard(String stName);
    public void loadBoardFromFile(String stName, String stFilePath);
}
