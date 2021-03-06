package jchess.ruleengine;

import java.util.Map;

import jchess.common.IBoardActivity;
import jchess.common.IBoardAgent;
import jchess.common.IMoveCandidate;
import jchess.common.IPieceAgent;
import jchess.common.IPlayerAgent;

/**
 * This interface aims to provide abstraction to classes that should provide functionality to process and execute no just the rules
 * that can be defined in the XML, but also entertain the custom rules that are not XML supportable. 
 * The classes should evaluate the pieces and, with the help of the rules defined against them, 
 * the should finds out all the possible move candidates the piece can perform. They should also facilitate the game engine 
 * in the execution of the rules.
 * 
 * This interface provides abstraction to different Rule Engines.
 * 
 * It is designed to keep the rule processing functionality loosely coupled to the main game logic therefore developer 
 * can create different code files for different boards.
 * 
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IRuleEngine {
	/**
	 * This method evaluates the rules defined for the piece and finds all the possible positions the piece can make.
	 * 
	 * @param oBoard IBoardAgent
	 * @param oPiece IPieceAgent
	 * return Map of String and IMoveCandidate
	 */
	public Map<String, IMoveCandidate> tryEvaluateAllRules(IBoardAgent oBoard, IPieceAgent oPiece);
	/**
	 * This method executes the rule provided with IMoveCandidate object.
	 * 
	 * @param oBoard IBoardAgent
	 * @param oMoveCandidate IPieceAgent
	 * return IBoardActivity
	 */
	public IBoardActivity tryExecuteRule(IBoardAgent oBoard, IMoveCandidate oMoveCandidate);
	/**
	 * This method checks whether the provided player is in danger or not.
	 * 
	 * @param oBoard
	 * @param oPlayer
	 * @return
	 */
	public IPlayerAgent tryCheckIfPlayerEndengered(IBoardAgent oBoard, IPlayerAgent oPlayer);
	/**
	 * This method check for Stalemate.
	 * 
	 * @param oBoard
	 * @param oPlayer
	 * @return
	 */
	public Boolean checkStalemate(IBoardAgent oBoard, IPlayerAgent oPlayer);
	/**
	 * This method checks whether the provided piece is in danger or not.
	 * 
	 * @param oBoard
	 * @param oPiece
	 * @return
	 */
	public IPieceAgent isPieceEndangered(IBoardAgent oBoard, IPieceAgent oPiece);
}
