package jchess.model;

import java.awt.Image;
import java.util.List;

import jchess.common.Board;
import jchess.common.IBoard;
import jchess.common.Position;

public class GameModel implements IGameModel, IBoardModel, IClockModel{
	IBoard m_oBoard;
	String m_stClockText;
	
	public GameModel() {
		m_stClockText = "--:--";
	}
	
	public void setBoard(IBoard oBoard) {
		m_oBoard = oBoard;
	}
	
	public IBoard getBoard() {
		return m_oBoard;
	}
	
	public String getClockText() {
		return m_stClockText;
	}
	public void setClockText(String stClockText) {
		m_stClockText = stClockText;
	}
	
	//region IBoardModel
	public int getBoardWidth() {
		return m_oBoard.getBoardWidth();
	}
	public int getBoardHeight() {
		return m_oBoard.getBoardHeight();
	}
	public Image getBoardImage() {
		return m_oBoard.getBoardImage();
	}
	public Image getActivCellImage() {
		return m_oBoard.getActivCellImage();
	}
	public Image getMarkedCellImage() {
		return m_oBoard.getMarkedCellImage();
	}
	public List<Position> getPositions(){
		return m_oBoard.getPositions();
	}

	//endregion
}
