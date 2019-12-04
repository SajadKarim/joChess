package jchess.view;

import java.awt.Component;
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
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import jchess.Chessboard;
import jchess.RuleEngine;
import jchess.Square;
import jchess.common.Board;
import jchess.common.IPolygon;
import jchess.common.Piece;
import jchess.common.PositionData;
import jchess.model.BoardModel;
import jchess.model.IBoardModel;
import jchess.common.Position;

public class BoardView extends JPanel implements IBoardView, MouseListener, ComponentListener {
	private IBoardModel m_oBoardModel;
	private final ArrayList<BoardViewListener> m_lstListeners;

	public BoardView() {
		this.setDoubleBuffered(true);
        
		m_oBoardModel = null;
		addMouseListener(this);		
        m_lstListeners = new ArrayList<BoardViewListener>();
	}
	
	public void setModelData(IBoardModel oBoardModel) {
		m_oBoardModel = oBoardModel;
	}
	
	private void drawBoard(Graphics oGraphics) {
        oGraphics.drawImage(m_oBoardModel.getBoard().getBoardImage(), 0, 0, m_oBoardModel.getBoard().getBoardWidth(), m_oBoardModel.getBoard().getBoardHeight(), null);        

        drawPositions(oGraphics);
    }
	
	private void drawPositions(Graphics oGraphics) {
		Iterator<Position> itt = m_oBoardModel.getBoard().getPositions().iterator();
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
        imageGr.drawImage(m_oBoardModel.getBoard().getActivCellImage(), 0, 0, (int)p.getBounds2D().getWidth(), (int)p.getBounds2D().getHeight(), null);
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
        	
            Image tempImage2 = m_oBoardModel.getBoard().getMarkedCellImage();
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
        	oGraphics.drawImage(m_oBoardModel.getBoard().getMarkedCellImage(), (int)p2.getBounds2D().getCenterX()-20, (int)p2.getBounds2D().getCenterY()-20, null);
        }
	
	/*
	public void paintComponent(Graphics g, Position oPosition, ArrayList<Position> lstPositions)
    {
		m_oGraphics = g;
		drawBoard(g);

		drawPieces(g, boardManager.getInstance().getAllPositions());

		selectPosition(oPosition);
		
		markPositions(lstPositions);

	}

	public void drawPieces(Graphics g, List<Position> lstPosition) {
		m_oGraphics = g;
        
		//for (Map.Entry<String, Position> entry : lstPosition.entrySet()) {
    	////}
		Iterator<Position> itt = lstPosition.iterator();
    	while( itt.hasNext()) {
    		Position oPosition = itt.next();//entry.getValue();
    		if( oPosition.getPiece() != null)
    			drawPiece( ((IPolygon)(oPosition.getShape())).getPolygon(), ((Piece)oPosition.getPiece()).getImage());
    	}
	}
	*/
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
	
    public void paintComponent(Graphics g)
    {
    	if( m_oBoardModel != null) {
    		drawBoard(g);
    		drawPositions(g);
    	}
    }
    
    public void mouseClicked(MouseEvent event)
    {
    
    	switch(event.getButton() ) {
    		case MouseEvent.BUTTON1:{
                int x = event.getX();
                int y = event.getY();
   	
                Position oPosition = getPosition(x, y);
                
                notifyListenersOnPositionClicked(oPosition);
    		}
    		break;
    	}
    }

    public Position getPosition(int x, int y) {
	    Iterator<Position> itt = m_oBoardModel.getBoard().getPositions().iterator();
		while( itt.hasNext()) {
			Position oPosition = itt.next();
			if( ((IPolygon) oPosition.getShape()).getPolygon().contains(new Point(x, y)))
			{
				return oPosition;
			}
		}
		
		return null;
    }
    
    public void mousePressed(MouseEvent event) {
    	System.out.println("mouse pressed!");
    }
    
    public void mouseReleased(MouseEvent arg0)
    {
    	System.out.println("mouse released!");
    }

    public void mouseEntered(MouseEvent arg0)
    {
    	System.out.println("mouse entered!");
    }

    public void mouseExited(MouseEvent arg0)
    {
    	System.out.println("mouse exited!");
    }

    public void componentResized(ComponentEvent e)
    {
    }

    public void componentMoved(ComponentEvent e)
    {
    }

    public void componentShown(ComponentEvent e)
    {
    }

    public void componentHidden(ComponentEvent e)
    {
    }
    
    private void notifyListenersOnPositionClicked(Position oPosition) {
        for (final BoardViewListener oListener : m_lstListeners) {
            oListener.onPositionClicked(oPosition);
        }
    }

    public void addListener(final BoardViewListener oListener) {
        m_lstListeners.add(oListener);
    }

    public void paintView() {
    	paintComponent(this.getGraphics());
    }
    
    public Component getViewComponent() {
    	return this;
    }
}
