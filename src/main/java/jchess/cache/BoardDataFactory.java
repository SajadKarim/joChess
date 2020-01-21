package jchess.cache;

import jchess.common.IBoard;
import jchess.common.IBoardFactory;
import jchess.common.IPiece;
import jchess.common.IPlayer;
import jchess.common.IPosition;
import jchess.common.IRule;

public final class BoardDataFactory implements IBoardFactory {

	public IPosition createPosition() {
		return new PositionData();
	}	

	public IPiece createPiece() {
		return new PieceData();
	}	

	public IRule createRule() {
		return new RuleData();
	}

	public IPlayer createPlayer() {
		return new PlayerData();
	}

	public IBoard createBoard() {
		return new BoardData();
	}
}
