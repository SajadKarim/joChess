package jchess.common;

/**
 * It fulfills functionality of Abstract Factory Pattern for BoardData and BoardAgent. 
 *
 * @author	Sajad Karim
 * @since	7 Dec 2019
 *
 */

public interface IBoardFactory {
	/**
	 * This method hides the creation of Rule. 
	 * It could be of type IRuleData or IRuleAgent.
	 * 
	 * @return IRule
	 */
	public IRule createRule();
	/**
	 * This method hides the creation of Rule. 
	 * It could be of type IPieceData or IPieceAgent.
	 * 
	 * @return IPiece
	 */
	public IPiece createPiece();
	/**
	 * This method hides the creation of Rule. 
	 * It could be of type IPlayerData or IPlayerAgent.
	 * 
	 * @return IPlayer
	 */
	public IPlayer createPlayer();
	/**
	 * This method hides the creation of Rule. 
	 * It could be of type IPositionData or IPositionAgent.
	 * 
	 * @return IPosition
	 */
	public IPosition createPosition();

	public IBoard createBoard();
}
