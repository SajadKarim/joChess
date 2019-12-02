package jchess.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import jchess.Chessboard;
import jchess.RuleEngine;
import jchess.boardManager;
import jchess.common.BoardAgent;
import jchess.common.IPolygon;
import jchess.common.PieceAgent;
import jchess.common.Position;
import jchess.common.PositionAgent;

public class BoardView extends JPanel implements IBoardView {
	private BoardAgent m_oBoard;
	private Graphics m_oGraphics;
	private Point m_oTopLeftPoint;
	
	public BoardView(Graphics oGraphics, Point oTopLeftPoint) {
		m_oGraphics = oGraphics;
		m_oTopLeftPoint = oTopLeftPoint;
	}
	
	public void setBoard(BoardAgent oBoard) {
		m_oBoard = oBoard;
	}
	
	public void drawBoard(Graphics g) {
        g.drawImage(m_oBoard.getBoardImage(), m_oTopLeftPoint.x, m_oTopLeftPoint.y, 808, 700, null);//draw an Image of chessboard
	}
	
	public void selectPosition(PositionAgent oPosition) {
		Polygon p = ((IPolygon)oPosition.getShape()).getPolygon();
    	
        BufferedImage resized = new BufferedImage((int)p.getBounds2D().getWidth(), (int)p.getBounds2D().getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D imageGr = (Graphics2D) resized.createGraphics();
        imageGr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        imageGr.drawImage(m_oBoard.getActivCellImage(), 0, 0, (int)p.getBounds2D().getWidth(), (int)p.getBounds2D().getHeight(), null);
        imageGr.dispose();
        Image image__ = resized.getScaledInstance((int)p.getBounds2D().getWidth(), (int)p.getBounds2D().getHeight(), 0);
        //g2d.drawImage(image, x, y, null);
        
    	m_oGraphics.setClip(p);
    	//g.setColor(Color.cyan);
    	//g.fillPolygon(p);
    	m_oGraphics.drawImage(image__, (int)p.getBounds2D().getMinX(), (int)p.getBounds2D().getMinY(), null);

	}
	
	public void markPositions(ArrayList<PositionAgent> lstPositions) {	
        for (Iterator it = lstPositions.iterator(); lstPositions != null && it.hasNext();)
        {
        	PositionAgent sq = (PositionAgent) it.next();
        	
        	Polygon p2 = ((IPolygon)sq.getShape()).getPolygon();
        	
            Image tempImage2 = m_oBoard.getMarkedCellImage();
            BufferedImage resized2 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB_PRE);
            Graphics2D imageGr2 = (Graphics2D) resized2.createGraphics();
            imageGr2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            imageGr2.drawImage(tempImage2, 0, 0, 10, 10, null);
            imageGr2.dispose();
            Image image__2 = resized2.getScaledInstance(20,20, 0);
            //g2d.drawImage(image, x, y, null);
            
        	m_oGraphics.setClip(p2);
        	//g.setColor(Color.cyan);
        	//g.fillPolygon(p);
        	m_oGraphics.drawImage(m_oBoard.getMarkedCellImage(), (int)p2.getBounds2D().getCenterX()-20, (int)p2.getBounds2D().getCenterY()-20, null);
        }
	}
	
	public void paintComponent(Graphics g, PositionAgent oPosition, ArrayList<PositionAgent> lstPositions)
    {
		m_oGraphics = g;
		drawBoard(g);

		drawPieces(g, boardManager.getInstance().getAllPositions());

		selectPosition(oPosition);
		
		markPositions(lstPositions);

	}

	public void drawPieces(Graphics g, Map<String, PositionAgent> lstPosition) {
		m_oGraphics = g;
        
		for (Map.Entry<String, PositionAgent> entry : lstPosition.entrySet()) {
    	//}
		//Iterator<PositionAgent> itt = lstPosition.iterator();
    	//while( itt.hasNext()) {
    		PositionAgent oPosition = entry.getValue();
    		if( oPosition.getPiece() != null)
    			drawPiece( ((IPolygon)(oPosition.getShape())).getPolygon(), ((PieceAgent)oPosition.getPiece()).getImage());
    	}
	}
	
	public void drawPiece(Polygon oPolygon, Image oPieceImage)
    {
        try
        {
            //Graphics2D oGraphics2d = (Graphics2D) m_oGraphics;
            //oGraphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            m_oGraphics.setClip(oPolygon);
            m_oGraphics.drawImage(oPieceImage, 
            		(int)oPolygon.getBounds2D().getCenterX()-30, 
            		(int)oPolygon.getBounds2D().getCenterY()-30, 
            		null);
        }
        catch (java.lang.NullPointerException exc)
        {
            System.out.println("Something wrong when painting piece: " + exc.getMessage());
        }
    }
}
