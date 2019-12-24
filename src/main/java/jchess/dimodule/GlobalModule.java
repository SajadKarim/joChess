package jchess.dimodule;

import org.jdesktop.application.Application;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import jchess.IMain;
import jchess.Main;
import jchess.cache.CacheManager;
import jchess.cache.ICacheManager;
import jchess.gui.GUIManager;
import jchess.gui.IGUIManager;
import jchess.gui.presenter.mainwindow.IMainPresenter;
import jchess.gui.presenter.mainwindow.MainPresenter;
import jchess.gui.view.mainwindow.IJChessView;
import jchess.gui.view.mainwindow.JChessView;
import jchess.util.AppLogger;
import jchess.util.IAppLogger;

/**
 * This class binds all the dependencies that are shared across the project,
 * and requires single instantiation (Singleton).
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class GlobalModule extends AbstractModule {	
	@Override 
	protected void configure() {
		bind(IDIManager.class).to(DIManager.class).in(Singleton.class);
		bind(ICacheManager.class).to(CacheManager.class).in(Singleton.class);
		bind(IAppLogger.class).to(AppLogger.class).in(Singleton.class);
		bind(IGUIManager.class).to(GUIManager.class).in(Singleton.class);		
		bind(IMainPresenter.class).to(MainPresenter.class).in(Singleton.class);
		bind(IJChessView.class).to(JChessView.class).in(Singleton.class);		
	}
	
	@Provides @Singleton
	IMain provideApplication() {
		return Application.getInstance(Main.class);
	}	
}