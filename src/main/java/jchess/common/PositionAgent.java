package jchess.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jchess.boardManager;

public class PositionAgent implements IPosition {
	private Position m_oPosition;
	private PieceAgent m_oPiece;

	//region
	public PositionAgent(Position oPosition/*, PieceAgent oPiece*/) {
		m_oPosition = oPosition;
		//m_oPiece = oPiece;
	}
	
	public PositionAgent(PositionAgent oPositionAgent) {
		m_oPosition = new Position(oPositionAgent.getPosition());
		m_oPiece = new PieceAgent(oPositionAgent.getPiece());
	}
	
	public int getFile() {
		return m_oPosition.getFile();
	}
	
	public int getRank() {
		return m_oPosition.getRank();
	}
	
	public String getName() {
		return m_oPosition.getName();
	}

	public String getCategory() {
		return m_oPosition.getCategory();
	}

	public IShape getShape() {
		return m_oPosition.getShape();
	}

	public Map<String, Path> getAllPaths(){
		return m_oPosition.getAllPaths();
	}
	
	public Path getPath(String stName) {
		return m_oPosition.getPath(stName);
	}
	//endregion
	
	public Position getPosition() {
		return m_oPosition;
	}
	
	private String getOtherNeighbour(String stPathName, String stNeighbour) {
		IPath oPath = m_oPosition.getAllPaths().get(stPathName);
		
		if( oPath.getAllNeighbors().size() != 2)
			return null;
		
		if( !oPath.getAllNeighbors().get(0).getName().equals(stNeighbour))
			return oPath.getAllNeighbors().get(0).getName();
		
		if( !oPath.getAllNeighbors().get(1).getName().equals(stNeighbour))
			return oPath.getAllNeighbors().get(1).getName();
		
		return null;
	}
	
	public List<PositionAgent> tryGetOppositePath( IPosition oPosition) {	
		String stInitiator = null;
		
		Iterator<Map.Entry<String, Path>> it = m_oPosition.getAllPaths().entrySet().iterator(); 
	    while( it.hasNext()) {

	    	Map.Entry<String, Path> entry = it.next();
	    	if( entry.getValue().doesPositionExist(oPosition.getName())) {
		        stInitiator = entry.getKey();
		        break;
	    	}
	    }
	    
	    if( stInitiator == null)
	    	return null;
   	
		IPath oPath = m_oPosition.getAllPaths().get(stInitiator);
		
		if( oPath.getAllNeighbors().size() != 2)
			return null;
		
		String stA = oPath.getAllNeighbors().get(0).getName();
		String stB = oPath.getAllNeighbors().get(1).getName();

		String stInitiatorA = stInitiator;
		String stInitiatorB = stInitiator;
		while( true) {
			String newA = getOtherNeighbour(stA, stInitiatorA);
			String newB = getOtherNeighbour(stB, stInitiatorB);

			if( newA == null || newB == null)
				return null;
			
			if( newA.equals(newB)) {
				List<PositionAgent> lst = new ArrayList<PositionAgent>();
				
				List<Position> temp = m_oPosition.getAllPaths().get(newA).getAllPositions();
				
				
				Iterator<Position> __it = temp.iterator(); 
			    while( __it.hasNext()) {

			    	lst.add(boardManager.getInstance().getPosition(__it.next().getName()));
			    }

				
				return lst;
			}
			
			stInitiatorA = stA;
			stInitiatorB = stB;
			stA = newA;
			stB = newB;
		}
	}

	public void setPiece(PieceAgent oPiece) {
		this.m_oPiece = oPiece;
	}

	public PieceAgent getPiece() {
		return m_oPiece;
	}
}
