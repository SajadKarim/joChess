package jchess.view.gamewindow;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.JPanel;

import com.google.inject.Inject;

import jchess.common.IPolygon;
import jchess.common.IPositionAgent;
import jchess.gamelogic.PieceAgent;
import jchess.model.IModel;
import jchess.model.gamewindow.IBoardModel;

/**
 * This class is responsible to draw Chessboard and related operations regarding GUI.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class BoardView extends JPanel implements IBoardView {
	private IBoardModel m_oData;
	private Dimension m_oDimension;
	
	@Inject
	public BoardView(final IBoardModel oData) {
		m_oData = oData;
	}

	public void init() {
		this.setLayout(null);		
		this.setDoubleBuffered(false);
	}

	public void SetDimension(Dimension oDimension) {
		m_oDimension = oDimension;
	}
	
	public void draw(Graphics oGraphics) {
        oGraphics.drawImage(m_oData.getBoardImage(), 0, 0, null);        

        drawPositions(oGraphics);
    }
	
	private void drawPositions(Graphics oGraphics) {
		for (Map.Entry<String,IPositionAgent> entry : m_oData.getPositions().entrySet()) {
    		IPositionAgent oPosition = entry.getValue();
    		if( oPosition.getPiece() != null) {
    			drawPiece(oGraphics, ((IPolygon)(oPosition.getShape())).getPolygon(), ((PieceAgent)oPosition.getPiece()).getImage());
    			
    			if( oPosition.getSelectState())
    				selectPosition(oGraphics, oPosition);
    		}
    		if( oPosition.getMoveCandidacy())
    			markPositions(oGraphics, oPosition);
    	}
	}
	/**
	 * Select the position in the chess board
	 * @param oGraphics the chess board
	 * @param oPosition the position in the chess board
	 */
	public void selectPosition(Graphics oGraphics, IPositionAgent oPosition) {
		try
        {
		Polygon oPolygon = ((IPolygon)oPosition.getShape()).getPolygon();
    	
		int nWidth = (int)oPolygon.getBounds2D().getWidth();
		int nHeight = (int)oPolygon.getBounds2D().getHeight();
		
        BufferedImage resized = new BufferedImage(nWidth, nHeight, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D imageGr = (Graphics2D) resized.createGraphics();
        imageGr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        imageGr.drawImage(m_oData.getActivCellImage(), 0, 0, nWidth, nHeight, null);
        imageGr.dispose();
        Image oResizedImage = resized.getScaledInstance(nWidth, nHeight, 0);
        
    	oGraphics.setClip(oPolygon);
    	oGraphics.drawImage(oResizedImage, (int)oPolygon.getBounds2D().getMinX(), (int)oPolygon.getBounds2D().getMinY(), null);
        }
        catch (java.lang.NullPointerException exc)
        {
            System.out.println("Something wrong when painting piece: " + exc.getMessage());
        }

	}
	/**
	 * Mark the position in the chess board
	 * @param oGraphics the chess board
	 * @param oPosition the position in the chess board
	 */
	public void markPositions(Graphics oGraphics, IPositionAgent oPosition) {
    	
		try
        {
			Polygon oPolygon = ((IPolygon)oPosition.getShape()).getPolygon();
        	
			oGraphics.setClip(oPolygon);
			oGraphics.drawImage(m_oData.getMarkedCellImage(), (int)oPolygon.getBounds2D().getCenterX()-20, (int)oPolygon.getBounds2D().getCenterY()-20, null);
        }
        catch (java.lang.NullPointerException exc)
        {
            System.out.println("Something wrong when painting piece: " + exc.getMessage());
        }
    }
	/**
	 * Draw the pice in the position of the chess board
	 * @param oGraphics the chess board
	 * @param oPolygon the position of the piece
	 * @param oPieceImage the image of the piece
	 */
	public void drawPiece(Graphics oGraphics, Polygon oPolygon, Image oPieceImage)
    {
        try
        {
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
    	super.paintComponent(oGraphics);
    	draw(oGraphics);
    }
	
	@Override
	public void drawView() {
		draw(this.getParent().getGraphics());		
	}

	@Override
	public void refreshView() {
		draw(this.getParent().getGraphics());		
	}

	@Override
	public Component getViewComponent() {
		return this;
	}

	@Override
	public void setViewData(IModel oData) {
		m_oData = (IBoardModel)oData;
	}
}
