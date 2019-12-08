package jchess.common;

import java.util.Map;

/**
 * IBoard provides abstraction to IBoardData and IBoardAgent.
 * It fulfills functionality of Abstract Factory Pattern, and
 * it is mainly built for Cache module to make both the
 * type compatible with its implementation (data population logic). 
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 *
 */

public interface IBoard {
	/**
	 * 
	 * @return String: Board name.
	 */
	public String getName();
	/**
	 * 
	 * @return int: Width of board.
	 */
	public int getBoardWidth();
	/**
	 * 
	 * @return int: Height of board.
	 */
	public int getBoardHeight();	
	
	public void init();
	public IRule createRule();
	public IPiece createPiece();
	public IPlayer createPlayer();
	public IPosition createPosition();
	
	public IPosition getPosition(String stName);
	public Map<String, IPosition> getAllPositions() ;
	
	public IPlayer getPlayer(String stName);
	public Map<String, IPlayer > getAllPlayers();
	
	public IRule getRule(String stName);
	public  Map<String, IRule>  getAllRules();
	
	public IPiece getPiece(String stName);
	public  Map<String, IPiece>  getAllPieces();
	
	public IBoardData getBoardData();
}
