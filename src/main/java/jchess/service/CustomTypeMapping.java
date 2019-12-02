package jchess.service;

import jchess.common.enumerator.Direction;

class CustomTypeMapping {

	static public Direction convertStringToDirection(String stDirection) {
		switch(stDirection.toUpperCase()) {
		case "EDGE":
			return Direction.EDGE;
		case "VERTEX":
			return Direction.VERTEX;
		}
		return null;
	}
}
