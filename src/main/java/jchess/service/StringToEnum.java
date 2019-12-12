package jchess.service;

import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

/**
 * This is helper class that maps String to Enums.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

class StringToEnum {

	static public Direction convertStringToDirection(String stDirection) {
		switch(stDirection.toUpperCase()) {
		case "EDGE":
			return Direction.EDGE;
		case "VERTEX":
			return Direction.VERTEX;
		}
		return null;
	}
	
	static public RuleType convertStringToRuleType( String stRuleType) {
		switch(stRuleType.toUpperCase()) {
		case "MOVE":
			return RuleType.MOVE;
		case "MOVE_AND_CAPTURE":
			return RuleType.MOVE_AND_CAPTURE;
		case "MOVE_IFF_CAPTURE_POSSIBLE":
			return RuleType.MOVE_IFF_CAPTURE_POSSIBLE;
		case "MOVE_TRANSIENT":
			return RuleType.MOVE_TRANSIENT;
		}
		return null;
	}

	static public Manoeuvre convertStringToManoeuvre( String stManoeuvre) {
		switch(stManoeuvre.toUpperCase()) {
		case "BLINKER":
			return Manoeuvre.BLINKER;
		case "BLINKER_WITH_FILE_AND_RANK":
			return Manoeuvre.BLINKER_WITH_FILE_AND_RANK;
		case "FILE_AND_RANK":
			return Manoeuvre.FILE_AND_RANK;
		}
		return null;
	}

	static public File convertStringToFile( String stFile) {
		switch(stFile.toUpperCase()) {
		case "BACKWARD":
			return File.BACKWARD;
		case "FORWARD":
			return File.FORWARD;
		case "SAME":
			return File.SAME;
		case "IGNORE":
			return File.IGNORE;
		}
		return null;
	}

	static public Rank convertStringToRank( String stRank) {
		switch(stRank.toUpperCase()) {
		case "BACKWARD":
			return Rank.BACKWARD;
		case "FORWARD":
			return Rank.FORWARD;
		case "SAME":
			return Rank.SAME;
		case "IGNORE":
			return Rank.IGNORE;
		}
		return null;
	}

	static public Family convertStringToFamily( String stFamily) {
		switch(stFamily.toUpperCase()) {
		case "DIFFERENT":
			return Family.DIFFERENT;
		case "PROVIDED":
			return Family.PROVIDED;
		case "SAME":
			return Family.SAME;
		case "IGNORE":
			return Family.IGNORE;
		}
		return null;
	}
}
