package jchess.common;

/**
 * IPiece provides abstraction to IPieceData and IPieceAgent.
 * It fulfills functionality of Abstract Factory Pattern, and
 * it is mainly built for Cache module to make both the
 * type compatible with its implementation (data population logic). 
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 *
 */

public interface IPiece {
	public void init();
	public String getName();	
	public String getImagePath();
	public String getFamily();
	public IPieceData getPieceData();
	public IPiece clone();
}
