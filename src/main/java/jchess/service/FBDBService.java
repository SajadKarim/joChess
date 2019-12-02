package jchess.service;

import jchess.common.Board;

class FBDBService extends StorageService {
	public Board getBoard() {
		XMLDeserializer _ = new XMLDeserializer();
		
		return _.deserliaizeBoard("D:\\git\\repositories\\joChess\\src\\main\\resources\\boardlayout\\3PlayerBoard.xml");
		//return new Board("", "", "", "", 808, 700);
	}
	
}
