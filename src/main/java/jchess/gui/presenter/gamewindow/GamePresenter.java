package jchess.gui.presenter.gamewindow;

import java.awt.Component;
import java.util.Map;

import javax.swing.JDialog;

import org.jdesktop.application.View;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import jchess.common.IBoardActivity;
import jchess.common.ICacheManager;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.gui.DialogResult;
import jchess.gamelogic.IGame;
import jchess.gui.IGUIManager;
import jchess.gui.model.gamewindow.IGameModel;
import jchess.gui.view.gamewindow.IGameView;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

/**
 * This is class is responsible to show or load Game window with the help of model and presenter.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public final class GamePresenter extends AbstractModule implements IGamePresenter {
    private IGame m_oGame;
    private final IGameView m_oView;
    private final IGameModel m_oModel;
    private final ICacheManager m_oCacheManager;
    private final IAppLogger m_oLogger;
    private final IGUIManager m_oGUIHandle;
    
    @Inject
    public GamePresenter(final IGUIManager oGUIHandle, final IGame oGame, final IGameView oView, final IGameModel oModel, final ICacheManager oCacheManager, final IAppLogger oLogger) {
        m_oGame = oGame;
    	m_oView = oView;
    	m_oModel = oModel;
        m_oLogger  = oLogger;
        m_oCacheManager = oCacheManager;
        m_oGUIHandle = oGUIHandle;
        
     	m_oLogger.writeLog(LogLevel.INFO, "Instantiating GamePresenter.", "GamePresenter", "GamePresenter");
    }

	public void updatePlayerNames(Map<String, IPlayerAgent> mpPlayer) {
     	m_oLogger.writeLog(LogLevel.DETAILED, "Updating player names.", "updatePlayerNames", "GamePresenter");

		m_oModel.updatePlayerNames(mpPlayer);
	}

    public void init() {     	
    	m_oLogger.writeLog(LogLevel.INFO, "Initializing Game presenter.", "init", "GamePresenter");

    	m_oView.addListener(this);
        m_oView.init();
        
    	m_oGame.addListener(this);
    	m_oGame.init();
    }
        
    public void showView() {
     	m_oLogger.writeLog(LogLevel.DETAILED, "Displaying view.", "showView", "GamePresenter");

    	m_oView.drawComponents();

    	m_oGUIHandle.showGameStartPopup();
    	
    	m_oGame.start();
    }
    
    public Component tryGetViewComponent() {
    	return m_oView.getViewComponent();
    }
    
    //region: Notifications from GameView
    public void onPositionClicked(IPositionAgent oPosition) {
     	m_oLogger.writeLog(LogLevel.DETAILED, "User clicked on a position.", "onPositionClicked", "GamePresenter");

     	m_oGame.onBoardActivity(oPosition);
    	m_oView.repaintBoardView();
    }
    //endregion
    
    //region: Notifications from Game
	public void onTimerUpdate_SecondsElapsed(int nRemainingSeconds) {
     	m_oLogger.writeLog(LogLevel.DETAILED, "Timer notification for seconds elapse.", "onTimerUpdate_SecondsElapsed", "GamePresenter");

     	// TODO: Move following logic to respective View
        int nMintues = (nRemainingSeconds / 60) % 60;
        int nSeconds = nRemainingSeconds  % 60;
		m_oModel.setClockText(String.format("%02d", nMintues) + ":" + String.format("%02d", nSeconds));

		m_oView.repaintClockView();
	}
	
	public void onTimerUpdate_TimerElapsed(IPlayerAgent oPlayer) {
     	m_oLogger.writeLog(LogLevel.DETAILED, "Timer notification for exhausting all the time.", "onTimerUpdate_TimerElapsed", "GamePresenter");

     	m_oModel.setClockText("--:--");
		m_oModel.setPlayer(oPlayer);
		m_oView.repaintClockView();
	}

	public void onCurrentPlayerChanged(IPlayerAgent oPlayer) {
     	m_oLogger.writeLog(LogLevel.DETAILED, "Player turn changes. Player=" + oPlayer.getName(), "onCurrentPlayerChanged", "GamePresenter");

     	m_oModel.setPlayer(oPlayer);
		m_oView.repaintPlayerView();
	}
	//endregion

	@Override
	public View tryGetJDesktopView() {
		return null;
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

	public void onMoveMadeByPlayer(IPlayerAgent oPlayer, IBoardActivity oMove) {
     	m_oLogger.writeLog(LogLevel.DETAILED, "A move made by plyaer. Player=" + oPlayer.getName(), "onMoveMadeByPlayer", "GamePresenter");

		m_oView.addBoardActivity(oMove.toString());
	}

	@Override
	public void onPlayerRequestForUndoBoardActivity() {
     	m_oLogger.writeLog(LogLevel.DETAILED, "Undoing last move.", "onPlayerRequestForUndoBoardActivity", "GamePresenter");

     	IBoardActivity oActivity = m_oModel.tryUndoBoardActivity();
		if (oActivity != null) {
			m_oGame.setPlayerAsActivePlayer(oActivity.getPlayer());
			m_oView.removeBoardActivity(oActivity.toString());
			
			m_oView.repaintBoardView();
		}
	}

	@Override
	public void onPlayerRequestForRedoBoardActivity() {
     	m_oLogger.writeLog(LogLevel.DETAILED, "Redoing last move.", "onPlayerRequestForRedoBoardActivity", "GamePresenter");

     	IBoardActivity oActivity = m_oModel.tryRedoBoardActivity();
		if (oActivity != null) {
			m_oGame.setPlayerTurnAsLast(oActivity.getPlayer());
			m_oView.addBoardActivity(oActivity.toString());

			m_oView.repaintBoardView();
		}
	}
	
	public void endCurrentGame() {
		m_oGUIHandle.closeGameWindow();
	}
	
	public void displayConfirmDialog(String stConfirmDialogMessage, String stConfirmDialogTitle) {
		m_oGUIHandle.popUpConfirmDialog(stConfirmDialogMessage, stConfirmDialogTitle);
	}
}
