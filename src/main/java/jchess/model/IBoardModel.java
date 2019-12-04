package jchess.model;

import java.awt.Image;
import java.util.List;

import jchess.common.Board;
import jchess.common.IBoard;
import jchess.common.Position;

public interface IBoardModel {
	public void setBoard(IBoard oBoard);	
	public IBoard getBoard();	
}
