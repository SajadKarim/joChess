package jchess.gui.presenter.mainwindow;

import java.awt.Component;

import org.jdesktop.application.View;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import jchess.IMain;
import jchess.gui.IGUIManager;
import jchess.gui.view.mainwindow.IJChessView;
import jchess.util.IAppLogger;

@Singleton
public class MainPresenter implements IMainPresenter{
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
	}
	
	public void init() {
		//m_oJChessView = new JChessView(m_oApplication, m_oLogger, m_oGUIManager);
	}
	
	public View getView() {
		return (View)m_oJChessView;
	}
	
	public void addTab(Component oComponent, String stName) {
		m_oJChessView.addTab(oComponent, stName);
	}
}