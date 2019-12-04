package jchess.presenter;

import java.awt.Component;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import jchess.cache.CacheManager;
import jchess.cache.ICacheManager;
import jchess.common.Position;
import jchess.model.BoardModel;
import jchess.model.IBoardModel;
import jchess.view.BoardView;
import jchess.view.BoardViewListener;
import jchess.view.IBoardView;

public class BoardPresenter extends AbstractModule implements BoardViewListener{
	    private final IBoardView m_oView;
	    private final IBoardModel m_oModel;
	    private final ICacheManager m_oCacheManager;

	    public BoardPresenter() {
	    	m_oView = null;
	    	m_oModel = null;
	    	m_oCacheManager = null;
	    }
	    
	    @Override 
		  protected void configure() {
		
			  bind(IBoardView.class).to(BoardView.class);
		
			  bind(IBoardModel.class).to(BoardModel.class);

			  bind(ICacheManager.class).to(CacheManager.class).in(Singleton.class);
		  }

	    @Inject
	    public BoardPresenter(final IBoardView oView, final IBoardModel oModel, final ICacheManager oCacheManager) {
	        m_oView = oView;
	        m_oModel = oModel;
	        m_oCacheManager = oCacheManager;
	        
	        m_oView.addListener(this);
	    }

	    public void init() {
	    	m_oCacheManager.loadBoardFromFile("Mock", "D:\\git\\repositories\\joChess\\src\\main\\resources\\boardlayout\\3PlayerBoard.xml");
	    	m_oModel.setBoard(m_oCacheManager.getBoard("Mock"));
	    }
	    
	    public void showView() {
	    	m_oView.setModelData(m_oModel);
	    	m_oView.paintView();
	    }
	    
	    public Component getViewComponent() {
	    	return m_oView.getViewComponent();
	    }
	    
	    public void onPositionClicked(Position oPosition) {
	    	if( oPosition.getPiece() != null)
		    	oPosition.setSelectState(true);
	    	else
	    		oPosition.setMoveCandidacy(true);
	    	
	    	m_oView.getViewComponent().getParent().repaint();
	    }
}
