package jchess.gui.presenter.mainwindow;

import java.awt.Component;

import javax.swing.JDialog;

import org.jdesktop.application.View;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jchess.IMain;
import jchess.common.gui.DialogResult;
import jchess.gui.IGUIManager;
import jchess.gui.view.mainwindow.IJChessView;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

@Singleton
public final class MainPresenter implements IMainPresenter {
	private IJChessView m_oJChessView;
	
	private IMain m_oApplication;
	private IGUIManager m_oGUIManager;
	private IAppLogger m_oLogger;
	
	@Inject
	public MainPresenter(IMain oApplication, IGUIManager oGUIManager, IAppLogger oLogger, IJChessView oJChessView) {
		m_oApplication = oApplication;
		m_oGUIManager = oGUIManager;
		m_oLogger = oLogger;
		m_oJChessView = oJChessView;

		m_oLogger.writeLog(LogLevel.DETAILED, "Instantiating MainPresenter.", "MainPresenter", "MainPresenter");
	}
	
	public void init() {
	}
		
	public void addTab(Component oComponent, String stName) {
     	m_oLogger.writeLog(LogLevel.INFO, "Adding a new game.", "addTab", "MainPresenter");

		m_oJChessView.addTab(oComponent, stName);
	}
	
	public void closetab()
	{
		m_oJChessView.removeCurrentTab();
	}

	@Override
	public void showView() {
		// TODO Auto-generated method stub		
	}

	@Override
	public View tryGetJDesktopView() {
		return (View)m_oJChessView;
	}

	@Override
	public JDialog tryGetViewJDialog() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component tryGetViewComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onViewClosed(DialogResult oFormAction, Object oData) {
		// TODO Auto-generated method stub
		
	}
}