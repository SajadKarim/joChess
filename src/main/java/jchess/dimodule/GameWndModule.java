package jchess.dimodule;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import jchess.cache.CacheManager;
import jchess.cache.ICacheManager;
import jchess.model.gamewindow.GameModel;
import jchess.model.gamewindow.IBoardModel;
import jchess.model.gamewindow.IClockModel;
import jchess.model.gamewindow.IGameModel;
import jchess.model.gamewindow.IPlayerModel;
import jchess.model.newgamewindow.INewGameModel;
import jchess.model.newgamewindow.NewGameModel;
import jchess.view.gamewindow.BoardView;
import jchess.view.gamewindow.ClockView;
import jchess.view.gamewindow.GameView;
import jchess.view.gamewindow.IBoardView;
import jchess.view.gamewindow.IClockView;
import jchess.view.gamewindow.IGameView;
import jchess.view.gamewindow.IMoveHistoryView;
import jchess.view.gamewindow.IPlayerView;
import jchess.view.gamewindow.MoveHistoryView;
import jchess.view.gamewindow.PlayerView;

public class GameWndModule extends AbstractModule {

	@Override 
	protected void configure() {
		bind(IGameView.class).to(GameView.class);		
		bind(IGameModel.class).to(GameModel.class).in(Singleton.class);
		
		bind(IBoardView.class).to(BoardView.class);
		bind(IBoardModel.class).to(GameModel.class);
		
		bind(IClockView.class).to(ClockView.class);
		bind(IClockModel.class).to(GameModel.class);

		bind(IPlayerView.class).to(PlayerView.class);
		bind(IPlayerModel.class).to(GameModel.class);

		bind(IMoveHistoryView.class).to(MoveHistoryView.class);
	}
	
	@Provides @Singleton
	ICacheManager provideCacheManager() {
		return CacheManager.getInstance();
	}

	
	@Provides @Singleton
	GameModel provideGameModel() {
		return new GameModel();
	}
}