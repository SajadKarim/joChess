package jchess.view.newgamewindow;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JDialog;
import javax.swing.JFrame;

import jchess.Settings;
import jchess.model.IModel;
import jchess.model.newgamewindow.INewGameModel;

/**
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class NewGameView extends JDialog implements INewGameView, INewGame_Callback {
    private INewGameModel m_oData;
    private INewLocalGameView m_NewLocalGameView;
    private javax.swing.JTabbedPane m_oJTabbedPanel;
	
    private INewGame_Callback m_oCallback;

    public NewGameView() {
    }
    
    public void init() {
        initComponents();

        this.setSize(400, 700);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        m_NewLocalGameView = new NewLocalGameView();
        m_NewLocalGameView.setParent(this);
        m_NewLocalGameView.setViewData(m_oData);
        m_NewLocalGameView.setCallback(this);
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
    
    public void launchNewGame(INewGameModel oModel) {
    	m_oCallback.launchNewGame(oModel);
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

	@Override
	public void setCallback(INewGame_Callback oCallback) {
		m_oCallback = oCallback;
	}
}
