package jchess.service;

import jchess.cache.BoardData;
import jchess.common.*;
import jchess.gamelogic.BoardAgent;

class FBDBService extends StorageService {
	public IBoardData getBoardData(String stFilePath) {
		IBoardData oBoard = new BoardData();
		XMLDeserializer.populateBoard(stFilePath, oBoard);
		return oBoard;
	}	

	public IBoardAgent getBoardAgent(String stFilePath) {
		IBoardAgent oBoard = new BoardAgent();
		XMLDeserializer.populateBoard(stFilePath, oBoard);
		return oBoard;
	}	
}
