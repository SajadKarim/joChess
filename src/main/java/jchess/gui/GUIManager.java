package jchess.gui;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import jchess.IMain;
import jchess.common.ICacheManager;
import jchess.common.gui.IPresenter;
import jchess.dimodule.IDIManager;
import jchess.gui.model.newgamewindow.INewGameModel;
import jchess.gui.presenter.gamewindow.GamePresenter;
import jchess.gui.presenter.gamewindow.IGamePresenter;
import jchess.gui.presenter.mainwindow.IMainPresenter;
import jchess.gui.presenter.newgamewindow.INewGamePresenter;
import jchess.gui.presenter.newgamewindow.NewGamePresenter;
import jchess.gui.view.gamewindow.IGameView;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

/**
 * Implements IGUIManager.
 * Its main responsibility is to manage GUI related operations.
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
	
	private int m_nGameCounter;	//TODO: need to replace it with better logic.
	
	/**
	 * Constructor for GUIManager
	 * @param oApplication Handle to main instance.
	 * @param oDIManager Handle to Dependency Injection instance.
	 * @param oCacheManager Handle to Cache Manager instance.
	 * @param oLogger Handle to AppLogger instance.
	 * @param oMainPresenter Handle to MainWindow instance.
	 */
	@Inject
    public GUIManager(IMain oApplication, IDIManager oDIManager, ICacheManager oCacheManager, IAppLogger oLogger, IMainPresenter oMainPresenter) {
    	m_oLogger = oLogger;
    	m_oApplication = oApplication;
    	m_oDIManager = oDIManager;
    	m_oCacheManager = oCacheManager;
		m_oMainPresenter = oMainPresenter;
		
		m_nGameCounter = 1;
		
     	m_oLogger.writeLog(LogLevel.INFO, "Instantiating GUIManager.", "GUIManager", "GUIManager");
	}
	
	/**
	 * To display Main window.
	 */
	public void showMainWindow() {
     	m_oLogger.writeLog(LogLevel.DETAILED, "Launching main window.", "showMainWindow", "GUIManager");

     	try {
     		m_oMainPresenter.init();
     		m_oApplication.showView(m_oMainPresenter.tryGetJDesktopView());
     	} catch (Exception ex) {
	    	m_oLogger.writeLog(LogLevel.ERROR, ex.toString(), "showMainWindow", "GUIManager");
		}
	}
	
	/**
	 * To display NewGame window to let use enter players and other details.
	 */
	public void showNewGameWindow() {
    	m_oLogger.writeLog(LogLevel.DETAILED, "Initializing and launching NewGameWindow.", "showNewGameWindow", "GUIManager");

    	Injector injector = m_oDIManager.createChildInjectorForNewGameModule();
    	        	
    	INewGamePresenter oPresenter = injector.getInstance(NewGamePresenter.class);
    	oPresenter.addListener(this);
    	oPresenter.init();
    	
    	m_oApplication.showDialog(oPresenter.tryGetViewJDialog()); 
	}
	
	/**
	 * To display Game window.
	 */
	public void showGameWindow(INewGameModel oData) {
    	m_oLogger.writeLog(LogLevel.DETAILED, "Initializing and launching New game.", "showGameWindow", "GUIManager");

    	String stGameId = "Game#" + m_nGameCounter;
		Injector injector = m_oDIManager.createChildInjectorForGameModule(stGameId, oData.getSelectedBoardName(),  oData.getSelectedBoardFileName());
		
		IGamePresenter oPresenter = injector.getInstance(GamePresenter.class);

		oPresenter.updatePlayerNames(oData.getPlayers());
		
		oPresenter.init();
		oPresenter.tryGetViewComponent().setLocation(new Point(0, 0));
		
		m_oMainPresenter.addTab(oPresenter.tryGetViewComponent(), stGameId);
		
		oPresenter.showView();
		
		m_nGameCounter++;
	}
	
	public void closeGameWindow() {
		m_oMainPresenter.closetab();
	}
	
	public void popUpConfirmDialog(String stConfirmDialogMessage, String stConfirmDialogTitle) {
		JOptionPane.showConfirmDialog(null, stConfirmDialogMessage, stConfirmDialogTitle, JOptionPane.DEFAULT_OPTION);
	}
	
	/**
	 * Following method shows a dialog box and informs players about the commencement of the game.
	 * TODO: This is not an appropriate way and not the right place to do such stuff. I shall move it later to 
	 * some appropriate class.
	 */
	public void showGameStartPopup() {
		JOptionPane oOpPane = new JOptionPane();
		oOpPane.setMessageType(JOptionPane.PLAIN_MESSAGE);
		JDialog oJDialog = oOpPane.createDialog(null, "N-Players Chess Game");
		
		// Set a 2 second timer
		new Thread(new Runnable() {
		    @Override
		    public void run() {
		        try {
		        	// TODO: Replace the following with splash screen.
		        	// This is not right, but doing to by giving explicit pauses to give player some room to start.
		        	oOpPane.setMessage(String.format("Loading...", 3));
		        	Thread.sleep(2000);
		        	oOpPane.setMessage(String.format("Game starts in %d secs...", 3));
		            Thread.sleep(1000);
		            oOpPane.setMessage(String.format("Game starts in %d secs...", 2));
		            Thread.sleep(1000);
		            oOpPane.setMessage(String.format("Game starts in %d secs...", 1));
		            Thread.sleep(1000);
		        } catch (Exception e) {
		        }
		        oJDialog.dispose();
		    }

		}).start();

		oJDialog.setVisible(true);
	}
	
	
	@Override
	public void onNewGameLaunchRequest(INewGameModel oData) {
    	m_oLogger.writeLog(LogLevel.DETAILED, "Request to launch a fresh game.", "onNewGameLaunchRequest", "GUIManager");

    	showGameWindow(oData);
	}
	
	/**
	 * Implements IGUIHandle, that lets other modules to launch custom windows/dialogs.
	 */
	public void showDialog(IPresenter oPresenter) {
    	m_oLogger.writeLog(LogLevel.DETAILED, "Launching dialog window.", "showDialog", "GUIManager");

    	oPresenter.init();
		m_oApplication.showDialog(oPresenter.tryGetViewJDialog());
	}
	
	public JFrame getGUIMainFrame() {
		return m_oApplication.getAppMainFrame();
	}
	
	public IGUIHandle getGUIHandle() {
		return (IGUIHandle)((GUIManager)this);
	}
	
	public void onPlayerRequestForUndoBoardActivity(Component oSelectedGame) {
    	m_oLogger.writeLog(LogLevel.DETAILED, "Request to Undo the move.", "onPlayerRequestForUndoBoardActivity", "GUIManager");

		if (oSelectedGame == null) {
	    	m_oLogger.writeLog(LogLevel.INFO, "No game is selected.", "onPlayerRequestForUndoBoardActivity", "GUIManager");
			return;
		}
		
		IGameView oGameView = (IGameView)oSelectedGame;
		oGameView.tryUndoBoardActivity();
	}

	public void onPlayerRequestForRedoBoardActivity(Component oSelectedGame) {
    	m_oLogger.writeLog(LogLevel.DETAILED, "Request to Redo the move.", "onPlayerRequestForRedoBoardActivity", "GUIManager");

		if (oSelectedGame == null) {
	    	m_oLogger.writeLog(LogLevel.INFO, "No game is selected.", "onPlayerRequestForRedoBoardActivity", "GUIManager");
			return;
		}
		
		IGameView oGameView = (IGameView)oSelectedGame;
		oGameView.tryRedoBoardActivity();		
	}
}