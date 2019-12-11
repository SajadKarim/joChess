package jchess.service;

import java.util.Map;

import org.javatuples.Pair;

import jchess.common.IBoardAgent;
import jchess.common.IBoardData;

/**
 * This class provides abstraction to other modules and acts as Factory Pattern.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public abstract class StorageService {
	public abstract IBoardData getBoardData(String stFilePath); 	
	public abstract IBoardAgent getBoardAgent(String stFilePath); 	
	public abstract Map<String, Pair<String, Integer>> getPossiblePlayerInEachBoard(String stFolderPath);
	
	public static StorageService create(StorageType enStorageType) {
		switch(enStorageType) {
			case FBDB:{
				return new FBDBService();
			}
		}
		return null;
	}
}
