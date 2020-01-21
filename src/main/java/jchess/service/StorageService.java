package jchess.service;

import java.util.Map;

import org.javatuples.Pair;

import jchess.common.IBoard;
import jchess.common.IBoardFactory;
import jchess.util.IAppLogger;

/**
 * This class provides abstraction to other modules and acts as Factory Pattern.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public abstract class StorageService {
	public abstract IBoard getBoard(IBoardFactory oBoardFactory, String stFilePath);
	public abstract Map<String, Pair<String, Integer>> getPlayersInEachBoard(String stFolderPath);

	public static StorageService create(StorageType enStorageType, IAppLogger oLogger) {		
		switch (enStorageType) {
			case FBDB:
				return new FBDBService(oLogger);
			default:
				return null;
		}
	}
}
