package jchess.gui;

import java.awt.Component;

import jchess.gui.model.newgamewindow.INewGameModel;
import jchess.gui.view.newgamewindow.INewGameListener;

/**
 * Defines IGUIManager.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IGUIManager extends INewGameListener {
	public void showMainWindow();
	public void showNewGameWindow();	
	public void showGameWindow(INewGameModel oData);
	public IGUIHandle getGUIHandle();
	public void onPlayerRequestForUndoMove(Component oSelectedGame);
	public void onPlayerRequestForRedoMove(Component oSelectedGame);
}

