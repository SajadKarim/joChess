package jchess.cache;

import jchess.common.IBoard;

public interface ICacheManager {
    public IBoard getBoard(String stName);
    public void loadBoardFromFile(String stName, String stFilePath);
}
