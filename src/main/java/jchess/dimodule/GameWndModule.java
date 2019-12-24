package jchess.dimodule;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import jchess.cache.ICacheManager;
import jchess.gamelogic.Game;
import jchess.gamelogic.GameState;
import jchess.gamelogic.IGame;
import jchess.gamelogic.IGameState;
import jchess.gui.GUIManager;
import jchess.gui.IGUIHandle;
import jchess.gui.model.gamewindow.GameModel;
import jchess.gui.model.gamewindow.IBoardModel;
import jchess.gui.model.gamewindow.IClockModel;
import jchess.gui.model.gamewindow.IGameModel;
import jchess.gui.model.gamewindow.IPlayerModel;
import jchess.util.ITimer;
import jchess.gui.view.gamewindow.BoardView;
import jchess.gui.view.gamewindow.ClockView;
import jchess.gui.view.gamewindow.GameView;
import jchess.gui.view.gamewindow.IBoardView;
import jchess.gui.view.gamewindow.IClockView;
import jchess.gui.view.gamewindow.IGameView;
import jchess.gui.view.gamewindow.IMoveHistoryView;
import jchess.gui.view.gamewindow.IPlayerView;
import jchess.gui.view.gamewindow.MoveHistoryView;
import jchess.gui.view.gamewindow.PlayerView;

/**
 * This class binds all the dependencies that are required for Game window.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class GameWndModule extends AbstractModule {
	private Injector m_oGlobalInjector = null;
	private String m_stBoardName;
	private String m_stGameId;
	
	public GameWndModule(Injector oGlobalInjector, String stGameId, String stBoardName) {
		m_oGlobalInjector = oGlobalInjector;
		m_stBoardName = stBoardName;
		m_stGameId = stGameId;
	}
	
	@Override 
	protected void configure() {
		bind(IGameView.class).to(GameView.class);
		bind(IGameModel.class).to(GameModel.class);

		bind(IBoardView.class).to(BoardView.class);
		bind(IBoardModel.class).to(GameModel.class);

		bind(IClockView.class).to(ClockView.class);
		bind(IClockModel.class).to(GameModel.class);

		bind(IPlayerView.class).to(PlayerView.class);
		bind(IPlayerModel.class).to(GameModel.class);

		bind(IMoveHistoryView.class).to(MoveHistoryView.class);

		bind(IGame.class).to(Game.class);
		bind(IGameState.class).to(GameState.class);

		bind(ITimer.class).to(jchess.util.Timer.class);

		bind(IGUIHandle.class).to(GUIManager.class).in(Singleton.class);
	}
	
	@Provides @Singleton
	GameModel provideGameModel() {
		ICacheManager oCacheManager = m_oGlobalInjector.getInstance(ICacheManager.class);
		oCacheManager.loadBoardFromFile(m_stGameId, m_stBoardName);
		
		GameModel oGameModel = new GameModel();
		oGameModel.setBoard( oCacheManager.getBoard(m_stGameId));
		return oGameModel;
	}

}