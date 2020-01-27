package jchess.cache;

import jchess.common.IBoard;
import jchess.common.IBoardFactory;
import jchess.common.IPiece;
import jchess.common.IPlayer;
import jchess.common.IPosition;
import jchess.common.IRule;

/**
 * Factory class for BoardData.
 * 
 * @author  Sajad Karim
 * @since	26 Dec 2019
 */

public final class BoardDataFactory implements IBoardFactory {
	/**
	 * Instantiate PositionData.
	 */
	public IPosition createPosition() {
		return new PositionData();
	}	

	/**
	 * Instantiate PieceData.
	 */
	public IPiece createPiece() {
		return new PieceData();
	}	

	/**
	 * Instantiate RuleData.
	 */
	public IRule createRule() {
		return new RuleData();
	}

	/**
	 * Instantiate PlayerData.
	 */
	public IPlayer createPlayer() {
		return new PlayerData();
	}

	/**
	 * Instantiate BoardData.
	 */
	public IBoard createBoard() {
		return new BoardData();
	}
}
