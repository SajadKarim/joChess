package jchess.dimodule;

import com.google.inject.AbstractModule;

import jchess.model.newgamewindow.INewGameModel;
import jchess.model.newgamewindow.NewGameModel;
import jchess.view.newgamewindow.*;

/**
 * This class binds all the dependencies that are required for New-Game window.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class NewGameWndModule extends AbstractModule {
	@Override 
	protected void configure() {
		bind(INewGameView.class).to(NewGameView.class);
		bind(INewGameModel.class).to(NewGameModel.class);
	}
}
