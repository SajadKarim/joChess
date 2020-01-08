package jchess.common.enumerator;

/**
 * This datastructure is used to decide movement. 
 * MOVE: To just make a move.
 * MOVE_AND_CAPTURE: To make a move and capture if Piece (opposite side) blocks the way.
 * MOVE_IFF_CAPTURE_POSSIBLE: Only to capture a Piece.
 * MOVE_TRANSIENT: Temporary move to decide upon further moves.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public enum RuleType{
	MOVE,
	MOVE_AND_CAPTURE,
	MOVE_IFF_CAPTURE_POSSIBLE,
	MOVE_TRANSIENT,
	CUSTOM
}

