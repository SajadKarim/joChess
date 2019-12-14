package jchess.dimodule;

import java.util.Timer;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import jchess.cache.ICacheManager;
import jchess.gamelogic.Game;
import jchess.gamelogic.IGame;
import jchess.model.gamewindow.GameModel;
import jchess.model.gamewindow.IBoardModel;
import jchess.model.gamewindow.IClockModel;
import jchess.model.gamewindow.IGameModel;
import jchess.model.gamewindow.IPlayerModel;
import jchess.model.newgamewindow.INewGameModel;
import jchess.model.newgamewindow.NewGameModel;
import jchess.presenter.gamewindow.GamePresenter;
import jchess.util.AppLogger;
import jchess.util.IAppLogger;
import jchess.util.ITimer;
import jchess.view.gamewindow.BoardView;
import jchess.view.gamewindow.ClockView;
import jchess.view.gamewindow.GameView;
import jchess.view.gamewindow.IBoardView;
import jchess.view.gamewindow.IClockView;
import jchess.view.gamewindow.IGameView;
import jchess.view.gamewindow.IGameViewListener;
import jchess.view.gamewindow.IMoveHistoryView;
import jchess.view.gamewindow.IPlayerView;
import jchess.view.gamewindow.MoveHistoryView;
import jchess.view.gamewindow.PlayerView;
import jchess.view.newgamewindow.INewGameView;
import jchess.view.newgamewindow.NewGameView;

/**
 * This class binds all the dependencies that are required for Game window.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class GameWndModule extends AbstractModule {
	private Injector m_oGlobalInjector = null;
	private String m_stBoardName;
	private String m_stBoardFilePath;
	
	public GameWndModule(Injector oGlobalInjector, String stBoardName, String stBoardFilePath) {
		m_oGlobalInjector = oGlobalInjector;
		m_stBoardName = stBoardName;
		m_stBoardFilePath = stBoardFilePath;
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

		bind(ITimer.class).to(jchess.util.Timer.class);
	}
	
	@Provides @Singleton
	GameModel provideGameModel() {
		ICacheManager oCacheManager = m_oGlobalInjector.getInstance(ICacheManager.class);
		oCacheManager.loadBoardFromFile(m_stBoardName, m_stBoardFilePath);
		
		GameModel oGameModel = new GameModel();
		oGameModel.setBoard( oCacheManager.getBoard(m_stBoardName));
		return oGameModel;
	}

}