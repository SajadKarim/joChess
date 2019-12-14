package jchess.gui.model.gamewindow;

import java.awt.Image;
import java.util.Map;

import com.google.inject.Singleton;

import jchess.common.IBoardAgent;
import jchess.common.IPlayer;
import jchess.common.IPositionAgent;

/**
 * This class hold all the data that is required to draw Game in whole.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

@Singleton
public class GameModel implements IGameModel, IBoardModel, IClockModel, IPlayerModel{
	IBoardAgent m_oBoard;
	String m_stClockText;
	IPlayer m_oPlayer;
	
	public GameModel() {
		m_stClockText = "--:--";
	}

	//region:  IGameModel
	public void setBoard(IBoardAgent oBoard) {
		m_oBoard = oBoard;
	}
	
	public IBoardAgent getBoard() {
		return m_oBoard;
	}
	
	public void setPlayer(IPlayer oPlayer) {
		m_oPlayer = oPlayer;
	}
	
	public IPlayer getPlayer() {
		return m_oPlayer;
	}
	
	//region: IClockModel
	public String getClockText() {
		return m_stClockText;
	}
	//endregion
	
	public void setClockText(String stClockText) {
		m_stClockText = stClockText;
	}
	//endregion
	
	//region: IBoardModel
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
	public Map<String, IPositionAgent> getPositions(){
		return m_oBoard.getAllPositionAgents();
	}
	//endregion
	
	//region: IPlayer
	public String getPlayerName() {
		return m_oPlayer.getName();
	}
	//endregion
}
