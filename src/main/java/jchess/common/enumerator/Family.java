package jchess.common.enumerator;

/**
 * This datastructure is used to decide movement on the basis of the Color of the Position.
 * Positions having similar color are considered as one family.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public enum Family {
	SAME,
	DIFFERENT,
	PROVIDED,
	IGNORE
}

