package jchess.gamelogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jchess.cache.PositionData;
import jchess.common.IPath;
import jchess.common.IPathAgent;
import jchess.common.IPieceAgent;
import jchess.common.IPositionAgent;
import jchess.common.IPositionData;
import jchess.common.IShape;

/**
 * This class is responsible to manage underlying "Position" (only) related data.
 * It keeps the state of the Position and facilitates game-logic with numerous operations
 * E.g. It lets game-logic about its neighbouring Positions and it also maintains
 *      the current state of the Position (Selected or Marked-for-Candidate-Move).
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class PositionAgent implements IPositionAgent {
	private IPositionData m_oPosition;
	private IPieceAgent m_oPiece;
	
	private Boolean m_bSelected;
	private Boolean m_bMoveCandidacy;
	
	PositionAgent() {
		m_bSelected = false;
		m_bMoveCandidacy = false;	
		m_oPosition = new PositionData();
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

	public IPath getPath(String stName) {
		return m_oPosition.getPath(stName);
	}

	public Map<String, IPath> getAllPaths(){
		return m_oPosition.getAllPaths();
	}
	//endregion
	
	public IPositionData getPosition() {
		return m_oPosition;
	}
	
	// region: Implements IPositionAgent
	public List<IPositionAgent> tryGetOppositePath( IPositionAgent oPosition) {	
		String stInitiator = null;
		
		for( Map.Entry<String, IPath> entry: m_oPosition.getAllPaths().entrySet()) {
		//Iterator<IPath> it = m_oPosition.getAllPaths().iterator(); 
	    //while( it.hasNext()) {
	    	IPath oPath = entry.getValue();// it.next();
	    	if( oPath.doesPositionExist(oPosition.getName())) {
		        stInitiator = oPath.getName();
		        break;
	    	}
	    }
	    
	    if( stInitiator == null)
	    	return null;
   	
		IPath oPath = m_oPosition.getPath(stInitiator);
		
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
				List<IPositionAgent> lst = new ArrayList<IPositionAgent>();
				
				List<IPositionAgent> temp = this.getPathByName(newA).getAllPositionAgents();
				
				
				Iterator<IPositionAgent> __it = temp.iterator(); 
			    while( __it.hasNext()) {

			    	lst.add((PositionAgent)__it.next());
			    }

				
				return lst;
			}
			
			stInitiatorA = stA;
			stInitiatorB = stB;
			stA = newA;
			stB = newB;
		}
	}

	public void setPiece(IPieceAgent oPiece) {
		this.m_oPiece = oPiece;
	}

	public IPieceAgent getPiece() {
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
	
	public IPathAgent getPathByName(String stName) {
		return (IPathAgent)m_oPosition.getPath(stName);
	}
	
	public Map<String, IPathAgent> getAllPathAgents(){
		return  (Map<String, IPathAgent>)(Object)m_oPosition.getAllPaths();
	}
	// endregion
	
	private String getOtherNeighbour(String stPathName, String stNeighbour) {
		IPath oPath = m_oPosition.getPath(stPathName);
		
		if( oPath.getAllNeighbors().size() != 2)
			return null;
		
		if( !oPath.getAllNeighbors().get(0).getName().equals(stNeighbour))
			return oPath.getAllNeighbors().get(0).getName();
		
		if( !oPath.getAllNeighbors().get(1).getName().equals(stNeighbour))
			return oPath.getAllNeighbors().get(1).getName();
		
		return null;
	}
}