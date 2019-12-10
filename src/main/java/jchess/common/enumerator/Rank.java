package jchess.common.enumerator;

/**
 * This datastructure is used to decide movement on the basis of the Rank # of the Position.
 * Rank # (in 2 Player) represents rows of the board. It with 'File #' distinguishes Positions.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public enum Rank{
	SAME,
	FORWARD,
	BACKWARD,
	IGNORE
}

