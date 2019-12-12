package jchess.view.newgamewindow;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import jchess.Settings;
import jchess.model.IModel;
import jchess.model.newgamewindow.INewGameModel;

public class NewLocalGameView extends JPanel implements ActionListener, INewLocalGameView {

	INewGameModel m_oData;
	INewGame_Callback m_oCallback;
    	
    JDialog parent;
    JSeparator sep;
    Container cont;
    JFrame localPanel;
    JButton okButton;
    GridBagLayout gbl;
    GridBagConstraints gbc;
	JComboBox cmboxBoardTypes;
    IPlayerDetailsEntryView m_oPlayerDetailsEntryView;

    public void actionPerformed(ActionEvent e)
    {
        Object target = e.getSource(); 
        if( target == cmboxBoardTypes) {
        	String stSelectedItem = (String)cmboxBoardTypes.getSelectedItem();
        	m_oPlayerDetailsEntryView.setPlayersCount(m_oData.getPlayersAllowedInBoard(stSelectedItem));
        	m_oPlayerDetailsEntryView.drawView();
        } else if (target == this.okButton) {
        	Component[] controls = ((PlayerDetailsEntryView) m_oPlayerDetailsEntryView).getComponents();
        	for( int i=0;i<controls.length; i++) {
        		if( controls[i] instanceof JTextField)
        			m_oData.updatePlayerDetails(controls[i].getName(), ((JTextField)controls[i]).getText(), "", null);
        		}
        	m_oData.setSelectedBoardName( (String)cmboxBoardTypes.getSelectedItem());
            m_oCallback.launchNewGame(m_oData);
        }
     }

    public NewLocalGameView() {
    	super();
    	
		this.setLayout(null);
		this.setLocation(new Point(0, 0));        
    }
    
    public void setParent(JDialog parent) {
        this.parent = parent;
    }
    
    public void initComponenets() {        
    	this.gbl = new GridBagLayout();
        this.gbc = new GridBagConstraints();
    	this.cmboxBoardTypes = new JComboBox(m_oData.getAllBoardNames());
        this.okButton = new JButton(Settings.lang("ok"));

        
        m_oPlayerDetailsEntryView = new PlayerDetailsEntryView();
        m_oPlayerDetailsEntryView.setViewData(m_oData);

        String stSelectedItem = (String)cmboxBoardTypes.getSelectedItem();
    	m_oPlayerDetailsEntryView.setPlayersCount(m_oData.getPlayersAllowedInBoard(stSelectedItem));    	

    	
    	m_oPlayerDetailsEntryView.init();

    	this.setLayout(gbl);
        this.cmboxBoardTypes.addActionListener(this);
        this.okButton.addActionListener(this);
        
        this.gbc.gridx = 0;
        this.gbc.gridy = 0;
        this.gbc.insets = new Insets(3, 3, 3, 3);
        this.gbc.gridwidth = 0;
        this.gbl.setConstraints(cmboxBoardTypes, gbc);
        this.add(cmboxBoardTypes);
        this.gbc.gridx = 0;
        this.gbc.gridy = 1;
        this.gbl.setConstraints(m_oPlayerDetailsEntryView.getViewComponent(), gbc);
        this.add(m_oPlayerDetailsEntryView.getViewComponent());
        this.gbc.gridx = 1;
        this.gbc.gridy = 9;
        this.gbc.gridwidth = 0;
        this.gbl.setConstraints(okButton, gbc);
        this.add(okButton);
        
    }
    
	@Override
	public void init() {
		initComponenets();		
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
	public Component getViewComponent() {
		return this;
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