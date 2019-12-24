package jchess.ruleengine.gui;

import java.awt.Component;

import javax.swing.JDialog;

import jchess.common.IPieceAgent;
import jchess.common.gui.DialogResult;
import jchess.common.gui.IModel;
import jchess.common.gui.IPresenter;
import jchess.common.gui.IView;
import jchess.gui.view.gamewindow.IGameViewListener;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

public class PawnPromotionPresenter implements IPresenter {
    private final IPawnPromotionDialogView m_oView;
    private final IPawnPromotionModel m_oData;
    private final IAppLogger m_oAppLogger;

	public PawnPromotionPresenter(IPawnPromotionDialogView oView, IPawnPromotionModel oData, IAppLogger oAppLogger){
		m_oView = oView;
		m_oData = oData;
		m_oAppLogger = oAppLogger;
		
		init();
	}
	
	public void init() {
    	//m_oAppLogger.writeLog(LogLevel.DETAILED, "Initializing Game presenter.", "init", "GamePresenter");

    	m_oView.addListener(this);
    	m_oView.setViewData(m_oData);
        m_oView.init();
    }

	public void showView() {
    	m_oView.drawComponents();
	}

	public void onViewClosed(DialogResult oFormAction,Object oData) {
		m_oData.updateSelectedPiece((String)oData);
	}
	
	@Override
	public void getView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component getViewComponent() {
		return m_oView.getViewComponent();
	}

	@Override
	public JDialog tryGetViewJDialog() {
    	return m_oView.getJDialog();
	}
}