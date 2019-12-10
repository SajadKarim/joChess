package jchess.common.enumerator;

/**
 * This datastructure is used to decide movement. It basically decides which parameter (datastructures) are
 * required to be considered when taking a specific move.
 * BLINKER: (Could not find appropriate name) It refers horse-with-blinkers that goes in a straight direction.
 * FILE_AND_RANK: Considers Rank and File information to do movements.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public enum Manoeuvre{
	BLINKER,
	FILE_AND_RANK,
	BLINKER_WITH_FILE_AND_RANK
}

