package jchess.gui.view.gamewindow;

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

import jchess.gui.model.gamewindow.IGameModel;
import jchess.util.AppLogger;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;
import jchess.common.IPolygon;
import jchess.common.IPositionAgent;
import jchess.common.gui.IViewClosedListener;
import jchess.common.gui.IModel;

/**
 * This class with the help of custom control (child view) draws Chessboard, Clock, etc.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class GameView extends JPanel implements IGameView, MouseListener, ComponentListener {
	private IGameModel m_oData;
	private IClockView m_oClockView;
	private IBoardView m_oBoardView;    
	private IPlayerView m_oPlayerView;
    private IMoveHistoryView m_oMoveHistoryView;	
    private IAppLogger m_oLogger;
	private ArrayList<IGameViewListener> m_lstListener;
	
	@Inject
	public GameView(IGameModel oData, IBoardView oBoardView, IPlayerView oPlayerView, IClockView oClockView, IMoveHistoryView oMoveHistoryView, IAppLogger oLogger) {
		m_oData = oData;
		m_oBoardView = oBoardView;
		m_oPlayerView = oPlayerView;
		m_oClockView = oClockView;
		m_oMoveHistoryView = oMoveHistoryView;
		m_lstListener = new ArrayList<IGameViewListener>();
		m_oLogger = oLogger;
		
     	m_oLogger.writeLog(LogLevel.DETAILED, "Instantiating GameView.", "GameView", "GameView");
	}
	
	public void init() {
     	m_oLogger.writeLog(LogLevel.DETAILED, "Initializing GameView.", "init", "GameView");

		this.setLayout(null);
        this.setDoubleBuffered(true);
		this.setLocation(new Point(0, 0));        
        
		initSubViews();
		
		addMouseListener(this);		
        addComponentListener(this);
	}
		
	public void initSubViews() {
     	m_oLogger.writeLog(LogLevel.DETAILED, "Initializing sub views.", "initSubViews", "GameView");

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
                 	m_oLogger.writeLog(LogLevel.INFO, String.format("User clicked on Position=%s.", oPosition.getName()), "mouseClicked", "GameView");

                	notifyListenersOnPositionClicked(oPosition);
                }
    		}
    		break;
    	}
    }

    public IPositionAgent getPosition(int x, int y) {
     	m_oLogger.writeLog(LogLevel.DETAILED, String.format("Extracting Position for x=%d, y=%d",x ,y), "getPosition", "GameView");

    	for (Map.Entry<String, IPositionAgent> entry : m_oData.getBoard().getAllPositionAgents().entrySet()) {
			IPositionAgent oPosition = entry.getValue();
			if( ((IPolygon) oPosition.getShape()).getPolygon().contains(new Point(x, y))) {
				return oPosition;
			}
		}
		
		return null;
    }
    
    public void mousePressed(MouseEvent event) {
    }
    
    public void mouseReleased(MouseEvent arg0)
    {
    }

    public void mouseEntered(MouseEvent arg0)
    {
    }

    public void mouseExited(MouseEvent arg0)
    {
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
         	m_oLogger.writeLog(LogLevel.ERROR, String.format("Unhandled exception occured. %s", e.toString()), "repaintClockView", "GameView");
    	}
    }
    
    public void repaintBoardView() {
    	try {
    		m_oBoardView.getViewComponent().repaint();
    	}
    	catch(java.lang.Exception e) {
         	m_oLogger.writeLog(LogLevel.ERROR, String.format("Unhandled exception occured. %s", e.toString()), "repaintBoardView", "GameView");
    	}
    }
    
    public void repaintPlayerView() {
    	try {    		
    		m_oPlayerView.getViewComponent().repaint();
    	}
    	catch(java.lang.Exception e) {
         	m_oLogger.writeLog(LogLevel.ERROR, String.format("Unhandled exception occured. %s", e.toString()), "repaintPlayerView", "GameView");
    	}
    }
    @Override
    public void paintComponent(Graphics oGraphics)
    {
    	super.paintComponent(oGraphics);
    }

	@Override
	public void drawComponents() {
     	m_oLogger.writeLog(LogLevel.DETAILED, "Drawing sub components", "drawComponents", "GameView");

     	m_oBoardView.drawComponents();
		m_oPlayerView.drawComponents();
		m_oClockView.drawComponents();
	}

	@Override
	public void refresh() {
     	m_oLogger.writeLog(LogLevel.DETAILED, "Call for refresh.", "refresh", "GameView");

		drawComponents();
	}

	@Override
	public void setViewData(IModel oData) {
		m_oData = (IGameModel)oData;
	}

	public void addListener(final IGameViewListener oListener) {
        m_lstListener.add(oListener);
    }
	
	private void notifyListenersOnPositionClicked(IPositionAgent oPosition) {
     	m_oLogger.writeLog(LogLevel.DETAILED, "Notifying all the listeners about the selected Position.", "notifyListenersOnPositionClicked", "GameView");

     	for (final IGameViewListener oListener : m_lstListener) {
            oListener.onPositionClicked(oPosition);
        }
    }
	
    public void addMove(String stMoveString) {
    	m_oMoveHistoryView.addMove(stMoveString);
    }
    
    public void removeMove(String stMoveString) {
    	m_oMoveHistoryView.removeMove(stMoveString);
    }

    public void tryUndoMove() {
        for (final IGameViewListener oListener : m_lstListener) {
            oListener.onPlayerRequestForUndoMove();
        }
	}

	public void tryRedoMove() {
        for (final IGameViewListener oListener : m_lstListener) {
            oListener.onPlayerRequestForRedoMove();
        }
	}
}
