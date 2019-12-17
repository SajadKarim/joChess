package jchess.gui;

import java.awt.Point;

import com.google.inject.Injector;

import jchess.IMain;
import jchess.dimodule.GameWndModule;
import jchess.dimodule.NewGameWndModule;
import jchess.gui.presenter.gamewindow.GamePresenter;
import jchess.gui.presenter.gamewindow.IGamePresenter;
import jchess.gui.presenter.newgamewindow.INewGamePresenter;
import jchess.gui.presenter.newgamewindow.NewGamePresenter;
import jchess.gui.view.newgamewindow.INewGameListener;
import jchess.util.LogLevel;

public interface IGUIManager extends INewGameListener {
	public void showMainWindow();
	public void showNewGameWindow();	
	public void showGameWindow(String stBoardName, String stBoardFilePath);}
