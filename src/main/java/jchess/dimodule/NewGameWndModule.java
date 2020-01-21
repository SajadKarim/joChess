package jchess.dimodule;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;

import jchess.gui.model.newgamewindow.INewGameModel;
import jchess.gui.model.newgamewindow.NewGameModel;
import jchess.gui.view.newgamewindow.INewGameView;
import jchess.gui.view.newgamewindow.INewLocalGameView;
import jchess.gui.view.newgamewindow.NewGameView;
import jchess.gui.view.newgamewindow.NewLocalGameView;

/**
 * This class binds all the dependencies that are required for New-Game window.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public final class NewGameWndModule extends AbstractModule {
	private Injector m_oGlobalInjector = null;
	
	public NewGameWndModule(Injector oGlobalInjector) {
		m_oGlobalInjector = oGlobalInjector;
	}
	
	@Override 
	protected void configure() {
		bind(INewGameView.class).to(NewGameView.class);
		bind(INewGameModel.class).to(NewGameModel.class);
		
		bind(INewLocalGameView.class).to(NewLocalGameView.class);
	}
}
