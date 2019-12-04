package jchess.model;

import java.awt.Image;
import java.util.List;

import jchess.common.Board;
import jchess.common.IBoard;
import jchess.common.Position;

public class BoardModel implements IBoardModel{
	IBoard m_oBoard;
	
	public BoardModel() {		
	}
	
	public void setBoard(IBoard oBoard) {
		m_oBoard = oBoard;
	}
	
	public IBoard getBoard() {
		return m_oBoard;
	}
}
