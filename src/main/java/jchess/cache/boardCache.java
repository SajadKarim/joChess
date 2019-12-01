package jchess.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jchess.Bishop;
import jchess.King;
import jchess.Knight;
import jchess.Pawn;
import jchess.common.Player;
import jchess.Queen;
import jchess.Rook;
import jchess.boardManager;
import jchess.common.Board;
import jchess.common.BoardAgent;
import jchess.common.Path;
import jchess.common.Piece;
import jchess.common.PieceAgent;
import jchess.common.Position;
import jchess.common.PositionAgent;
import jchess.common.Quadrilateral;
import jchess.common.enumerator.Direction;

public class boardCache
{
	BoardAgent m_oBard;
	
    public boardCache() { 
    	m_oBard = new BoardAgent(new Board("","","","",808,700));
    }
        
    public PositionAgent getPosition(String stName) {
    	return m_oBard.getPosition(stName);
    }


    public Map<String, PositionAgent> getAllPositions(){
    	return m_oBard.getAllPositions();
    }
    
    public Player getPlayer1() {
    	return m_oBard.getPlayer("P1");
    }

    public Player getPlayer2() {
    	return m_oBard.getPlayer("P2");
    }
    
    public BoardAgent getBoard() {
    	return m_oBard;
    }
} 