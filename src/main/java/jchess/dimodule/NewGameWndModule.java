package jchess.dimodule;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import jchess.cache.CacheManager;
import jchess.cache.ICacheManager;
import jchess.model.newgamewindow.INewGameModel;
import jchess.model.newgamewindow.NewGameModel;
import jchess.view.newgamewindow.*;

public class NewGameWndModule extends AbstractModule {
	@Override 
	protected void configure() {
		bind(INewGameView.class).to(NewGameView.class);
		bind(INewGameModel.class).to(NewGameModel.class);
	}
	
	@Provides @Singleton
	ICacheManager provideCacheManager() {
		return CacheManager.getInstance();
	}
}
