package jchess.presenter.gamewindow;

import java.awt.Component;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import jchess.cache.ICacheManager;
import jchess.common.IPlayer;
import jchess.common.IPositionAgent;
import jchess.gamelogic.Game;
import jchess.model.gamewindow.*;
import jchess.view.gamewindow.*;

/**
 * This is class is responsible to show or load Game window with the help of model and presenter.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public class GamePresenter extends AbstractModule implements IGameView_Callback, IGamePresenter_Callback{
    private Game m_oGame;
    private IGameView m_oView;
    private IGameModel m_oModel;
    private final ICacheManager m_oCacheManager;
    
    @Inject
    public GamePresenter(final ICacheManager oCacheManager) {
        m_oModel = new GameModel();
        m_oCacheManager = oCacheManager;
    }

    public void init(String stBoardName, String stBoardFilePath) {   	
    	loadModel(stBoardName, stBoardFilePath);
    	
        m_oView = new GameView(this, m_oModel);
        m_oView.init();
        
    	m_oGame = new Game(this, m_oModel.getBoard());
    }
    
    private void loadModel(String stBoardName, String stBoardFilePath) {
    	m_oCacheManager.loadBoardFromFile(stBoardName, stBoardFilePath);
    	m_oModel.setBoard(m_oCacheManager.getBoard(stBoardName));
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
	
	public void onTimerUpdate_TimerElapsed(IPlayer oPlayer) {
		m_oModel.setClockText("--:--");
		m_oModel.setPlayer(oPlayer);
		m_oView.repaintClockView();
	}
	
	public void updateCurrentPlayer(IPlayer oPlayer) {
		m_oModel.setPlayer(oPlayer);
		m_oView.repaintPlayerView();
	} 
	//endregion
}
