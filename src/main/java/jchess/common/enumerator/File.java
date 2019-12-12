package jchess.common.enumerator;

/**
 * This datastructure is used to decide movement on the basis of the File # of the Position.
 * File # (in 2 Player) represents columns of the board. It with 'Rank #' distinguishes Positions.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public enum File{
	SAME,
	FORWARD,
	BACKWARD,
	IGNORE	
}

