package jchess.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jchess.model.IBoardModel;
import jchess.model.IGameModel;
import jchess.model.IClockModel;
import jchess.common.IPolygon;
import jchess.common.Position;

public class GameView extends JPanel implements IGameView, MouseListener, ComponentListener {
	private IGameModel m_oGameModel;
    
	private IClockView m_oClockView;
	private IBoardView m_oBoardView;
    private IMoveHistoryView m_oMoveHistoryView;
    
	private final ArrayList<GameViewListener> m_lstListeners;

	private Dimension m_oBoardViewDimensions;        
	private Dimension m_oClockViewDimensions;
	private Dimension m_oHistoryViewDimensions;

	private Point m_oBoardViewStartLocation;
	private Point m_oClockViewStartLocation;
	private Point m_oHistoryViewStartLocation;

	public GameView() {
        m_lstListeners = new ArrayList<GameViewListener>();
	}
	
	public void init() {
		this.setLayout(null);
		this.setLocation(new Point(0, 0));        
        
		initPositionsAndDimensions();
		initSubViews();
		
		addMouseListener(this);		
        addComponentListener(this);

        //m_oBoardView.draw(this.getGraphics());
		this.setDoubleBuffered(true);

	}
	
	public void initPositionsAndDimensions() {
		m_oBoardViewDimensions = new Dimension(m_oGameModel.getBoard().getBoardWidth(), m_oGameModel.getBoard().getBoardHeight());        
        m_oClockViewDimensions = new Dimension(180, 60);
        m_oHistoryViewDimensions = new Dimension(180, 500);

        m_oBoardViewStartLocation = new Point(0, 0);
        m_oClockViewStartLocation = new Point(m_oGameModel.getBoard().getBoardWidth() + 20, 0);
        m_oHistoryViewStartLocation = new Point(m_oGameModel.getBoard().getBoardWidth() + 20, 60);
	}
	
	public void initSubViews() {
        m_oBoardView = new BoardView();
        m_oBoardView.setData((IBoardModel)m_oGameModel);
        m_oBoardView.SetDimension(m_oBoardViewDimensions);
        Component oBoardComponent = m_oBoardView.getComponent();
        oBoardComponent.setSize(m_oBoardViewDimensions);
        oBoardComponent.setLocation(m_oBoardViewStartLocation);        
        this.add(oBoardComponent);

        m_oClockView = new ClockView();
        m_oClockView.setData((IClockModel)m_oGameModel);
        m_oClockView.setDimension( m_oClockViewDimensions);
        Component oClockComponent = m_oClockView.getComponent();
        oClockComponent.setSize(m_oClockViewDimensions);
        oClockComponent.setLocation(m_oClockViewStartLocation);
        this.add(oClockComponent);

        m_oMoveHistoryView = new MoveHistoryView(null);
        //m_oMoveHistoryView.setData((IMoveHistoryModel)m_oGameModel);
        //m_oMoveHistoryView.setDimensions( m_oHistoryViewDimensions);
        JScrollPane oMoveHistory = m_oMoveHistoryView.getScrollPane();
        oMoveHistory.setSize(m_oHistoryViewDimensions);
        oMoveHistory.setLocation(m_oHistoryViewStartLocation);
        this.add(oMoveHistory);
        
	}
	
	public void setModelData(IGameModel oBoardModel) {
		m_oGameModel = oBoardModel;
	}
	
	
	@Override
    public void paintComponent(Graphics g)
    {
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
	    Iterator<Position> itt = m_oGameModel.getBoard().getPositions().iterator();
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
        for (final GameViewListener oListener : m_lstListeners) {
            oListener.onPositionClicked(oPosition);
        }
    }

    public void addListener(final GameViewListener oListener) {
        m_lstListeners.add(oListener);
    }

    public void paintView() {
    	paintComponent(this.getGraphics());
    }
    
    public Component getViewComponent() {
    	return this;
    }
    
    public void repaintClockView() {
    	try {
    	m_oClockView.getComponent().repaint();
    	}catch(java.lang.Exception e) {
    		
    	}
    }
}