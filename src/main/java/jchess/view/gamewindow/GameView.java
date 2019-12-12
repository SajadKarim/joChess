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
import javax.swing.JScrollPane;

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
    
	private IPlayerView m_oPlayerView;
	private IClockView m_oClockView;
	private IBoardView m_oBoardView;
    private IMoveHistoryView m_oMoveHistoryView;	

	private Dimension m_oPlayerViewDimensions;        
	private Dimension m_oBoardViewDimensions;        
	private Dimension m_oClockViewDimensions;
	private Dimension m_oHistoryViewDimensions;

	private Point m_oPlayerViewStartLocation;
	private Point m_oBoardViewStartLocation;
	private Point m_oClockViewStartLocation;
	private Point m_oHistoryViewStartLocation;

	private IGameView_Callback m_oCallback;
	
	@Inject
	public GameView(IGameModel oData, IBoardView oBoardView, IClockView oClockView, IPlayerView oPlayerView, IMoveHistoryView oHistoryView) {
		m_oData = oData;
		m_oBoardView = oBoardView;
		m_oClockView = oClockView;
		m_oPlayerView = oPlayerView;
		m_oMoveHistoryView = oHistoryView;
	}
	
	
	public void init() {
		this.setLayout(null);
		this.setLocation(new Point(0, 0));        
        this.setSize(1000, 800);
		initPositionsAndDimensions();
		initSubViews();
		
		addMouseListener(this);		
        addComponentListener(this);

        this.setDoubleBuffered(true);
	}
	
	public void setCallback(IGameView_Callback oCallback) {
		m_oCallback = oCallback; 
	}
	
	public void initPositionsAndDimensions() {
		m_oBoardViewDimensions = new Dimension(m_oData.getBoard().getBoardWidth(), m_oData.getBoard().getBoardHeight());        
		m_oPlayerViewDimensions = new Dimension(180, 60);
        m_oClockViewDimensions = new Dimension(180, 60);
        m_oHistoryViewDimensions = new Dimension(180, 500);

        m_oBoardViewStartLocation = new Point(0, 0);
        m_oPlayerViewStartLocation = new Point(m_oData.getBoard().getBoardWidth() + 20, 0);
        m_oClockViewStartLocation = new Point(m_oData.getBoard().getBoardWidth() + 20, 60);
        m_oHistoryViewStartLocation = new Point(m_oData.getBoard().getBoardWidth() + 20, 120);
	}
	
	public void initSubViews() {
        //m_oBoardView = new BoardView();
        m_oBoardView.setViewData(m_oData);
        m_oBoardView.SetDimension(m_oBoardViewDimensions);
        Component oBoardComponent = m_oBoardView.getViewComponent();
        oBoardComponent.setSize(m_oBoardViewDimensions);
        oBoardComponent.setLocation(m_oBoardViewStartLocation);
        this.add(oBoardComponent);

        //m_oPlayerView = new PlayerView();
        m_oPlayerView.setViewData(m_oData);
        m_oPlayerView.setDimension( m_oPlayerViewDimensions);
        Component oPlayerComponent = m_oPlayerView.getViewComponent();
        oPlayerComponent .setSize(m_oPlayerViewDimensions);
        oPlayerComponent .setLocation(m_oPlayerViewStartLocation);
        this.add(oPlayerComponent );

        //m_oClockView = new ClockView();
        m_oClockView.setViewData(m_oData);
        m_oClockView.setDimension( m_oClockViewDimensions);
        Component oClockComponent = m_oClockView.getViewComponent();
        oClockComponent.setSize(m_oClockViewDimensions);
        oClockComponent.setLocation(m_oClockViewStartLocation);
        this.add(oClockComponent);

        //m_oMoveHistoryView = new MoveHistoryView(null);
        JScrollPane oMoveHistory = m_oMoveHistoryView.getScrollPane();
        oMoveHistory.setSize(m_oHistoryViewDimensions);
        oMoveHistory.setLocation(m_oHistoryViewStartLocation);
        this.add(oMoveHistory);
        
	}
	
    public void mouseClicked(MouseEvent event)
    {
    
    	switch(event.getButton() ) {
    		case MouseEvent.BUTTON1:{
                int x = event.getX();
                int y = event.getY();
   	
                IPositionAgent oPosition = getPosition(x, y);
                
                if( oPosition != null) {
                	m_oCallback.onPositionClicked(oPosition);
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
	public void drawView() {
		//this.repaint();
		//this.revalidate();
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
		//m_oData = (IGameModel)oData;
	}
}
