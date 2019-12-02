package jchess.service;

import jchess.common.Board;

public abstract class StorageService {
	public abstract Board getBoard(); 	
	
	public static StorageService create(STORAGE_TYPE enStorageType) {
		switch(enStorageType) {
		case FBDB:{
			return new FBDBService();
		}
		}
		return null;
	}
}
