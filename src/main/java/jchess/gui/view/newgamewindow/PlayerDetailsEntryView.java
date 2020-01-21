package jchess.gui.view.newgamewindow;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import jchess.common.gui.IModel;
import jchess.gui.model.newgamewindow.INewGameModel;

/**
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public final class PlayerDetailsEntryView extends JPanel implements IPlayerDetailsEntryView {

	private INewGameModel m_oData;
	private INewGameListener m_oCallback;
	
    Container cont;
    JSeparator sep;
    GridBagLayout gbl;
    GridBagConstraints gbc;    
    
	private int m_nPlayerCount = 0;

	public PlayerDetailsEntryView() {
    	super();
    	
    	m_nPlayerCount = 0;
    }
    
    public void initComponenets() {
        this.gbl = new GridBagLayout();
        this.gbc = new GridBagConstraints();
        this.sep = new JSeparator();

        this.setLayout(gbl);
                
        this.gbc.insets = new Insets(3, 3, 3, 3);
        for( int i=1; i<=m_nPlayerCount; i++) {
        
            JLabel lblName = new JLabel("Player" + i + " Name:");

        	JTextField txtName = new JTextField("Player"+i, 10);
        	txtName.setName("P"+i);
            txtName.setSize(new Dimension(200, 50));
            
	        this.gbc.gridx = 0;
	        this.gbc.gridy = i;
	        this.gbl.setConstraints(lblName, gbc);
	        this.add(lblName);
	        this.gbc.gridx = 1;
	        this.gbc.gridy = i;
	        this.gbl.setConstraints(txtName, gbc);
	        this.add(txtName);
        }        
    }

    @Override
	public void init() {
		initComponenets();		
	}

	@Override
	public void drawComponents() {
		this.removeAll();
		initComponenets();
		this.revalidate();
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

	@Override
	public void setPlayersCount(int nPlayerCount) {
		m_nPlayerCount = nPlayerCount;
	}
}