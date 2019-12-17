package jchess.gui.view.newgamewindow;

import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.google.inject.Inject;

import jchess.Settings;
import jchess.common.IPositionAgent;
import jchess.gui.model.IModel;
import jchess.gui.model.newgamewindow.INewGameModel;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;
import jchess.gui.view.gamewindow.IGameViewListener;

/**
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class NewGameView extends JDialog implements INewGameView, INewGameListener {
    private INewGameModel m_oData;
    private INewLocalGameView m_NewLocalGameView;
    private javax.swing.JTabbedPane m_oJTabbedPanel;
	
	private ArrayList<INewGameListener> m_lstListener;

    private IAppLogger m_oAppLogger;
    
    @Inject
    public NewGameView(final IAppLogger oAppLogger, final INewLocalGameView oNewLocalGameView) {
    	m_oAppLogger = oAppLogger;
    	m_NewLocalGameView = oNewLocalGameView;
    	m_lstListener = new ArrayList<INewGameListener>();
    }
    
    public void init() {
    	m_oAppLogger.writeLog(LogLevel.DETAILED, "Initializing new game view.", "init", "NewGameView");

        initComponents();

        this.setSize(400, 700);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        m_NewLocalGameView.setParent(this);
        m_NewLocalGameView.setViewData(m_oData);
        m_NewLocalGameView.addListener(this);
        m_NewLocalGameView.init();
        this.m_oJTabbedPanel.addTab(Settings.lang("local_game"), m_NewLocalGameView.getViewComponent());
    }

    private void initComponents() {

    	m_oJTabbedPanel = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setName("Form");
        this.setTitle("Launch New Game");
        m_oJTabbedPanel.setName("pnlGameOptions");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(m_oJTabbedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(m_oJTabbedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }
    
	public Component getViewComponent() {
		return this;
	}

	public void setModelData(INewGameModel oData) {
		m_oData = oData;
	}

	@Override
    public void paint(Graphics g)
    {
		m_NewLocalGameView.drawView();
    }
    
	@Override
	public void drawView() {
		this.repaint();
	}

	@Override
	public void refreshView() {
		this.repaint();
	}

	@Override
	public void setViewData(IModel oData) {
		m_oData = (INewGameModel)oData;
	}

	public JDialog getJDialog() {
		return this;
	}
	
	public void addListener(INewGameListener oListener) {
        m_lstListener.add(oListener);
    }
	
	private void notifyListenersOnNewGameLaunchRequest(INewGameModel oData) {
        for (final INewGameListener oListener : m_lstListener) {
            oListener.onNewGameLaunchRequest(oData);
        }
    }

	@Override
	public void onNewGameLaunchRequest(INewGameModel oData) {
		notifyListenersOnNewGameLaunchRequest(oData);
	}
}
