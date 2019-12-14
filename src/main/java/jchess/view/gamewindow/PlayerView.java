package jchess.view.gamewindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.google.inject.Inject;

import jchess.model.IModel;
import jchess.model.gamewindow.IPlayerModel;

/**
 * This class is responsible to draw Username and other related details on GUI.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class PlayerView extends JPanel implements IPlayerView {
	private IPlayerModel m_oData;
	private Dimension m_oDimension; 

	@Inject
    public PlayerView(IPlayerModel oData)
    {
		m_oData = oData;
    }
    
	public void init() {
		this.setLayout(null);		
		setDoubleBuffered(true);
	}

	public void setDimension(Dimension oDimension) {
    	m_oDimension = oDimension;	
    }
    
    void draw(Graphics oGraphics)
    {
        oGraphics.setColor(Color.BLACK);
        oGraphics.fillRect(0, 0, m_oDimension.width, m_oDimension.height);
        
        oGraphics.setColor(Color.WHITE);
        oGraphics.setFont(new Font("Arial", Font.PLAIN, 14));

       	oGraphics.drawString("Active Player:", 5, 20);

       	 oGraphics.setColor(Color.BLUE);
         oGraphics.fillRect(0, 30, m_oDimension.width, m_oDimension.height - 30);
         
         oGraphics.setColor(Color.YELLOW);
         oGraphics.setFont(new Font("Arial", Font.BOLD, 20));

         if( m_oData != null) {
        	 oGraphics.drawString(m_oData.getPlayerName(), 5, 50);
        }
        else {
        	 oGraphics.drawString("Sajad Karim", 5, 50);
         }
    }

    @Override
    public void paintComponent(Graphics oGraphics)
    {
    	draw(oGraphics);
    }

	@Override
	public void drawView() {
		draw( this.getParent().getGraphics());
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component getViewComponent() {
		return this;
	}

	@Override
	public void setViewData(IModel oData) {
		m_oData = (IPlayerModel)oData;
	}

}
