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
		
		//BoardData abc = new BoardData();
		//abc.populate2plyaerboard();
		//XMLDeserializer.serializeBoard(abc);

		
		XMLDeserializer.populateBoard(stFilePath, oBoard);
		return oBoard;
	}	
}
