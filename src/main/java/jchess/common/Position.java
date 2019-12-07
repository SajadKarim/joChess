package jchess.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Position implements IPositionData, IPosition {
	private PositionData m_oPosition;
	private Piece m_oPiece;
	
	private Boolean m_bSelected;
	private Boolean m_bMoveCandidacy;
	
	Position() {
		//m_oPiece = new Piece();
		m_oPosition = new PositionData();
		
		m_bSelected = false;
		m_bMoveCandidacy = false;	
	}
	
	//region	
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

	public IPathData getPath(String stName) {
		return m_oPosition.getPath(stName);
	}

	public List<IPathData> getAllPaths(){
		return m_oPosition.getAllPaths();
	}
	//endregion
	
	public PositionData getPosition() {
		return m_oPosition;
	}
	
	private String getOtherNeighbour(String stPathName, String stNeighbour) {
		IPathData oPath = m_oPosition.getPath(stPathName);
		
		if( oPath.getAllNeighbors().size() != 2)
			return null;
		
		if( !oPath.getAllNeighbors().get(0).getName().equals(stNeighbour))
			return oPath.getAllNeighbors().get(0).getName();
		
		if( !oPath.getAllNeighbors().get(1).getName().equals(stNeighbour))
			return oPath.getAllNeighbors().get(1).getName();
		
		return null;
	}
	
	public List<IPosition> tryGetOppositePath( IPosition oPosition) {	
		String stInitiator = null;
		
		Iterator<IPathData> it = m_oPosition.getAllPaths().iterator(); 
	    while( it.hasNext()) {
	    	IPathData oPath = it.next();
	    	if( oPath.doesPositionExist(oPosition.getName())) {
		        stInitiator = oPath.getName();
		        break;
	    	}
	    }
	    
	    if( stInitiator == null)
	    	return null;
   	
		IPathData oPath = m_oPosition.getPath(stInitiator);
		
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
				List<IPosition> lst = new ArrayList<IPosition>();
				
				List<IPosition> temp = this.getPathByName(newA).getPositions();
				
				
				Iterator<IPosition> __it = temp.iterator(); 
			    while( __it.hasNext()) {

			    	lst.add((Position)__it.next());
			    }

				
				return lst;
			}
			
			stInitiatorA = stA;
			stInitiatorB = stB;
			stA = newA;
			stB = newB;
		}
	}

	public void setPiece(Piece oPiece) {
		this.m_oPiece = oPiece;
	}

	public Piece getPiece() {
		return m_oPiece;
	}
	
	public Boolean getSelectState() {
		return m_bSelected;
	}
	public void setSelectState(Boolean oSelect) {
		m_bSelected = oSelect;
	}
	
	public Boolean getMoveCandidacy() {
		return m_bMoveCandidacy;
	}
	public void setMoveCandidacy(Boolean bMoveCandidacy) {
		m_bMoveCandidacy = bMoveCandidacy;
	}
	
	public IPath getPathByName(String stName) {
		return (IPath)m_oPosition.getPath(stName);
	}
	
	public List<IPath> getPaths(){
		return  (List<IPath>)(Object)m_oPosition.getAllPaths();
	}
}
