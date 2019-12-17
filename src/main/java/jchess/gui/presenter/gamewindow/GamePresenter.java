package jchess.gui.presenter.gamewindow;

import java.awt.Component;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import jchess.cache.ICacheManager;
import jchess.common.IPlayer;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.gamelogic.Game;
import jchess.gamelogic.IGame;
import jchess.gamelogic.IGameListener;
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

    public void init() {     	
    	m_oAppLogger.writeLog(LogLevel.DETAILED, "Initializing Game presenter.", "init", "GamePresenter");

    	m_oView.addListener(this);
        m_oView.init();
        
    	m_oGame.addListener(this);
    	m_oGame.init(m_oModel.getBoard());
    }
        
    public void showView() {
    	m_oView.drawView();
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
}
