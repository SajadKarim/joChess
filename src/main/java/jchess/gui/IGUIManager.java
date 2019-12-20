package jchess.gui;

import javax.swing.JFrame;

import jchess.common.gui.IPresenter;
import jchess.gui.view.newgamewindow.INewGameListener;

public interface IGUIManager extends INewGameListener {
	public void showMainWindow();
	public void showNewGameWindow();	
	public void showGameWindow(String stBoardName, String stBoardFilePath);
	public IGUIHandle getGUIHandle();
}

