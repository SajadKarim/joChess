package jchess.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jchess.Moves;
import jchess.common.IPolygon;
import jchess.common.Piece;
import jchess.common.Position;
import jchess.model.IBoardModel;
import jchess.model.IGameModel;

public class BoardView extends JPanel implements IBoardView {
	private IBoardModel m_oBoardModel;
	private Dimension m_oDimension;
	
	public BoardView() {
	}

	public void setData(IBoardModel oBoardModel) {
		m_oBoardModel = oBoardModel;
	}

	public void SetDimension(Dimension oDimension) {
		m_oDimension = oDimension;
	}
	
	public void draw(Graphics oGraphics) {
        oGraphics.drawImage(m_oBoardModel.getBoardImage(), 0, 0, null);        

        drawPositions(oGraphics);
    }
	
	private void drawPositions(Graphics oGraphics) {
		Iterator<Position> itt = m_oBoardModel.getPositions().iterator();
    	while( itt.hasNext()) {
    		Position oPosition = itt.next();//entry.getValue();
    		if( oPosition.getPiece() != null) {
    			drawPiece(oGraphics, ((IPolygon)(oPosition.getShape())).getPolygon(), ((Piece)oPosition.getPiece()).getImage());
    			
    			if( oPosition.getSelectState())
    				selectPosition(oGraphics, oPosition);
    		}
    		if( oPosition.getMoveCandidacy())
    			markPositions(oGraphics, oPosition);
    	}
	}
	
	public void selectPosition(Graphics oGraphics, Position oPosition) {
		
		Polygon p = ((IPolygon)oPosition.getShape()).getPolygon();
    	
        BufferedImage resized = new BufferedImage((int)p.getBounds2D().getWidth(), (int)p.getBounds2D().getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D imageGr = (Graphics2D) resized.createGraphics();
        imageGr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        imageGr.drawImage(m_oBoardModel.getActivCellImage(), 0, 0, (int)p.getBounds2D().getWidth(), (int)p.getBounds2D().getHeight(), null);
        imageGr.dispose();
        Image image__ = resized.getScaledInstance((int)p.getBounds2D().getWidth(), (int)p.getBounds2D().getHeight(), 0);
        //g2d.drawImage(image, x, y, null);
        
    	oGraphics.setClip(p);
    	//g.setColor(Color.cyan);
    	//g.fillPolygon(p);
    	oGraphics.drawImage(image__, (int)p.getBounds2D().getMinX(), (int)p.getBounds2D().getMinY(), null);

	}
	
	public void markPositions(Graphics oGraphics, Position oPosition) {	
        	Polygon p2 = ((IPolygon)oPosition.getShape()).getPolygon();
        	
            Image tempImage2 = m_oBoardModel.getMarkedCellImage();
            BufferedImage resized2 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB_PRE);
            Graphics2D imageGr2 = (Graphics2D) resized2.createGraphics();
            imageGr2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            imageGr2.drawImage(tempImage2, 0, 0, 10, 10, null);
            imageGr2.dispose();
            Image image__2 = resized2.getScaledInstance(20,20, 0);
            //g2d.drawImage(image, x, y, null);
            
        	oGraphics.setClip(p2);
        	//g.setColor(Color.cyan);
        	//g.fillPolygon(p);
        	oGraphics.drawImage(m_oBoardModel.getMarkedCellImage(), (int)p2.getBounds2D().getCenterX()-20, (int)p2.getBounds2D().getCenterY()-20, null);
        }
	
	public void drawPiece(Graphics oGraphics, Polygon oPolygon, Image oPieceImage)
    {
        try
        {
            //Graphics2D oGraphics2d = (Graphics2D) m_oGraphics;
            //oGraphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            oGraphics.setClip(oPolygon);
            oGraphics.drawImage(oPieceImage, 
            		(int)oPolygon.getBounds2D().getCenterX()-30, 
            		(int)oPolygon.getBounds2D().getCenterY()-30, 
            		null);
        }
        catch (java.lang.NullPointerException exc)
        {
            System.out.println("Something wrong when painting piece: " + exc.getMessage());
        }
    }
	
	@Override
    public void paintComponent(Graphics oGraphics)
    {
		draw(oGraphics);
    }
    
	public void setModel(IBoardModel oBoardModel) {
		m_oBoardModel = oBoardModel;
	}
	
    public Component getComponent() {
    	return this;
    }
}
