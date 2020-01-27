package jchess.gamelogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jchess.cache.PositionData;
import jchess.common.IBoardMapping;
import jchess.common.IPath;
import jchess.common.IPathAgent;
import jchess.common.IPieceAgent;
import jchess.common.IPosition;
import jchess.common.IPositionAgent;
import jchess.common.IPositionData;
import jchess.common.IShape;
import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Rank;

/**
 * This class is responsible to manage underlying "Position" (only) related data.
 * It keeps the state of the Position and facilitates game-logic with numerous operations
 * E.g. It lets game-logic about its neighbouring Positions and it also maintains
 *      the current state of the Position (Selected or Marked-for-Candidate-Move).
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public final class PositionAgent implements IPositionAgent {
	private IPositionData m_oPosition;
	private IPieceAgent m_oPiece;
	
	private Boolean m_bSelected;
	private Boolean m_bMoveCandidacy;
	
	PositionAgent() {
		m_bSelected = false;
		m_bMoveCandidacy = false;	
		m_oPosition = new PositionData();
	}
	
	PositionAgent(PositionAgent oPosition) {
		m_bSelected = oPosition.m_bSelected;
		m_bMoveCandidacy = oPosition.m_bMoveCandidacy;	
		m_oPosition = new PositionData((PositionData)oPosition.m_oPosition);
		m_oPiece = new PieceAgent((PieceAgent)oPosition.m_oPiece);
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

	public Map<String, IPath> getAllPaths() {
		return m_oPosition.getAllPaths();
	}
	//endregion
	
	public IPositionData getPosition() {
		return m_oPosition;
	}
	
	// region: Implements IPositionAgent
	/**
	 * This method returns the opposite Position against the provided one.
	 * 
	 *  NW	N	NE
	 * 	W	*	E
	 *  SW	S	WE
	 *  
	 * Consider * is the current Position. 
	 * The Position provided in the argument is the one linked to SW.
	 * This method would return the Position that is linked to NE location (if any).
	 * 
	 */	
	public List<IPositionAgent> tryGetOppositePath(IPositionAgent oPosition) {	
		String stInitiator = null;
		
		for (Map.Entry<String, IPath> entry: m_oPosition.getAllPaths().entrySet()) {
	    	IPath oPath = entry.getValue();
	    	if (oPath.doesPositionExist(oPosition.getName())) {
		        stInitiator = oPath.getName();
		        break;
	    	}
	    }
	    
	    if (stInitiator == null) {
	    	return null;
	    }
   	
		IPath oPath = m_oPosition.getPath(stInitiator);
		
		if (oPath.getAllNeighbors().size() != 2) {
			return null;
		}
		
		String stA = oPath.getAllNeighbors().get(0).getName();
		String stB = oPath.getAllNeighbors().get(1).getName();

		String stInitiatorA = stInitiator;
		String stInitiatorB = stInitiator;
		while (true) {
			String newA = getOtherNeighbour(stA, stInitiatorA);
			String newB = getOtherNeighbour(stB, stInitiatorB);

			if (newA == null || newB == null) {
				return null;
			}
			
			if (newA.equals(newB)) {
				List<IPositionAgent> lstPositions = new ArrayList<IPositionAgent>();
				
				Iterator<IPositionAgent> itPosition = this.getPathByName(newA).getAllPositionAgents().iterator(); 
			    while (itPosition.hasNext()) {
			    	lstPositions.add((PositionAgent)itPosition.next());
			    }
				
				return lstPositions;
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
	
	public Map<String, IPathAgent> getAllPathAgents() {
		return  (Map<String, IPathAgent>)(Object)m_oPosition.getAllPaths();
	}
	// endregion
	
	private String getOtherNeighbour(String stPathName, String stNeighbour) {
		IPath oPath = m_oPosition.getPath(stPathName);
		
		if (oPath.getAllNeighbors().size() != 2) {
			return null;
		}
		
		if (!oPath.getAllNeighbors().get(0).getName().equals(stNeighbour)) {
			return oPath.getAllNeighbors().get(0).getName();
		}
		
		if (!oPath.getAllNeighbors().get(1).getName().equals(stNeighbour)) {
			return oPath.getAllNeighbors().get(1).getName();
		}
		
		return null;
	}

	public Map<String, IPositionAgent> getAllPathAgents(IBoardMapping oBoardMapping, Direction enDirection, Family enFamily, File enFile, Rank enRank) {
		Map<String, IPositionAgent> mpPaths = new HashMap<String, IPositionAgent>();
		for (Map.Entry<String, IPath> itPath : m_oPosition.getAllPaths().entrySet()) {
	    	IPath oPath = itPath.getValue();
	    	
	    	if (oPath.getDirection() != enDirection) {
	    		continue;
	    	}
	    	
	    	Iterator<IPosition> itPosition = oPath.getAllPositions().iterator();
	    	while (itPosition.hasNext()) {
	    		IPositionAgent oNextPosition = (IPositionAgent)itPosition.next();
		    	if (tryValidateRuleApplicability(oBoardMapping, enFamily, enFile, enRank, oNextPosition)) {
		    		mpPaths.put(oNextPosition.getName(), oNextPosition);
		    	}
	    	}
		}

		return mpPaths;
	}
	
	public Boolean tryValidateRuleApplicability(IBoardMapping oBoardMapping, Family enFamily, File enFile, Rank enRank, IPositionAgent oNextPosition) {				
		String stCategorySource = this.getCategory().toUpperCase();
		String stCategoryDestination = oNextPosition.getCategory().toUpperCase();

		switch (enFamily) {
		case DIFFERENT:
			if (stCategorySource.equals(stCategoryDestination)) {
				return false;
			}
			break;
		case SAME:
			if (!stCategorySource.equals(stCategoryDestination)) {
				return false;
			}
			break;
		case IGNORE:
			break;
		default:
			return false;
		}
		
		int nFileSource = oBoardMapping.getMapping(this.getFile());
		int nFileDestination = oBoardMapping.getMapping(oNextPosition.getFile());
		
		switch (enFile) {
		case SAME:
			if (nFileSource != nFileDestination) {
				return false;
			}
			break;
		case FORWARD:
			if (nFileDestination <= nFileSource) {
				return false;
			}
			break;
		case BACKWARD:
			if (nFileDestination >= nFileSource) {
				return false;
			}
			break;
		case IGNORE:
		default:
			break;
		}

		int nRankSource = oBoardMapping.getMapping(this.getRank());
		int nRankDestination = oBoardMapping.getMapping(oNextPosition.getRank());

		switch (enRank) {
		case SAME:
			if (nRankSource != nRankDestination) {
				return false;
			}
			break;
		case FORWARD:
			if (nRankDestination <= nRankSource) {
				return false;
			}
			break;
		case BACKWARD:
			if (nRankDestination >= nRankSource) {
				return false;
			}
			break;
		case IGNORE:
		default:
			break;
		}
	
		return true;
	}

	public IPosition clone() {
		return new PositionAgent(this);
	}
	
	public String toLog() {
		return String.format("Id=%s, Piece=%s", getName(), m_oPiece == null ? "<no piece attached>" : m_oPiece.getName());
	}
}
