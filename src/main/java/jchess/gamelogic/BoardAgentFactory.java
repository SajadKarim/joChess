package jchess.gamelogic;

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

public final class BoardAgentFactory implements IBoardFactory {
	public IPosition createPosition() {
		return new PositionAgent();
	}	

	public IPiece createPiece() {
		return new PieceAgent();
	}	

	public IRule createRule() {
		return new RuleAgent();
	}	
	
	public IPlayer createPlayer() {
		return new PlayerAgent();
	}
	
	public IBoard createBoard() {
		return new BoardAgent();
	}
}
