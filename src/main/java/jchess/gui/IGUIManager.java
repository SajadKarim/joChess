package jchess.gui;

import java.awt.Component;

import javax.swing.JFrame;

import jchess.common.gui.IPresenter;
import jchess.gui.model.newgamewindow.INewGameModel;
import jchess.gui.view.newgamewindow.INewGameListener;

public interface IGUIManager extends INewGameListener {
	public void showMainWindow();
	public void showNewGameWindow();	
	public void showGameWindow(INewGameModel oData);
	public IGUIHandle getGUIHandle();
	public void onPlayerRequestForUndoMove(Component oSelectedGame);
	public void onPlayerRequestForRedoMove(Component oSelectedGame);
}

