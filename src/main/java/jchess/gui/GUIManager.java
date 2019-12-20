package jchess.gui;

import java.awt.Point;

import javax.swing.JFrame;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import jchess.IMain;
import jchess.cache.ICacheManager;
import jchess.common.gui.IPresenter;
import jchess.dimodule.IDIManager;
import jchess.gui.model.newgamewindow.INewGameModel;
import jchess.gui.presenter.gamewindow.GamePresenter;
import jchess.gui.presenter.gamewindow.IGamePresenter;
import jchess.gui.presenter.mainwindow.IMainPresenter;
import jchess.gui.presenter.newgamewindow.INewGamePresenter;
import jchess.gui.presenter.newgamewindow.NewGamePresenter;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

/**
 * Implements IGUIManager.
 * Its main responsibility is to manage GUI related operationss.
 * 
 * @author	Sajad Karim
 * @since	14  Dec 2019
 */

@Singleton
public class GUIManager implements IGUIManager, IGUIHandle {
	private IAppLogger m_oLogger;
	private IMain m_oApplication;
	private IDIManager m_oDIManager;
	private ICacheManager m_oCacheManager;

	private IMainPresenter m_oMainPresenter;
	
	@Inject
    public GUIManager(IMain oApplication, IDIManager oDIManager, ICacheManager oCacheManager, IAppLogger oLogger, IMainPresenter oMainPresenter) {
    	m_oLogger = oLogger;
    	m_oApplication = oApplication;
    	m_oDIManager = oDIManager;
    	m_oCacheManager = oCacheManager;
		m_oMainPresenter = oMainPresenter;
	}
	
	public void showMainWindow() {
		m_oMainPresenter.init();
    	m_oApplication.showView(m_oMainPresenter.getView());
	}
	
	public void showNewGameWindow() {
    	m_oLogger.writeLog(LogLevel.DETAILED, "Initializing and launching NewGameWindow", "actionPerformed", "JChessView");

    	Injector injector = m_oDIManager.createChildInjectorForNewGameModule();
    	        	
    	INewGamePresenter oPresenter = injector.getInstance(NewGamePresenter.class);
    	oPresenter.addListener(this);
    	oPresenter.init();
    	
    	m_oApplication.showDialog(oPresenter.getViewJDialog()); 

	}
	
	public void showGameWindow(String stBoardName, String stBoardFilePath) {
    	m_oLogger.writeLog(LogLevel.DETAILED, "Initializing and launching New game.", "launchNewGame", "JChessView");

		Injector injector = m_oDIManager.createChildInjectorForGameModule(stBoardName, stBoardFilePath);
		
		IGamePresenter oPresenter = injector.getInstance(GamePresenter.class);

		oPresenter.init();		
		oPresenter.getViewComponent().setLocation(new Point(0, 0));
		
		m_oMainPresenter.addTab(oPresenter.getViewComponent(), stBoardName);
		
		oPresenter.showView();
	}

	@Override
	public void onNewGameLaunchRequest(INewGameModel oData) {
		showGameWindow( oData.getSelectedBoardName(), oData.getSelectedBoardFileName());
	}
	
	public void showDialog(IPresenter oPresenter) {
		oPresenter.init();
		m_oApplication.showDialog(oPresenter.tryGetViewJDialog());
	}
	
	public JFrame getGUIMainFrame() {
		return m_oApplication.getAppMainFrame();
	}
	
	public IGUIHandle getGUIHandle() {
		return (IGUIHandle)((GUIManager)this);
	}
}