package jchess.presenter.newgamewindow;

import java.awt.Component;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import jchess.cache.CacheManager;
import jchess.cache.ICacheManager;
import jchess.model.newgamewindow.INewGameModel;
import jchess.view.newgamewindow.*;

public class NewGamePresenter extends AbstractModule implements INewGame_Callback{
    private final INewGameView m_oView;
    private final INewGameModel m_oModel;
    private final ICacheManager m_oCacheManager;

    private INewGame_Callback m_oCallback;
    
    public NewGamePresenter() {
    	m_oView = null;
    	m_oModel = null;
    	m_oCacheManager = null;
    }
    
    @Inject
    public NewGamePresenter(final INewGameView oView, final INewGameModel oModel, final ICacheManager oCacheManager) {
        m_oView = oView;
        m_oModel = oModel;
        m_oCacheManager = oCacheManager;        
    }

    public void init() {
    	m_oCacheManager.init();
    	m_oModel.setPlayerBoardMapping(m_oCacheManager.getPossiblePlayerInEachBoard());

        m_oView.setCallback(this);
    	m_oView.setViewData(m_oModel);
    	m_oView.init();
    }
    
    public void setCallback(INewGame_Callback oCallback) {
    	m_oCallback = oCallback;
    }
    
    public INewGameView getView() {
    	return m_oView;
    }
    
    public Component getViewComponent() {
    	return m_oView.getViewComponent();
    }

	public void onRequestForNewGame(INewGameModel oModel) {
	}

	@Override
	public void launchNewGame(INewGameModel oData) {
		m_oView.getViewComponent().setVisible(false);
		m_oCallback.launchNewGame(oData);
	}    
}
