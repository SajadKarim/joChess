package jchess.ruleengine.gui;

import java.awt.Component;

import javax.swing.JDialog;

import org.jdesktop.application.View;

import jchess.common.gui.DialogResult;
import jchess.common.gui.IPresenter;
import jchess.util.IAppLogger;

/**
 * PawnPromotionPresenter 
 * 
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

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
	public View tryGetJDesktopView() {
		return null;
	}

	@Override
	public Component tryGetViewComponent() {
		return m_oView.getViewComponent();
	}

	@Override
	public JDialog tryGetViewJDialog() {
    	return m_oView.getJDialog();
	}
}
