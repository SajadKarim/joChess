package jchess.common;

import java.util.Map;

/**
 * IBoard provides abstraction to IBoardData and IBoardAgent.
 * It is mainly built for Cache module to make both the
 * type compatible with its implementation (data population logic). 
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 *
 */

public interface IBoard {
	/**
	 * Returns name of the Board.
	 * 
	 * @return String
	 */
	public String getName();
	/**
	 * Returns width of the Board.
	 * 
	 * @return int
	 */
	public int getBoardWidth();
	/**
	 * Returns height of the Board.
	 * 
	 * @return int
	 */
	public int getBoardHeight();	
	/**
	 * This method is exposed to do Board internal initializations, and,
	 * gives control to IBoardData and IBoardAgent to perform their own custom initializations.
	 * 
	 */
	public void init();
	/**
	 * Returns IPosition against the value provided to it.
	 * Method returns NULL if it does not find any value against the provided name.
	 * 
	 * @param stName
	 * @return IPosition 
	 */
	public IPosition getPosition(String stName);
	/**
	 * This method returns all the available IPosition objects.
	 * @return Map of String, IPosition
	 */
	public Map<String, IPosition> getAllPositions() ;
	/**
	 * Returns IPlayer against the value provided to it.
	 * Method returns NULL if it does not find any value against the provided name.
	 * 
	 * @param stName
	 * @return IPlayer 
	 */	
	public IPlayer getPlayer(String stName);
	/**
	 * This method returns all the available IPlayer objects.
	 * @return Map of String, IPlayer
	 */
	public Map<String, IPlayer > getAllPlayers();
	/**
	 * Returns IRule against the value provided to it.
	 * Method returns NULL if it does not find any value against the provided name.
	 * 
	 * @param stName
	 * @return IRule
	 */		
	public IRule getRule(String stName);
	/**
	 * This method returns all the available IRule objects.
	 * @return Map of String, IRule
	 */
	public  Map<String, IRule>  getAllRules();
	/**
	 * Returns IPiece against the value provided to it.
	 * Method returns NULL if it does not find any value against the provided name.
	 * 
	 * @param stName
	 * @return IPiece
	 */		
	public IPiece getUnlinkedPiece(String stName);
	/**
	 * This method returns all the available IPiece objects.
	 * @return Map of String, IPiece
	 */
	public  Map<String, IPiece>  getAllUnlinkedPieces();
	/**
	 * This method return the BoardData object.
	 * @return IBoardData 
	 */
	public IBoardData getBoardData();
	public String getRuleEngineName();
	public String getRuleProcessorName();
}
