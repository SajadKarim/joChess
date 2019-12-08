package jchess.presenter;

import java.awt.Component;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import jchess.cache.CacheManager;
import jchess.cache.ICacheManager;
import jchess.common.IPlayer;
import jchess.common.IPositionAgent;
import jchess.gamelogic.Game;
import jchess.model.GameModel;
import jchess.model.IGameModel;
import jchess.view.GameView;
import jchess.view.GameViewListener;
import jchess.view.IGameView;

public class GamePresenter extends AbstractModule implements GameViewListener, IGamePresenterCallback{
	    private final IGameView m_oView;
	    private final IGameModel m_oModel;
	    private final ICacheManager m_oCacheManager;

	    private Game m_oGame;
	    
	    public GamePresenter() {
	    	m_oView = null;
	    	m_oModel = null;
	    	m_oCacheManager = null;
	    }
	    
	    @Override 
		  protected void configure() {
		
			  bind(IGameView.class).to(GameView.class);
		
			  bind(IGameModel.class).to(GameModel.class);

			  bind(ICacheManager.class).to(CacheManager.class).in(Singleton.class);
		  }

	    @Inject
	    public GamePresenter(final IGameView oView, final IGameModel oModel, final ICacheManager oCacheManager) {
	        m_oView = oView;
	        m_oModel = oModel;
	        m_oCacheManager = oCacheManager;
	        
	        m_oView.addListener(this);
	    }

	    public void init() {
	    	m_oCacheManager.loadBoardFromFile("Mock", "D:\\git\\repositories\\joChess\\src\\main\\resources\\boardlayout\\3PlayerBoard.xml");
	    	m_oModel.setBoard(m_oCacheManager.getBoard("Mock"));

	    	//m_oModel.setPlayer();	    	
	    	m_oGame = new Game(this, m_oModel.getBoard());
	    	
	    	m_oView.setModelData(m_oModel);
	    	m_oView.init();
	    }
	    
	    public void showView() {
	    	m_oView.paintView();
	    }
	    
	    public Component getViewComponent() {
	    	return m_oView.getViewComponent();
	    }
	    
	    //region: Notifications from GameView
	    public void onPositionClicked(IPositionAgent oPosition) {
	    	m_oGame.onBoardActivity(oPosition);	    	
	    	m_oView.getViewComponent().getParent().repaint();
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
	    	m_oView.getViewComponent().getParent().repaint();
		}
		
		public void updateCurrentPlayer(IPlayer oPlayer) {
			m_oModel.setPlayer(oPlayer);
			m_oView.repaintPlayerView();
		}
		//endregion
}
