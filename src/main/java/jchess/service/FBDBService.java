package jchess.service;

import java.io.File;
import java.util.SortedMap;
import java.util.TreeMap;

import org.javatuples.Pair;

import jchess.common.*;
import jchess.gamelogic.BoardAgentFactory;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

/**
 * This class is responsible to read or load from files.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

class FBDBService extends StorageService {
	private IAppLogger m_oLogger;

	public FBDBService(IAppLogger oLogger) {
		m_oLogger = oLogger;
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating FBDBService.", "FBDBService", "FBDBService");
	}
	
	public IBoard getBoard(IBoardFactory oBoardFactory, String stFilePath) {
		return BoardXMLDeserializer.getBoard(stFilePath, oBoardFactory, m_oLogger);
	}	

	public SortedMap<String, Pair<String, Integer>> getPlayersInEachBoard(String stFolderPath){
		m_oLogger.writeLog(LogLevel.INFO, stFolderPath, "getPossiblePlayerInEachBoard", "FBDBService");
		
		SortedMap<String, Pair<String, Integer>> mpData = new TreeMap<String, Pair<String, Integer>>();
		
		File oFolder = new File(stFolderPath);
		File[] arFiles = oFolder.listFiles();

		for (int i = 0; i < arFiles.length; i++) {
			if (arFiles[i].isFile() && arFiles[i].getName().endsWith(".xml")) {
				String stBoardFilePath = stFolderPath + arFiles[i].getName();
				
				IBoard oBoard = BoardXMLDeserializer.getBoardWithPlayerDetailsOnly(stBoardFilePath, new BoardAgentFactory(), m_oLogger);

				if( oBoard != null) {
					mpData.put(oBoard.getName(), new Pair<String, Integer>(arFiles[i].getName(), oBoard.getAllPlayers().size()));
				}
			}
		}
		
		return mpData;
	}
}
