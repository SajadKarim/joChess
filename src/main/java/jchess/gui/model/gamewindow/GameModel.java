package jchess.gui.model.gamewindow;

import java.awt.Image;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jchess.common.IBoardActivity;
import jchess.common.IBoardAgent;
import jchess.common.IPlayer;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.util.IAppLogger;

/**
 * This class hold all the data that is required to draw Game in whole.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

@Singleton
public final class GameModel implements IGameModel, IBoardModel, IClockModel, IPlayerModel {
	private IBoardAgent m_oBoard;
	private String m_stClockText;
	private IPlayerAgent m_oPlayer;
	
	private IAppLogger m_oLogger;
	
	@Inject
	public GameModel(IAppLogger oLogger) {
		m_oLogger = oLogger;
		
		m_stClockText = "--:--";
		
     	//m_oLogger.writeLog(LogLevel.DETAILED, "Instantiating GameModel.", "GameModel", "GameModel");
	}

	//region:  IGameModel
	public void setBoard(IBoardAgent oBoard) {
		m_oBoard = oBoard;
	}
	
	public IBoardAgent getBoard() {
		return m_oBoard;
	}
	
	public void setPlayer(IPlayerAgent oPlayer) {
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
	public Map<String, IPositionAgent> getPositions() {
		return m_oBoard.getAllPositionAgents();
	}
	//endregion
	
	//region: IPlayer
	public String getPlayerName() {
		return m_oPlayer.getName();
	}

	public String getPlayerFirstName() {
		return m_oPlayer.getFirstName();
	}
	//endregion
	
	public Map<String, IPlayerAgent > getAllPlayerAgents() {
		return (Map<String, IPlayerAgent>)(Object)m_oBoard.getAllPlayers();
	}
	
	public String getRuleEngineName() {
		return m_oBoard.getRuleEngineName();
	}
	
	public void addBoardActivity(IBoardActivity oActivity) {
		m_oBoard.addActivity(oActivity);
	}
	
	public IBoardActivity tryUndoBoardActivity() {
		return m_oBoard.undoLastActivity();
	}

	public IBoardActivity tryRedoBoardActivity() {
		return m_oBoard.redoLastActivity();
	}
	
	public void updatePlayerNames(Map<String, IPlayerAgent> mpPlayer) {
     	//m_oLogger.writeLog(LogLevel.DETAILED, "Updating player's name.", "updatePlayerNames", "GameModel");

		for (Map.Entry<String, IPlayerAgent> itPlayer: m_oBoard.getAllPlayerAgents().entrySet()) {
			itPlayer.getValue().setFirstName(mpPlayer.get(itPlayer.getKey()).getFirstName());
		}
	}
}
