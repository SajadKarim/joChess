		package jchess.gui.view.newgamewindow;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.google.inject.Inject;

import jchess.Settings;
import jchess.common.gui.IModel;
import jchess.gui.model.newgamewindow.INewGameModel;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

/**
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class NewLocalGameView extends JPanel implements ActionListener, INewLocalGameView {

	INewGameModel m_oData;
	INewGameListener m_oCallback;
    	
    JDialog parent;
    JSeparator sep;
    Container cont;
    JFrame localPanel;
    JButton okButton;
    GridBagLayout gbl;
    GridBagConstraints gbc;
	JComboBox cmboxBoardTypes;
    IPlayerDetailsEntryView m_oPlayerDetailsEntryView;

    private IAppLogger m_oAppLogger;
    
	private ArrayList<INewGameListener> m_lstListener;

    public void actionPerformed(ActionEvent e)
    {
        Object target = e.getSource(); 
        if( target == cmboxBoardTypes) {
        	String stSelectedItem = (String)cmboxBoardTypes.getSelectedItem();
        	m_oPlayerDetailsEntryView.setPlayersCount(m_oData.getPlayersAllowedInBoard(stSelectedItem));
        	m_oPlayerDetailsEntryView.drawComponents();
        } else if (target == this.okButton) {
        	Component[] controls = ((PlayerDetailsEntryView) m_oPlayerDetailsEntryView).getComponents();
        	for( int i=0;i<controls.length; i++) {
        		if( controls[i] instanceof JTextField)
        			m_oData.updatePlayerDetails(controls[i].getName(), ((JTextField)controls[i]).getText(), "", null);
        		}
        	m_oData.setSelectedBoardName( (String)cmboxBoardTypes.getSelectedItem());
            notifyListenersOnNewGameLaunchRequest(m_oData);
        }
     }

    @Inject
    public NewLocalGameView(final IAppLogger oAppLogger) {
    	super();
    	
		this.setLayout(null);
		this.setLocation(new Point(0, 0));
		
		m_oAppLogger = oAppLogger;
		
    	m_lstListener = new ArrayList<INewGameListener>();
    }
    
    public void setParent(JDialog parent) {
        this.parent = parent;
    }
    
    public void initComponenets() {    
    	m_oAppLogger.writeLog(LogLevel.DETAILED, "Initializing components.", "initComponents", "NewLocalGameView");

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
	public void drawComponents() {
		this.repaint();
	}

	@Override
	public void refresh() {
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

	
	public void addListener(final INewGameListener oListener) {
        m_lstListener.add(oListener);
    }
	
	private void notifyListenersOnNewGameLaunchRequest(INewGameModel oData) {
        for (final INewGameListener oListener : m_lstListener) {
            oListener.onNewGameLaunchRequest(oData);
        }
    }
}