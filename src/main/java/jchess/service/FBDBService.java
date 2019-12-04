package jchess.service;

import jchess.common.*;

class FBDBService extends StorageService {
	public void populateBoard(String stFilePath, IBoardData oBoard) {
		XMLDeserializer.populateBoard(stFilePath, oBoard);
	}	
}
