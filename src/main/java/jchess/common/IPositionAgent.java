package jchess.common;

import java.util.List;
import java.util.Map;

import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Rank;

/**
 * IPositionAgent provides interface for the module where the
 * all the decisions (game) are taken. It ensures that user
 * does not make any changes to underlying data object.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IPositionAgent extends IPosition {
	public IPieceAgent getPiece();
	public Boolean getSelectState();
	public Boolean getMoveCandidacy();
	public Map<String, IPathAgent> getAllPathAgents();
	public void setPiece(IPieceAgent oPiece);
	public void setSelectState(Boolean oSelect);
	public IPathAgent getPathByName(String stName);
	public void setMoveCandidacy(Boolean bMoveCandidacy);
	public List<IPositionAgent> tryGetOppositePath( IPositionAgent oPosition);	
	public Map<String, IPositionAgent> getAllPathAgents(IBoardMapping oBoardMapping, Direction enDirection, Family enFamily, File enFile, Rank enRank);
	public Boolean tryValidateRuleApplicability(IBoardMapping oBoardMapping, Family enFamily, File enFile, Rank enRank, IPositionAgent oNextPosition);	
	public String toLog();
}
