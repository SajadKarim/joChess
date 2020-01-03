package jchess.gamelogic;

import jchess.common.IBoard;
import jchess.common.IBoardFactory;
import jchess.common.IPiece;
import jchess.common.IPlayer;
import jchess.common.IPosition;
import jchess.common.IRule;

public class BoardAgentFactory implements IBoardFactory {

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
