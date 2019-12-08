package jchess.service;

import jchess.common.IBoardAgent;
import jchess.common.IBoardData;

public abstract class StorageService {
	public abstract IBoardData getBoardData(String stFilePath); 	
	public abstract IBoardAgent getBoardAgent(String stFilePath); 	
	
	public static StorageService create(STORAGE_TYPE enStorageType) {
		switch(enStorageType) {
			case FBDB:{
				return new FBDBService();
			}
		}
		return null;
	}
}
