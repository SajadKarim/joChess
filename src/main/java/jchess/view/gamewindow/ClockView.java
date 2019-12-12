package jchess.view.gamewindow;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.google.inject.Inject;

import jchess.model.IModel;
import jchess.model.gamewindow.IClockModel;

/**
 * This class is responsible to draw Clock and related operations regarding GUI.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class ClockView extends JPanel implements IClockView {
	private IClockModel m_oData;
	private Dimension m_oDimension;
	
	@Inject
    public ClockView(IClockModel oData)
    {
    	m_oData = oData;
        this.setDoubleBuffered(true);
    }
    
    public void setDimension(Dimension oDimension) {
    	m_oDimension = oDimension;	
    }
    
    void draw(Graphics oGraphics)
    {
         oGraphics.setColor(Color.BLACK);
         oGraphics.fillRect(0, 0, m_oDimension.width, m_oDimension.height);
         
         oGraphics.setColor(Color.WHITE);
         oGraphics.setFont(new Font("Courier", Font.BOLD, 60));

         oGraphics.drawString(m_oData.getClockText(), 0, 50);
    }

    @Override
    public void paintComponent(Graphics oGraphics)
    {
    	draw(oGraphics);
    }

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawView() {
		draw(this.getParent().getGraphics());
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
	}
}
