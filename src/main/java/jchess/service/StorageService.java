package jchess.service;

import jchess.common.IBoardData;

public abstract class StorageService {
	public abstract void populateBoard(String stFilePath, IBoardData oBoard); 	
	
	public static StorageService create(STORAGE_TYPE enStorageType) {
		switch(enStorageType) {
			case FBDB:{
				return new FBDBService();
			}
		}
		return null;
	}
}
