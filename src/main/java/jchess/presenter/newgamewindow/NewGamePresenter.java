package jchess.presenter.newgamewindow;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JDialog;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import jchess.cache.ICacheManager;
import jchess.model.newgamewindow.INewGameModel;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;
import jchess.view.newgamewindow.*;

public class NewGamePresenter extends AbstractModule implements INewGamePresenter {
    private final INewGameView m_oView;
    private final INewGameModel m_oModel;
    private final ICacheManager m_oCacheManager;
    private final IAppLogger m_oAppLogger;

	private ArrayList<INewGameListener> m_lstListener;

    @Inject
    public NewGamePresenter(final INewGameView oView, final INewGameModel oModel, final ICacheManager oCacheManager, final IAppLogger oAppLogger) {
        m_oView = oView;
        m_oModel = oModel;
        m_oAppLogger = oAppLogger;
        m_oCacheManager = oCacheManager;  
    	m_lstListener = new ArrayList<INewGameListener>();
    }

    public void init() {
    	m_oAppLogger.writeLog(LogLevel.DETAILED, "Initializing NewGameWindow presenter.", "init", "NewGamePresenter");

    	m_oCacheManager.init();
    	m_oModel.setPlayerBoardMapping(m_oCacheManager.getPossiblePlayerInEachBoard());

        m_oView.addListener(this);
    	m_oView.setViewData(m_oModel);
    	m_oView.init();
    }
    
    public INewGameView getView() {
    	return m_oView;
    }

    public JDialog getViewJDialog() {
    	return m_oView.getJDialog();
    }

    public Component getViewComponent() {
    	return m_oView.getViewComponent();
    }

	public void onRequestForNewGame(INewGameModel oModel) {
	}

	public void onNewGameLaunchRequest(INewGameModel oData) {
    	m_oAppLogger.writeLog(LogLevel.DETAILED, "Launching new game.", "launchNewGame", "NewGamePresenter");

		m_oView.getViewComponent().setVisible(false);
		notifyListenersOnNewGameLaunchRequest(oData);
	} 
	
	public void addListener(final INewGameListener oListener) {
        m_lstListener.add(oListener);
    }
	
	private void notifyListenersOnNewGameLaunchRequest(INewGameModel oData) {
        for (final INewGameListener oListener : m_lstListener) {
            oListener.onNewGameLaunchRequest(oData);
        }
    }

}
