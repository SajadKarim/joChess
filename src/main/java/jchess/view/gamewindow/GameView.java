package jchess.view.gamewindow;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JPanel;

import com.google.inject.Inject;

import jchess.model.IModel;
import jchess.model.gamewindow.IGameModel;
import jchess.common.IPolygon;
import jchess.common.IPositionAgent;

/**
 * This class with the help of custom control (child view) draws Chessboard, Clock, etc.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class GameView extends JPanel implements IGameView, MouseListener, ComponentListener {
	private IGameModel m_oData;
	private final IClockView m_oClockView;
	private final IBoardView m_oBoardView;    
	private final IPlayerView m_oPlayerView;
    private final IMoveHistoryView m_oMoveHistoryView;	

	private final ArrayList<IGameViewListener> m_lstListener;
	
	@Inject
	public GameView(IGameModel oData, final IBoardView oBoardView, final IPlayerView oPlayerView, final IClockView oClockView, final IMoveHistoryView oMoveHistoryView) {
		m_oData = oData;
		m_oBoardView = oBoardView;
		m_oPlayerView = oPlayerView;
		m_oClockView = oClockView;
		m_oMoveHistoryView = oMoveHistoryView;
		m_lstListener = new ArrayList<IGameViewListener>();
	}
	
	public void init() {
		this.setLayout(null);
        this.setDoubleBuffered(true);
		this.setLocation(new Point(0, 0));        
        
		initSubViews();
		
		addMouseListener(this);		
        addComponentListener(this);
	}
		
	public void initSubViews() {
		Dimension oDimension = new Dimension(m_oData.getBoard().getBoardWidth(), m_oData.getBoard().getBoardHeight());        
        m_oBoardView.SetDimension(oDimension);
        m_oBoardView.getViewComponent().setSize(oDimension);
        m_oBoardView.getViewComponent().setLocation(new Point(0, 0));
        this.add(m_oBoardView.getViewComponent());
        m_oBoardView.init();
        
		oDimension = new Dimension(180, 60);        
        m_oPlayerView.setDimension( oDimension);
        m_oPlayerView.getViewComponent().setSize(oDimension);
        m_oPlayerView.getViewComponent().setLocation(new Point(m_oData.getBoard().getBoardWidth() + 20, 0));
        this.add(m_oPlayerView.getViewComponent());
        m_oPlayerView.init();
        
        oDimension = new Dimension(180, 60);
        m_oClockView.setDimension(oDimension);
        m_oClockView.getViewComponent().setSize(oDimension);
        m_oClockView.getViewComponent().setLocation(new Point(m_oData.getBoard().getBoardWidth() + 20, 60));
        this.add(m_oClockView.getViewComponent());
        m_oClockView.init();
        
        oDimension = new Dimension(180, 500);
        m_oMoveHistoryView.getScrollPane().setSize(oDimension);
        m_oMoveHistoryView.getScrollPane().setLocation(new Point(m_oData.getBoard().getBoardWidth() + 20, 120));
        this.add(m_oMoveHistoryView.getScrollPane());
        m_oMoveHistoryView.init();	}
	
    public void mouseClicked(MouseEvent event)
    {
    	switch(event.getButton() ) {
    		case MouseEvent.BUTTON1:{
                int x = event.getX();
                int y = event.getY();
   	
                IPositionAgent oPosition = getPosition(x, y);
                
                if( oPosition != null) {
                	notifyListenersOnPositionClicked(oPosition);
                }
    		}
    		break;
    	}
    }

    public IPositionAgent getPosition(int x, int y) {
    	for (Map.Entry<String, IPositionAgent> entry : m_oData.getBoard().getAllPositionAgents().entrySet()) {
			IPositionAgent oPosition = entry.getValue();
			if( ((IPolygon) oPosition.getShape()).getPolygon().contains(new Point(x, y))) {
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
    
    public Component getViewComponent() {
    	return this;
    }
    
    public void repaintClockView() {
    	try {
    		m_oClockView.getViewComponent().repaint();
    	}catch(java.lang.Exception e) {
    		
    	}
    }
    
    public void repaintBoardView() {
    	try {
    		m_oBoardView.getViewComponent().repaint();
    	}
    	catch(java.lang.Exception e) {
    	}
    }
    
    public void repaintPlayerView() {
    	try {    		
    		m_oPlayerView.getViewComponent().repaint();
    	}
    	catch(java.lang.Exception e) {
    	}
    }
    @Override
    public void paintComponent(Graphics oGraphics)
    {
    	super.paintComponent(oGraphics);
    	//drawView();
    }

	@Override
	public void drawView() {
		m_oBoardView.drawView();
		m_oPlayerView.drawView();
		m_oClockView.drawView();
	}

	@Override
	public void refreshView() {
		drawView();
	}

	@Override
	public void setViewData(IModel oData) {
		m_oData = (IGameModel)oData;
	}

	public void addListener(final IGameViewListener oListener) {
        m_lstListener.add(oListener);
    }
	
	private void notifyListenersOnPositionClicked(IPositionAgent oPosition) {
        for (final IGameViewListener oListener : m_lstListener) {
            oListener.onPositionClicked(oPosition);
        }
    }
}
