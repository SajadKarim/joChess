package jchess.gui.presenter.gamewindow;

import java.awt.Component;
import java.util.Map;

import javax.swing.JDialog;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import jchess.cache.ICacheManager;
import jchess.common.IMove;
import jchess.common.IPieceAgent;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.gui.DialogResult;
import jchess.gamelogic.IGame;
import jchess.gui.model.gamewindow.*;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;
import jchess.gui.view.gamewindow.*;

/**
 * This is class is responsible to show or load Game window with the help of model and presenter.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public class GamePresenter extends AbstractModule implements IGamePresenter{
    private IGame m_oGame;
    private final IGameView m_oView;
    private final IGameModel m_oModel;
    private final ICacheManager m_oCacheManager;
    private final IAppLogger m_oAppLogger;
    
    @Inject
    public GamePresenter(final IGame oGame, final IGameView oView, final IGameModel oModel, final ICacheManager oCacheManager, final IAppLogger oAppLogger) {
        m_oGame = oGame;
    	m_oView = oView;
    	m_oModel = oModel;
        m_oAppLogger  = oAppLogger;
        m_oCacheManager = oCacheManager;
    }

	public void updatePlayerNames(Map<String, IPlayerAgent> mpPlayer) {
		m_oModel.updatePlayerNames(mpPlayer);
	}

    public void init() {     	
    	m_oAppLogger.writeLog(LogLevel.DETAILED, "Initializing Game presenter.", "init", "GamePresenter");

    	m_oView.addListener(this);
        m_oView.init();
        
    	m_oGame.addListener(this);
    	m_oGame.init();
    }
        
    public void showView() {
    	m_oView.drawComponents();
    }
    
    public Component getViewComponent() {
    	return m_oView.getViewComponent();
    }
    
    //region: Notifications from GameView
    public void onPositionClicked(IPositionAgent oPosition) {
    	m_oGame.onBoardActivity(oPosition);
    	m_oView.repaintBoardView();
    }
    //endregion
    
    //region: Notifications from Game
	public void onTimerUpdate_SecondsElapsed(int nRemainingSeconds) {
        int nMintues = (nRemainingSeconds / 60) % 60;
        int nSeconds = nRemainingSeconds  % 60;
		m_oModel.setClockText(String.format("%02d", nMintues) + ":" + String.format("%02d", nSeconds));

		m_oView.repaintClockView();
	}
	
	public void onTimerUpdate_TimerElapsed(IPlayerAgent oPlayer) {
		m_oModel.setClockText("--:--");
		m_oModel.setPlayer(oPlayer);
		m_oView.repaintClockView();
	}

	public void onCurrentPlayerChanged(IPlayerAgent oPlayer) {
		m_oModel.setPlayer(oPlayer);
		m_oView.repaintPlayerView();
	}
	//endregion

	@Override
	public void getView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JDialog tryGetViewJDialog() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onViewClosed(DialogResult oFormAction, Object oData) {
		// TODO Auto-generated method stub
		
	}

	public void onMoveMadeByPlayer(IPlayerAgent oPlayer, IMove oMove) {
		m_oModel.addMove(oMove);
		m_oView.addMove(oMove.toString());
	}

	@Override
	public void onPlayerRequestForUndoMove() {
		IMove oMove = m_oModel.tryUndoMove();
		if( oMove != null) {
			m_oGame.tryUndoMove(oMove.getPlayer());
			m_oView.removeMove(oMove.toString());
			
			for(Map.Entry<String, IPieceAgent> it: oMove.getPriorMoveDetails().entrySet()) {
				IPositionAgent oPosition = m_oModel.getBoard().getPositionAgent(it.getKey());
				oPosition.setPiece(it.getValue());
			}
			m_oView.repaintBoardView();
		}
	}

	@Override
	public void onPlayerRequestForRedoMove() {
		IMove oMove = m_oModel.tryRedoMove();
		if( oMove != null) {
			m_oGame.tryRedoMove(oMove.getPlayer());
			m_oView.addMove(oMove.toString());

			for(Map.Entry<String, IPieceAgent> it: oMove.getPostMoveDetails().entrySet()) {
				IPositionAgent oPosition = m_oModel.getBoard().getPositionAgent(it.getKey());
				oPosition.setPiece(it.getValue());
			}
			m_oView.repaintBoardView();
		}
	}
}
