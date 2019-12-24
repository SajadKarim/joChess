package jchess.gui.model.gamewindow;

import java.awt.Image;
import java.util.LinkedList;
import java.util.Map;

import com.google.inject.Singleton;

import jchess.common.IBoardAgent;
import jchess.common.IMove;
import jchess.common.IPlayer;
import jchess.common.IPlayerAgent;
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
	IPlayerAgent m_oPlayer;
	
	int m_nCurrentIndex;
	LinkedList<IMove> m_llMoves;
	
	public GameModel() {
		m_stClockText = "--:--";
		
		m_nCurrentIndex = -1;
		m_llMoves = new LinkedList<IMove>();
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
	public Map<String, IPositionAgent> getPositions(){
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
	
	public Map<String, IPlayerAgent > getAllPlayerAgents(){
		return (Map<String, IPlayerAgent>)(Object)m_oBoard.getAllPlayers();
	}
	
	public String getRuleEngineName() {
		return m_oBoard.getRuleEngineName();
	}
	
	public void addMove(IMove oMove) {
		if( m_nCurrentIndex != m_llMoves.size() - 1) {
			for(int nIndex = m_llMoves.size() - 1; nIndex > m_nCurrentIndex; nIndex--) {
				m_llMoves.remove(nIndex);
			}
		}
		
		m_llMoves.add(oMove);
		m_nCurrentIndex = m_llMoves.size() - 1;
	}

	public IMove tryUndoMove() {
		IMove oMove = null;
		
		if( m_nCurrentIndex >= 0) {
			oMove = m_llMoves.get(m_nCurrentIndex);
			m_nCurrentIndex--;
		}
		
		return oMove;
	}

	public IMove tryRedoMove() {
		IMove oMove = null;
		
		if( m_nCurrentIndex < m_llMoves.size() -1) {
			m_nCurrentIndex++;
			oMove = m_llMoves.get(m_nCurrentIndex);			
		}
		
		return oMove;
	}
	
	public void updatePlayerNames(Map<String, IPlayerAgent> mpPlayer) {
		for(Map.Entry<String, IPlayerAgent> itPlayer: m_oBoard.getAllPlayerAgents().entrySet()) {
			itPlayer.getValue().setFirstName(mpPlayer.get(itPlayer.getKey()).getFirstName());
		}
	}
}
