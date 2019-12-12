package jchess.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.javatuples.Pair;

import jchess.cache.BoardData;
import jchess.common.*;
import jchess.gamelogic.BoardAgent;

/**
 * This class is responsible to read or load from files.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

class FBDBService extends StorageService {
	public IBoardData getBoardData(String stFilePath) {
		IBoardData oBoard = new BoardData();
		BoardXMLDeserializer.populateBoard(stFilePath, oBoard);
		return oBoard;
	}	

	public IBoardAgent getBoardAgent(String stFilePath) {
		IBoardAgent oBoard = new BoardAgent();
		BoardXMLDeserializer.populateBoard(stFilePath, oBoard);
		return oBoard;
	}	
	
	public Map<String, Pair<String, Integer>> getPossiblePlayerInEachBoard(String stFolderPath){
		Map<String, Pair<String, Integer>> mpData = new HashMap<String, Pair<String, Integer>>();
		
		File oFolder = new File(stFolderPath);
		File[] arFiles = oFolder.listFiles();

		for (int i = 0; i < arFiles.length; i++) {
			if (arFiles[i].isFile() && arFiles[i].getName().endsWith(".xml")) {
				String stBoardFilePath = stFolderPath + arFiles[i].getName();
				
				IBoardAgent oBoard = new BoardAgent();
				BoardXMLDeserializer.populateBoardPlayerDetailsOnly(stBoardFilePath, oBoard);

				if( oBoard != null) {
					mpData.put(oBoard.getName(), new Pair<String, Integer>(arFiles[i].getName(), oBoard.getAllPlayers().size()));
				}
			}
		}
		
		return mpData;
	}
}
