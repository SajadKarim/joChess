package jchess.common;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jchess.GUI;
import jchess.common.enumerator.Direction;

public class BoardAgent implements IBoard {
	private Board m_oBoard;
	private Image m_oBoardImage;
	private Image m_oActivCellImage;
	private Image m_oMarkedCellImage;
    private Map<String, PositionAgent> m_mpPosition;

	public BoardAgent(Board oBoard) {
		m_oBoard = oBoard;
		m_oBoardImage = GUI.loadImage(getBoardImagePath());
		m_oActivCellImage = GUI.loadImage(getActivCellImagePath());
		m_oMarkedCellImage = GUI.loadImage(getMarkedCellImagePath());
		
    	m_mpPosition = new HashMap<String, PositionAgent>();
    	
    	PopulatePositions();
    	mapPieces();
	}
	
	public String getName() {
		return m_oBoard.getName();
	}
	
	public String getBoardImagePath() {
		return m_oBoard.getBoardImagePath();
	}
	
	public String getActivCellImagePath() {
		return m_oBoard.getActivCellImagePath();
	}
	
	public String getMarkedCellImagePath() {
		return m_oBoard.getMarkedCellImagePath();
	}

	public int getBoardWidth() {
		return m_oBoard.getBoardWidth();
	}

	public int getBoardHeight() {
		return m_oBoard.getBoardHeight();
	}

	public Image getBoardImage() {
		return m_oBoardImage;
	}

	public Image getActivCellImage() {
		return m_oActivCellImage;
	}
	
	public Image getMarkedCellImage() {
		return m_oMarkedCellImage;
	}
	
    /*
    private void PopulatePositions() {
    	m_lstPosition = new ArrayList<PositionAgent>();
    	
        m_lstPosition.add(new PositionAgent(new Position('a',1, new Quadrilateral(60 * 0, 60 * 8, 60 * 1, 60 * 8, 60 * 1, 60 * 7, 60 * 0, 60 * 7)));
        m_lstPosition.add(new PositionAgent(new Position('a',2, new Quadrilateral(60 * 0, 60 * 7, 60 * 1, 60 * 7, 60 * 1, 60 * 6, 60 * 0, 60 * 6)));
        m_lstPosition.add(new PositionAgent(new Position('a',3, new Quadrilateral(60 * 0, 60 * 6, 60 * 1, 60 * 6, 60 * 1, 60 * 5, 60 * 0, 60 * 5)));
        m_lstPosition.add(new PositionAgent(new Position('a',4, new Quadrilateral(60 * 0, 60 * 5, 60 * 1, 60 * 5, 60 * 1, 60 * 4, 60 * 0, 60 * 4)));
        m_lstPosition.add(new PositionAgent(new Position('a',5, new Quadrilateral(60 * 0, 60 * 4, 60 * 1, 60 * 4, 60 * 1, 60 * 3, 60 * 0, 60 * 3)));
        m_lstPosition.add(new PositionAgent(new Position('a',6, new Quadrilateral(60 * 0, 60 * 3, 60 * 1, 60 * 3, 60 * 1, 60 * 2, 60 * 0, 60 * 2)));
        m_lstPosition.add(new PositionAgent(new Position('a',7, new Quadrilateral(60 * 0, 60 * 2, 60 * 1, 60 * 2, 60 * 1, 60 * 1, 60 * 0, 60 * 1)));
        m_lstPosition.add(new PositionAgent(new Position('a',8, new Quadrilateral(60 * 0, 60 * 1, 60 * 1, 60 * 1, 60 * 1, 60 * 0, 60 * 0, 60 * 0)));

        m_lstPosition.add(new PositionAgent(new Position('b',1, new Quadrilateral(60 * 1, 60 * 8, 60 * 2, 60 * 8, 60 * 2, 60 * 7, 60 * 1, 60 * 7)));
        m_lstPosition.add(new PositionAgent(new Position('b',2, new Quadrilateral(60 * 1, 60 * 7, 60 * 2, 60 * 7, 60 * 2, 60 * 6, 60 * 1, 60 * 6)));
        m_lstPosition.add(new PositionAgent(new Position('b',3, new Quadrilateral(60 * 1, 60 * 6, 60 * 2, 60 * 6, 60 * 2, 60 * 5, 60 * 1, 60 * 5)));
        m_lstPosition.add(new PositionAgent(new Position('b',4, new Quadrilateral(60 * 1, 60 * 5, 60 * 2, 60 * 5, 60 * 2, 60 * 4, 60 * 1, 60 * 4)));
        m_lstPosition.add(new PositionAgent(new Position('b',5, new Quadrilateral(60 * 1, 60 * 4, 60 * 2, 60 * 4, 60 * 2, 60 * 3, 60 * 1, 60 * 3)));
        m_lstPosition.add(new PositionAgent(new Position('b',6, new Quadrilateral(60 * 1, 60 * 3, 60 * 2, 60 * 3, 60 * 2, 60 * 2, 60 * 1, 60 * 2)));
        m_lstPosition.add(new PositionAgent(new Position('b',7, new Quadrilateral(60 * 1, 60 * 2, 60 * 2, 60 * 2, 60 * 2, 60 * 1, 60 * 1, 60 * 1)));
        m_lstPosition.add(new PositionAgent(new Position('b',8, new Quadrilateral(60 * 1, 60 * 1, 60 * 2, 60 * 1, 60 * 2, 60 * 0, 60 * 1, 60 * 0)));
        
        m_lstPosition.add(new PositionAgent(new Position('c',1, new Quadrilateral(60 * 2, 60 * 8, 60 * 3, 60 * 8, 60 * 3, 60 * 7, 60 * 2, 60 * 7)));
        m_lstPosition.add(new PositionAgent(new Position('c',2, new Quadrilateral(60 * 2, 60 * 7, 60 * 3, 60 * 7, 60 * 3, 60 * 6, 60 * 2, 60 * 6)));
        m_lstPosition.add(new PositionAgent(new Position('c',3, new Quadrilateral(60 * 2, 60 * 6, 60 * 3, 60 * 6, 60 * 3, 60 * 5, 60 * 2, 60 * 5)));
        m_lstPosition.add(new PositionAgent(new Position('c',4, new Quadrilateral(60 * 2, 60 * 5, 60 * 3, 60 * 5, 60 * 3, 60 * 4, 60 * 2, 60 * 4)));
        m_lstPosition.add(new PositionAgent(new Position('c',5, new Quadrilateral(60 * 2, 60 * 4, 60 * 3, 60 * 4, 60 * 3, 60 * 3, 60 * 2, 60 * 3)));
        m_lstPosition.add(new PositionAgent(new Position('c',6, new Quadrilateral(60 * 2, 60 * 3, 60 * 3, 60 * 3, 60 * 3, 60 * 2, 60 * 2, 60 * 2)));
        m_lstPosition.add(new PositionAgent(new Position('c',7, new Quadrilateral(60 * 2, 60 * 2, 60 * 3, 60 * 2, 60 * 3, 60 * 1, 60 * 2, 60 * 1)));
        m_lstPosition.add(new PositionAgent(new Position('c',8, new Quadrilateral(60 * 2, 60 * 1, 60 * 3, 60 * 1, 60 * 3, 60 * 0, 60 * 2, 60 * 0)));
        
        m_lstPosition.add(new PositionAgent(new Position('d',1, new Quadrilateral(60 * 3, 60 * 8, 60 * 4, 60 * 8, 60 * 4, 60 * 7, 60 * 3, 60 * 7)));
        m_lstPosition.add(new PositionAgent(new Position('d',2, new Quadrilateral(60 * 3, 60 * 7, 60 * 4, 60 * 7, 60 * 4, 60 * 6, 60 * 3, 60 * 6)));
        m_lstPosition.add(new PositionAgent(new Position('d',3, new Quadrilateral(60 * 3, 60 * 6, 60 * 4, 60 * 6, 60 * 4, 60 * 5, 60 * 3, 60 * 5)));
        m_lstPosition.add(new PositionAgent(new Position('d',4, new Quadrilateral(60 * 3, 60 * 5, 60 * 4, 60 * 5, 60 * 4, 60 * 4, 60 * 3, 60 * 4)));
        m_lstPosition.add(new PositionAgent(new Position('d',5, new Quadrilateral(60 * 3, 60 * 4, 60 * 4, 60 * 4, 60 * 4, 60 * 3, 60 * 3, 60 * 3)));
        m_lstPosition.add(new PositionAgent(new Position('d',6, new Quadrilateral(60 * 3, 60 * 3, 60 * 4, 60 * 3, 60 * 4, 60 * 2, 60 * 3, 60 * 2)));
        m_lstPosition.add(new PositionAgent(new Position('d',7, new Quadrilateral(60 * 3, 60 * 2, 60 * 4, 60 * 2, 60 * 4, 60 * 1, 60 * 3, 60 * 1)));
        m_lstPosition.add(new PositionAgent(new Position('d',8, new Quadrilateral(60 * 3, 60 * 1, 60 * 4, 60 * 1, 60 * 4, 60 * 0, 60 * 3, 60 * 0)));
        
        m_lstPosition.add(new PositionAgent(new Position('e',1, new Quadrilateral(60 * 4, 60 * 8, 60 * 5, 60 * 8, 60 * 5, 60 * 7, 60 * 4, 60 * 7)));
        m_lstPosition.add(new PositionAgent(new Position('e',2, new Quadrilateral(60 * 4, 60 * 7, 60 * 5, 60 * 7, 60 * 5, 60 * 6, 60 * 4, 60 * 6)));
        m_lstPosition.add(new PositionAgent(new Position('e',3, new Quadrilateral(60 * 4, 60 * 6, 60 * 5, 60 * 6, 60 * 5, 60 * 5, 60 * 4, 60 * 5)));
        m_lstPosition.add(new PositionAgent(new Position('e',4, new Quadrilateral(60 * 4, 60 * 5, 60 * 5, 60 * 5, 60 * 5, 60 * 4, 60 * 4, 60 * 4)));
        m_lstPosition.add(new PositionAgent(new Position('e',5, new Quadrilateral(60 * 4, 60 * 4, 60 * 5, 60 * 4, 60 * 5, 60 * 3, 60 * 4, 60 * 3)));
        m_lstPosition.add(new PositionAgent(new Position('e',6, new Quadrilateral(60 * 4, 60 * 3, 60 * 5, 60 * 3, 60 * 5, 60 * 2, 60 * 4, 60 * 2)));
        m_lstPosition.add(new PositionAgent(new Position('e',7, new Quadrilateral(60 * 4, 60 * 2, 60 * 5, 60 * 2, 60 * 5, 60 * 1, 60 * 4, 60 * 1)));
        m_lstPosition.add(new PositionAgent(new Position('e',8, new Quadrilateral(60 * 4, 60 * 1, 60 * 5, 60 * 1, 60 * 5, 60 * 0, 60 * 4, 60 * 0)));
        
        m_lstPosition.add(new PositionAgent(new Position('f',1, new Quadrilateral(60 * 5, 60 * 8, 60 * 6, 60 * 8, 60 * 6, 60 * 7, 60 * 5, 60 * 7)));
        m_lstPosition.add(new PositionAgent(new Position('f',2, new Quadrilateral(60 * 5, 60 * 7, 60 * 6, 60 * 7, 60 * 6, 60 * 6, 60 * 5, 60 * 6)));
        m_lstPosition.add(new PositionAgent(new Position('f',3, new Quadrilateral(60 * 5, 60 * 6, 60 * 6, 60 * 6, 60 * 6, 60 * 5, 60 * 5, 60 * 5)));
        m_lstPosition.add(new PositionAgent(new Position('f',4, new Quadrilateral(60 * 5, 60 * 5, 60 * 6, 60 * 5, 60 * 6, 60 * 4, 60 * 5, 60 * 4)));
        m_lstPosition.add(new PositionAgent(new Position('f',5, new Quadrilateral(60 * 5, 60 * 4, 60 * 6, 60 * 4, 60 * 6, 60 * 3, 60 * 5, 60 * 3)));
        m_lstPosition.add(new PositionAgent(new Position('f',6, new Quadrilateral(60 * 5, 60 * 3, 60 * 6, 60 * 3, 60 * 6, 60 * 2, 60 * 5, 60 * 2)));
        m_lstPosition.add(new PositionAgent(new Position('f',7, new Quadrilateral(60 * 5, 60 * 2, 60 * 6, 60 * 2, 60 * 6, 60 * 1, 60 * 5, 60 * 1)));
        m_lstPosition.add(new PositionAgent(new Position('f',8, new Quadrilateral(60 * 5, 60 * 1, 60 * 6, 60 * 1, 60 * 6, 60 * 0, 60 * 5, 60 * 0)));
        
        m_lstPosition.add(new PositionAgent(new Position('g',1, new Quadrilateral(60 * 6, 60 * 8, 60 * 7, 60 * 8, 60 * 7, 60 * 7, 60 * 6, 60 * 7)));
        m_lstPosition.add(new PositionAgent(new Position('g',2, new Quadrilateral(60 * 6, 60 * 7, 60 * 7, 60 * 7, 60 * 7, 60 * 6, 60 * 6, 60 * 6)));
        m_lstPosition.add(new PositionAgent(new Position('g',3, new Quadrilateral(60 * 6, 60 * 6, 60 * 7, 60 * 6, 60 * 7, 60 * 5, 60 * 6, 60 * 5)));
        m_lstPosition.add(new PositionAgent(new Position('g',4, new Quadrilateral(60 * 6, 60 * 5, 60 * 7, 60 * 5, 60 * 7, 60 * 4, 60 * 6, 60 * 4)));
        m_lstPosition.add(new PositionAgent(new Position('g',5, new Quadrilateral(60 * 6, 60 * 4, 60 * 7, 60 * 4, 60 * 7, 60 * 3, 60 * 6, 60 * 3)));
        m_lstPosition.add(new PositionAgent(new Position('g',6, new Quadrilateral(60 * 6, 60 * 3, 60 * 7, 60 * 3, 60 * 7, 60 * 2, 60 * 6, 60 * 2)));
        m_lstPosition.add(new PositionAgent(new Position('g',7, new Quadrilateral(60 * 6, 60 * 2, 60 * 7, 60 * 2, 60 * 7, 60 * 1, 60 * 6, 60 * 1)));
        m_lstPosition.add(new PositionAgent(new Position('g',8, new Quadrilateral(60 * 6, 60 * 1, 60 * 7, 60 * 1, 60 * 7, 60 * 0, 60 * 6, 60 * 0)));
        
        m_lstPosition.add(new PositionAgent(new Position('h',1, new Quadrilateral(60 * 7, 60 * 8, 60 * 8, 60 * 8, 60 * 8, 60 * 7, 60 * 7, 60 * 7)));
        m_lstPosition.add(new PositionAgent(new Position('h',2, new Quadrilateral(60 * 7, 60 * 7, 60 * 8, 60 * 7, 60 * 8, 60 * 6, 60 * 7, 60 * 6)));
        m_lstPosition.add(new PositionAgent(new Position('h',3, new Quadrilateral(60 * 7, 60 * 6, 60 * 8, 60 * 6, 60 * 8, 60 * 5, 60 * 7, 60 * 5)));
        m_lstPosition.add(new PositionAgent(new Position('h',4, new Quadrilateral(60 * 7, 60 * 5, 60 * 8, 60 * 5, 60 * 8, 60 * 4, 60 * 7, 60 * 4)));
        m_lstPosition.add(new PositionAgent(new Position('h',5, new Quadrilateral(60 * 7, 60 * 4, 60 * 8, 60 * 4, 60 * 8, 60 * 3, 60 * 7, 60 * 3)));
        m_lstPosition.add(new PositionAgent(new Position('h',6, new Quadrilateral(60 * 7, 60 * 3, 60 * 8, 60 * 3, 60 * 8, 60 * 2, 60 * 7, 60 * 2)));
        m_lstPosition.add(new PositionAgent(new Position('h',7, new Quadrilateral(60 * 7, 60 * 2, 60 * 8, 60 * 2, 60 * 8, 60 * 1, 60 * 7, 60 * 1)));
        m_lstPosition.add(new PositionAgent(new Position('h',8, new Quadrilateral(60 * 7, 60 * 1, 60 * 8, 60 * 1, 60 * 8, 60 * 0, 60 * 7, 60 * 0)));

    	((Position)getPosition("a1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a2")));
    	((Position)getPosition("a1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b1")));
    	((Position)getPosition("a1").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b2")));

    	((Position)getPosition("a2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a1")));
    	((Position)getPosition("a2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b2")));
    	((Position)getPosition("a2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a3")));
    	((Position)getPosition("a2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b1")));
    	((Position)getPosition("a2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b3")));

    	((Position)getPosition("a3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a2")));
    	((Position)getPosition("a3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b3")));
    	((Position)getPosition("a3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a4")));
    	((Position)getPosition("a3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b2")));
    	((Position)getPosition("a3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b5")));
    	
    	((Position)getPosition("a4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a3")));
    	((Position)getPosition("a4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b4")));
    	((Position)getPosition("a4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a5")));
    	((Position)getPosition("a4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b3")));
    	((Position)getPosition("a4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b5")));
    	
    	((Position)getPosition("a5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a4")));
    	((Position)getPosition("a5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b5")));
    	((Position)getPosition("a5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a6")));
    	((Position)getPosition("a5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b4")));
    	((Position)getPosition("a5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b5")));

    	((Position)getPosition("a6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a5")));
    	((Position)getPosition("a6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b6")));
    	((Position)getPosition("a6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a7")));
    	((Position)getPosition("a6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b5")));
    	((Position)getPosition("a6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b7")));

    	((Position)getPosition("a7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a6")));
    	((Position)getPosition("a7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b7")));
    	((Position)getPosition("a7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a8")));
    	((Position)getPosition("a7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b6")));
    	((Position)getPosition("a7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b8")));

    	((Position)getPosition("a8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a7")));
    	((Position)getPosition("a8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b8")));
    	((Position)getPosition("a8").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b7")));


    	// File b
    	((Position)getPosition("b1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a1")));
    	((Position)getPosition("b1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b2")));
    	((Position)getPosition("b1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c1")));
    	((Position)getPosition("b1").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("a2")));
    	((Position)getPosition("b1").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c2")));

    	((Position)getPosition("b2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a2")));
    	((Position)getPosition("b2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b1")));
    	((Position)getPosition("b2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c2")));
    	((Position)getPosition("b2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b3")));
    	((Position)getPosition("b2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("a1")));
    	((Position)getPosition("b2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c1")));
    	((Position)getPosition("b2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("a3")));
    	((Position)getPosition("b2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c3")));

    	((Position)getPosition("b3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a3")));
    	((Position)getPosition("b3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b2")));
    	((Position)getPosition("b3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c3")));
    	((Position)getPosition("b3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b4")));
    	((Position)getPosition("b3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("a2")));
    	((Position)getPosition("b3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c2")));
    	((Position)getPosition("b3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("a4")));
    	((Position)getPosition("b3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c4")));
    	
    	((Position)getPosition("b4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a4")));
    	((Position)getPosition("b4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b3")));
    	((Position)getPosition("b4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c4")));
    	((Position)getPosition("b4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b5")));
    	((Position)getPosition("b4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("a3")));
    	((Position)getPosition("b4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c3")));
    	((Position)getPosition("b4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("a5")));
    	((Position)getPosition("b4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c5")));

    	((Position)getPosition("b5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a5")));
    	((Position)getPosition("b5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b4")));
    	((Position)getPosition("b5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c5")));
    	((Position)getPosition("b5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b6")));
    	((Position)getPosition("b5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("a4")));
    	((Position)getPosition("b5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c4")));
    	((Position)getPosition("b5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("a6")));
    	((Position)getPosition("b5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c6")));

    	((Position)getPosition("b6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a6")));
    	((Position)getPosition("b6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b5")));
    	((Position)getPosition("b6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c6")));
    	((Position)getPosition("b6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b7")));
    	((Position)getPosition("b6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("a5")));
    	((Position)getPosition("b6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c5")));
    	((Position)getPosition("b6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("a7")));
    	((Position)getPosition("b6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c7")));

    	((Position)getPosition("b7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a7")));
    	((Position)getPosition("b7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b6")));
    	((Position)getPosition("b7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c7")));
    	((Position)getPosition("b7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b8")));
    	((Position)getPosition("b7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("a6")));
    	((Position)getPosition("b7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c6")));
    	((Position)getPosition("b7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("a8")));
    	((Position)getPosition("b7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c8")));

    	((Position)getPosition("b8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("a8")));
    	((Position)getPosition("b8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b7")));
    	((Position)getPosition("b8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c8")));
    	((Position)getPosition("b8").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("a7")));
    	((Position)getPosition("b8").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c7")));
    	
    	// c
    	((Position)getPosition("c1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b1")));
    	((Position)getPosition("c1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c2")));
    	((Position)getPosition("c1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d1")));
    	((Position)getPosition("c1").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b2")));
    	((Position)getPosition("c1").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d2")));

    	((Position)getPosition("c2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b2")));
    	((Position)getPosition("c2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c1")));
    	((Position)getPosition("c2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d2")));
    	((Position)getPosition("c2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c3")));
    	((Position)getPosition("c2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b1")));
    	((Position)getPosition("c2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d1")));
    	((Position)getPosition("c2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b3")));
    	((Position)getPosition("c2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d3")));

    	((Position)getPosition("c3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b3")));
    	((Position)getPosition("c3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c2")));
    	((Position)getPosition("c3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d3")));
    	((Position)getPosition("c3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c4")));
    	((Position)getPosition("c3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b2")));
    	((Position)getPosition("c3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d2")));
    	((Position)getPosition("c3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b4")));
    	((Position)getPosition("c3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d4")));
    	
    	((Position)getPosition("c4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b4")));
    	((Position)getPosition("c4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c3")));
    	((Position)getPosition("c4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d4")));
    	((Position)getPosition("c4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c5")));
    	((Position)getPosition("c4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b3")));
    	((Position)getPosition("c4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d3")));
    	((Position)getPosition("c4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b5")));
    	((Position)getPosition("c4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d5")));

    	((Position)getPosition("c5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b5")));
    	((Position)getPosition("c5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c4")));
    	((Position)getPosition("c5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d5")));
    	((Position)getPosition("c5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c6")));
    	((Position)getPosition("c5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b4")));
    	((Position)getPosition("c5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d4")));
    	((Position)getPosition("c5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b6")));
    	((Position)getPosition("c5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d6")));

    	((Position)getPosition("c6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b6")));
    	((Position)getPosition("c6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c5")));
    	((Position)getPosition("c6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d6")));
    	((Position)getPosition("c6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c7")));
    	((Position)getPosition("c6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b5")));
    	((Position)getPosition("c6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d5")));
    	((Position)getPosition("c6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b7")));
    	((Position)getPosition("c6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d7")));

    	((Position)getPosition("c7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b7")));
    	((Position)getPosition("c7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c6")));
    	((Position)getPosition("c7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d7")));
    	((Position)getPosition("c7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c8")));
    	((Position)getPosition("c7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b6")));
    	((Position)getPosition("c7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d6")));
    	((Position)getPosition("c7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b8")));
    	((Position)getPosition("c7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d8")));

    	((Position)getPosition("c8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b8")));
    	((Position)getPosition("c8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c7")));
    	((Position)getPosition("c8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("b8")));
    	((Position)getPosition("c8").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("b7")));
    	((Position)getPosition("c8").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d7")));
    	
    	// d
    	((Position)getPosition("d1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c1")));
    	((Position)getPosition("d1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d2")));
    	((Position)getPosition("d1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e1")));
    	((Position)getPosition("d1").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c2")));
    	((Position)getPosition("d1").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e2")));

    	((Position)getPosition("d2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c2")));
    	((Position)getPosition("d2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d1")));
    	((Position)getPosition("d2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e2")));
    	((Position)getPosition("d2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d3")));
    	((Position)getPosition("d2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c1")));
    	((Position)getPosition("d2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e1")));
    	((Position)getPosition("d2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c3")));
    	((Position)getPosition("d2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e3")));

    	((Position)getPosition("d3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c3")));
    	((Position)getPosition("d3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d2")));
    	((Position)getPosition("d3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e3")));
    	((Position)getPosition("d3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d4")));
    	((Position)getPosition("d3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c2")));
    	((Position)getPosition("d3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e2")));
    	((Position)getPosition("d3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c4")));
    	((Position)getPosition("d3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e4")));
    	
    	((Position)getPosition("d4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c4")));
    	((Position)getPosition("d4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d3")));
    	((Position)getPosition("d4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e4")));
    	((Position)getPosition("d4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d5")));
    	((Position)getPosition("d4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c3")));
    	((Position)getPosition("d4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e3")));
    	((Position)getPosition("d4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c5")));
    	((Position)getPosition("d4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e5")));

    	((Position)getPosition("d5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c5")));
    	((Position)getPosition("d5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d4")));
    	((Position)getPosition("d5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e5")));
    	((Position)getPosition("d5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d6")));
    	((Position)getPosition("d5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c4")));
    	((Position)getPosition("d5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e4")));
    	((Position)getPosition("d5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c6")));
    	((Position)getPosition("d5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e6")));

    	((Position)getPosition("d6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c6")));
    	((Position)getPosition("d6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d5")));
    	((Position)getPosition("d6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e6")));
    	((Position)getPosition("d6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d7")));
    	((Position)getPosition("d6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c5")));
    	((Position)getPosition("d6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e5")));
    	((Position)getPosition("d6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c7")));
    	((Position)getPosition("d6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e7")));

    	((Position)getPosition("d7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c7")));
    	((Position)getPosition("d7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d6")));
    	((Position)getPosition("d7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e7")));
    	((Position)getPosition("d7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d8")));
    	((Position)getPosition("d7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c6")));
    	((Position)getPosition("d7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e6")));
    	((Position)getPosition("d7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c8")));
    	((Position)getPosition("d7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e8")));

    	((Position)getPosition("d8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c8")));
    	((Position)getPosition("d8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d7")));
    	((Position)getPosition("d8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("c8")));
    	((Position)getPosition("d8").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("c7")));
    	((Position)getPosition("d8").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e7")));

    	// e
    	((Position)getPosition("e1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d1")));
    	((Position)getPosition("e1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e2")));
    	((Position)getPosition("e1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f1")));
    	((Position)getPosition("e1").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d2")));
    	((Position)getPosition("e1").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f2")));

    	((Position)getPosition("e2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d2")));
    	((Position)getPosition("e2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e1")));
    	((Position)getPosition("e2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f2")));
    	((Position)getPosition("e2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e3")));
    	((Position)getPosition("e2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d1")));
    	((Position)getPosition("e2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f1")));
    	((Position)getPosition("e2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d3")));
    	((Position)getPosition("e2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f3")));

    	((Position)getPosition("e3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d3")));
    	((Position)getPosition("e3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e2")));
    	((Position)getPosition("e3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f3")));
    	((Position)getPosition("e3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e4")));
    	((Position)getPosition("e3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d2")));
    	((Position)getPosition("e3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f2")));
    	((Position)getPosition("e3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d4")));
    	((Position)getPosition("e3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f4")));
    	
    	((Position)getPosition("e4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d4")));
    	((Position)getPosition("e4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e3")));
    	((Position)getPosition("e4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f4")));
    	((Position)getPosition("e4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e5")));
    	((Position)getPosition("e4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d3")));
    	((Position)getPosition("e4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f3")));
    	((Position)getPosition("e4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d5")));
    	((Position)getPosition("e4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f5")));

    	((Position)getPosition("e5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d5")));
    	((Position)getPosition("e5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e4")));
    	((Position)getPosition("e5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f5")));
    	((Position)getPosition("e5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e6")));
    	((Position)getPosition("e5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d4")));
    	((Position)getPosition("e5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f4")));
    	((Position)getPosition("e5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d6")));
    	((Position)getPosition("e5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f6")));

    	((Position)getPosition("e6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d6")));
    	((Position)getPosition("e6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e5")));
    	((Position)getPosition("e6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f6")));
    	((Position)getPosition("e6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e7")));
    	((Position)getPosition("e6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d5")));
    	((Position)getPosition("e6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f5")));
    	((Position)getPosition("e6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d7")));
    	((Position)getPosition("e6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f7")));

    	((Position)getPosition("e7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d7")));
    	((Position)getPosition("e7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e6")));
    	((Position)getPosition("e7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f7")));
    	((Position)getPosition("e7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e8")));
    	((Position)getPosition("e7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d6")));
    	((Position)getPosition("e7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f6")));
    	((Position)getPosition("e7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d8")));
    	((Position)getPosition("e7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f8")));

    	((Position)getPosition("e8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d8")));
    	((Position)getPosition("e8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e7")));
    	((Position)getPosition("e8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("d8")));
    	((Position)getPosition("e8").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("d7")));
    	((Position)getPosition("e8").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f7")));

    	// f
    	((Position)getPosition("f1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e1")));
    	((Position)getPosition("f1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f2")));
    	((Position)getPosition("f1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g1")));
    	((Position)getPosition("f1").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e2")));
    	((Position)getPosition("f1").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g2")));

    	((Position)getPosition("f2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e2")));
    	((Position)getPosition("f2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f1")));
    	((Position)getPosition("f2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g2")));
    	((Position)getPosition("f2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f3")));
    	((Position)getPosition("f2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e1")));
    	((Position)getPosition("f2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g1")));
    	((Position)getPosition("f2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e3")));
    	((Position)getPosition("f2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g3")));

    	((Position)getPosition("f3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e3")));
    	((Position)getPosition("f3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f2")));
    	((Position)getPosition("f3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g3")));
    	((Position)getPosition("f3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f4")));
    	((Position)getPosition("f3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e2")));
    	((Position)getPosition("f3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g2")));
    	((Position)getPosition("f3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e4")));
    	((Position)getPosition("f3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g4")));
    	
    	((Position)getPosition("f4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e4")));
    	((Position)getPosition("f4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f3")));
    	((Position)getPosition("f4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g4")));
    	((Position)getPosition("f4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f5")));
    	((Position)getPosition("f4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e3")));
    	((Position)getPosition("f4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g3")));
    	((Position)getPosition("f4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e5")));
    	((Position)getPosition("f4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g5")));

    	((Position)getPosition("f5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e5")));
    	((Position)getPosition("f5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f4")));
    	((Position)getPosition("f5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g5")));
    	((Position)getPosition("f5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f6")));
    	((Position)getPosition("f5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e4")));
    	((Position)getPosition("f5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g4")));
    	((Position)getPosition("f5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e6")));
    	((Position)getPosition("f5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g6")));

    	((Position)getPosition("f6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e6")));
    	((Position)getPosition("f6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f5")));
    	((Position)getPosition("f6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g6")));
    	((Position)getPosition("f6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f7")));
    	((Position)getPosition("f6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e5")));
    	((Position)getPosition("f6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g5")));
    	((Position)getPosition("f6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e7")));
    	((Position)getPosition("f6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g7")));

    	((Position)getPosition("f7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e7")));
    	((Position)getPosition("f7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f6")));
    	((Position)getPosition("f7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g7")));
    	((Position)getPosition("f7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f8")));
    	((Position)getPosition("f7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e6")));
    	((Position)getPosition("f7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g6")));
    	((Position)getPosition("f7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e8")));
    	((Position)getPosition("f7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g8")));

    	((Position)getPosition("f8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("e8")));
    	((Position)getPosition("f8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f7")));
    	((Position)getPosition("f8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g8")));
    	((Position)getPosition("f8").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("e7")));
    	((Position)getPosition("f8").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g7")));

    	// g
    	((Position)getPosition("g1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f1")));
    	((Position)getPosition("g1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g2")));
    	((Position)getPosition("g1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h1")));
    	((Position)getPosition("g1").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f2")));
    	((Position)getPosition("g1").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("h2")));

    	((Position)getPosition("g2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f2")));
    	((Position)getPosition("g2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g1")));
    	((Position)getPosition("g2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h2")));
    	((Position)getPosition("g2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g3")));
    	((Position)getPosition("g2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f1")));
    	((Position)getPosition("g2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("h1")));
    	((Position)getPosition("g2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f3")));
    	((Position)getPosition("g2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("h3")));

    	((Position)getPosition("g3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f3")));
    	((Position)getPosition("g3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g2")));
    	((Position)getPosition("g3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h3")));
    	((Position)getPosition("g3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g4")));
    	((Position)getPosition("g3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f2")));
    	((Position)getPosition("g3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("h2")));
    	((Position)getPosition("g3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f4")));
    	((Position)getPosition("g3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("h4")));
    	
    	((Position)getPosition("g4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f4")));
    	((Position)getPosition("g4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g3")));
    	((Position)getPosition("g4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h4")));
    	((Position)getPosition("g4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g5")));
    	((Position)getPosition("g4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f3")));
    	((Position)getPosition("g4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("h3")));
    	((Position)getPosition("g4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f5")));
    	((Position)getPosition("g4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("h5")));

    	((Position)getPosition("g5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f5")));
    	((Position)getPosition("g5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g4")));
    	((Position)getPosition("g5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h5")));
    	((Position)getPosition("g5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g6")));
    	((Position)getPosition("g5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f4")));
    	((Position)getPosition("g5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("h4")));
    	((Position)getPosition("g5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f6")));
    	((Position)getPosition("g5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("h6")));

    	((Position)getPosition("g6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f6")));
    	((Position)getPosition("g6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g5")));
    	((Position)getPosition("g6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h6")));
    	((Position)getPosition("g6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g7")));
    	((Position)getPosition("g6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f5")));
    	((Position)getPosition("g6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("h5")));
    	((Position)getPosition("g6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f7")));
    	((Position)getPosition("g6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("h7")));

    	((Position)getPosition("g7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f7")));
    	((Position)getPosition("g7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g6")));
    	((Position)getPosition("g7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h7")));
    	((Position)getPosition("g7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g8")));
    	((Position)getPosition("g7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f6")));
    	((Position)getPosition("g7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("h6")));
    	((Position)getPosition("g7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f8")));
    	((Position)getPosition("g7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("h8")));

    	((Position)getPosition("g8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("f8")));
    	((Position)getPosition("g8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g7")));
    	((Position)getPosition("g8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h8")));
    	((Position)getPosition("g8").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("f7")));
    	((Position)getPosition("g8").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("h7")));

    	// h
    	((Position)getPosition("h1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g1")));
    	((Position)getPosition("h1").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h2")));
    	((Position)getPosition("h1").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g2")));

    	((Position)getPosition("h2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g2")));
    	((Position)getPosition("h2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h1")));
    	((Position)getPosition("h2").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h3")));
    	((Position)getPosition("h2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g1")));
    	((Position)getPosition("h2").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g3")));

    	((Position)getPosition("h3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g3")));
    	((Position)getPosition("h3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h2")));
    	((Position)getPosition("h3").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h4")));
    	((Position)getPosition("h3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g2")));
    	((Position)getPosition("h3").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g4")));
    	
    	((Position)getPosition("h4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g4")));
    	((Position)getPosition("h4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h3")));
    	((Position)getPosition("h4").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h5")));
    	((Position)getPosition("h4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g3")));
    	((Position)getPosition("h4").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g5")));

    	((Position)getPosition("h5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g5")));
    	((Position)getPosition("h5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h4")));
    	((Position)getPosition("h5").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h6")));
    	((Position)getPosition("h5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g4")));
    	((Position)getPosition("h5").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g6")));

    	((Position)getPosition("h6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g6")));
    	((Position)getPosition("h6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h5")));
    	((Position)getPosition("h6").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h7")));
    	((Position)getPosition("h6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g5")));
    	((Position)getPosition("h6").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g7")));

    	((Position)getPosition("h7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g7")));
    	((Position)getPosition("h7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h6")));
    	((Position)getPosition("h7").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h8")));
    	((Position)getPosition("h7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g6")));
    	((Position)getPosition("h7").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g8")));

    	((Position)getPosition("h8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("g8")));
    	((Position)getPosition("h8").getPosition()).addPath(new Path("E", Direction.EDGE, getPosition("h7")));
    	((Position)getPosition("h8").getPosition()).addPath(new Path("D", Direction.VERTEX, getPosition("g7")));
}
*/
    public void PopulatePositions() {
    	for (Map.Entry<String, Position> entry : m_oBoard.getAllPositions().entrySet()) {
           	m_mpPosition.put(entry.getKey(), new PositionAgent(entry.getValue()));
    	}
    }
    
    public void mapPieces() {
    	for (Map.Entry<String, Player> entry : m_oBoard.getAllPlayers().entrySet()) {
    		for (Map.Entry<String, String> entry2 : m_oBoard.getPlayerMapping(entry.getValue().getName()).entrySet()) {
    			m_mpPosition.get(entry2.getKey()).setPiece(new PieceAgent(m_oBoard.getPiece(entry2.getValue()), entry.getValue()));
    		}
    	}    	
    }
    
    public PositionAgent getPosition(String stName) {
    	return m_mpPosition.get(stName);
    }
    
    public Map<String, PositionAgent> getAllPositions(){
    	return m_mpPosition;
    }
    
    public Player getPlayer(String stName) {
    	return m_oBoard.getAllPlayers().get(stName);
    }
}
