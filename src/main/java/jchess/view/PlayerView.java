package jchess.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import jchess.model.IPlayerModel;

public class PlayerView extends JPanel implements IPlayerView {
	private IPlayerModel m_oData;
	private Dimension m_oDimension; 
    private Graphics g;
    private BufferedImage imgBackground;
    private Graphics bufferedGraphics;

    public PlayerView()
    {    	
        setDoubleBuffered(true);
    }
    
    public void setDimension(Dimension oDimension) {
    	m_oDimension = oDimension;	
    }
    
    public void setData(IPlayerModel oData)
    {
    	m_oData = oData;
    }

    /** Method of drawing graphical background of clock
     */
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
       /* Graphics gr = this.background.getGraphics();
        Graphics2D g2d = (Graphics2D) gr;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.ITALIC, 20);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(5, 30, 80, 30);
        g2d.setFont(font);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(85, 30, 90, 30);
        g2d.drawRect(5, 30, 170, 30);
        g2d.drawRect(5, 60, 170, 30);
        g2d.drawLine(85, 30, 85, 90);
        font = new Font("Serif", Font.ITALIC, 16);
        g2d.drawString("a", 10, 50);
        g2d.setColor(Color.WHITE);
        g2d.drawString("b", 100, 50);
        this.bufferedGraphics = this.background.getGraphics();
        */
    }

    /**
    Annotation to superclass Graphics drawing the clock graphics
     * @param g Graphics2D Capt object to paint
     */
    @Override
    public void paint(Graphics oGraphics)
    {
    	draw(oGraphics);
        //System.out.println("rysuje zegary");
        //super.paint(g);
       /* Graphics2D g2d = (Graphics2D) oGraphics;
        g2d.drawImage(this.background, 0, 0, this);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.ITALIC, 20);
        g2d.drawImage(this.background, 0, 0, this);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(5, 30, 80, 30);
        g2d.setFont(font);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(85, 30, 90, 30);
        g2d.drawRect(5, 30, 170, 30);
        g2d.drawRect(5, 60, 170, 30);
        g2d.drawLine(85, 30, 85, 90);
        font = new Font("Serif", Font.ITALIC, 14);
        g2d.drawImage(this.background, 0, 0, this);
        g2d.setFont(font);
        g.drawString("a", 10, 50);
        g.setColor(Color.WHITE);
        g.drawString("b", 100, 50);
        g2d.setFont(font);
        g.setColor(Color.BLACK);
        g2d.drawString("a", 10, 80);
        g2d.drawString("b", 90, 80);
        */
    }

    /**
    Annotation to superclass Graphics updateing clock graphisc
     * @param g Graphics2D Capt object to paint
     */
    @Override
    public void update(Graphics g)
    {
        paint(g);
    }
    
	public Component getComponent() {
		return this;
	}

}
