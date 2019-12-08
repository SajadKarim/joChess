package jchess.ruleengine;

import java.util.HashMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.*;

import org.javatuples.Pair;

import jchess.common.*;
import jchess.common.enumerator.*;

/**
 * The default logic to execute a Rule.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

class DefaultProcesssor implements IRuleProcessor{
	public Map<String,Pair<IPositionAgent, IRuleAgent>> tryFindPossibleCandidateMovePositions(IPositionAgent oPosition) {
		IPieceAgent oPiece = oPosition.getPiece();
		IPlayerAgent oPlayer = oPiece.getPlayer();
		
		if( oPiece == null) {
			return null;
		}
		
		if( oPlayer == null) {
			return null;
		}
		
		Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidateMovePositions = new HashMap<String, Pair<IPositionAgent, IRuleAgent>>();	    	
    	for(IRuleAgent oRule : oPosition.getPiece().getRules()) {
    		oRule.reset();	    		
    		tryFindPossibleCandidateMovePositions(oPlayer, oPiece, oPosition, oRule, mpCandidateMovePositions);			
    	}
    	
    	return mpCandidateMovePositions;
    }

	private void tryFindPossibleCandidateMovePositions(IPlayer oPlayer, IPiece oPiece, IPositionAgent oPosition, IRuleAgent oRule, Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidateMovePositions) {		
		if ( oRule == null)
			return;
	
		for( Map.Entry<String, IPathAgent> entry: oPosition.getAllPathAgents().entrySet()) {
			IPathAgent oPath = entry.getValue();
        	if( oPath.getDirection() != oRule.getDirection())
        		continue;			
		    		
    		Iterator<IPositionAgent> itPosition = oPath.getAllPositionAgents().iterator();
        	while( itPosition.hasNext()) {
        		IPositionAgent oNextPosition = itPosition.next();        		
        		IRuleAgent oNextRule = oRule.clone();
        		
        		tryFindPossibleCandidateMovePositions(oPlayer, oPiece, oNextRule, oPosition, oNextPosition, mpCandidateMovePositions);
        	}
		}		
		oRule.makeRuleDead();		
	}

	private void tryFindPossibleCandidateMovePositions(IPlayer oPlayer, IPiece oPiece, IRuleAgent oRule, IPositionAgent oPosition, IPositionAgent oNextPosition,  Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidateMovePositions) {				
		if( oNextPosition == null) {
			oRule.makeRuleDead();
			return;
		}
		
		if( !validateRule(oPlayer.getBoardMapping(), oRule, oPosition, oNextPosition))
			return;
		
		AtomicReference<Boolean> bIsValidMode = new AtomicReference<Boolean>(false);
		AtomicReference<Boolean> bCanContinue = new AtomicReference<Boolean>(false);
		
		checkForPositionMoveCandidacyAndContinuity(oPlayer, oPiece, oRule, oNextPosition, bIsValidMode, bCanContinue);
		
		if( bIsValidMode.get())
			mpCandidateMovePositions.put(oNextPosition.getName(), new Pair<IPositionAgent, IRuleAgent>(oNextPosition, oRule));
				
		if( !bCanContinue.get()) {
			oRule.makeRuleDead();
			return;
		}
		
		IRuleAgent oNextRule = null;
		if ( (oNextRule = oRule.getNextRule()) == null)
			return;
			
		switch( oNextRule.getManoeuvreStrategy()) {
			case BLINKER:
				tryFindPossibleCandidateMovePositionsUsingBlinkers(oPlayer, oPiece, oNextRule, oPosition, oNextPosition, mpCandidateMovePositions);
			break;
			case FILE_AND_RANK:
				tryFindPossibleCandidateMovePositions(oPlayer, oPiece, oNextPosition, oNextRule, mpCandidateMovePositions);
			break;
			default:
			break;
		}
		
    	oRule.makeRuleDead();
	}


	private void tryFindPossibleCandidateMovePositionsUsingBlinkers(IPlayer oPlayer, IPiece oPiece, IRuleAgent oRule, IPositionAgent oPosition, IPositionAgent oNextPosition,  Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidateMovePositions) {				
		if( oNextPosition == null) {
			oRule.makeRuleDead();
			return;
		}
				
		List<IPositionAgent> lstPosition = oNextPosition.tryGetOppositePath(oPosition);
		if( lstPosition == null) {
			oRule.makeRuleDead();
			return;
		}
	
		Iterator<IPositionAgent> itPosition = lstPosition.iterator();
		while( itPosition.hasNext()) {		
			IRuleAgent oNextRule = oRule.clone();
			IPositionAgent _abc =  itPosition.next();
			
			tryFindPossibleCandidateMovePositions(oPlayer, oPiece, oNextRule, oNextPosition, _abc, mpCandidateMovePositions);
		    		
		    oRule.makeRuleDead();
		}
    	oRule.makeRuleDead();
	}
	
	public static Boolean validateRule(IBoardMapping oBoardMapping, IRuleAgent oRule, IPositionAgent oPosition, IPositionAgent oNextPosition) {		
		
		String stCategorySource = oPosition.getCategory().toUpperCase();
		String stCategoryDestination = oNextPosition.getCategory().toUpperCase();

		Family enFamily = oRule.getFamily();
		switch( enFamily) {
		case DIFFERENT:
			if( stCategorySource.equals(stCategoryDestination))
				return false;
			break;
		case SAME:
			if( !stCategorySource.equals(stCategoryDestination))
				return false;
			break;
		case IGNORE:
			break;
		default:
			return false;
		}
		
		int nFileSource = oBoardMapping.getMapping(oPosition.getFile() );
		int nFileDestination = oBoardMapping.getMapping(oNextPosition.getFile() );
		
		File enFile = oRule.getFile();
		switch( enFile) {
		case SAME:
			if( nFileSource != nFileDestination)
				return false;
			break;
		case FORWARD:
			if( nFileDestination <= nFileSource)
				return false;
			break;
		case BACKWARD:
			if( nFileDestination >= nFileSource)
				return false;
			break;
		case IGNORE:
		default:
			break;
		}

		int nRankSource = oBoardMapping.getMapping(oPosition.getRank() );
		int nRankDestination = oBoardMapping.getMapping(oNextPosition.getRank() );

		Rank enRank = oRule.getRank();
		switch( enRank) {
		case SAME:
			if( nRankSource != nRankDestination)
				return false;
			break;
		case FORWARD:
			if( nRankDestination <= nRankSource)
				return false;
			break;
		case BACKWARD:
			if( nRankDestination >= nRankSource)
				return false;
			break;
		case IGNORE:
		default:
			break;
		}
	
		return true;
	}
	
	private void checkForPositionMoveCandidacyAndContinuity(IPlayer oPlayer, IPiece oPiece, IRule oRule, IPositionAgent oCandidacyPosition, AtomicReference<Boolean> bIsValidMode, AtomicReference<Boolean> bCanContinue) {
		bIsValidMode.set(false);
		bCanContinue.set(false);
		
		switch( oRule.getRuleType()) {
			case MOVE_TRANSIENT:
				bIsValidMode.set(false);
				bCanContinue.set(true);
				break;
			case MOVE:
				if( oCandidacyPosition.getPiece() != null) {
					bIsValidMode.set(false);
					bCanContinue.set(false);
				} else {
					bIsValidMode.set(true);
					bCanContinue.set(true);
				}
				break;
			case MOVE_AND_CAPTURE:
				if( oCandidacyPosition.getPiece() == null) {
					bIsValidMode.set(true);
					bCanContinue.set(true);
				} else if( oCandidacyPosition.getPiece().getPlayer() == oPlayer) {
					bIsValidMode.set(false);
					bCanContinue.set(false);
				} else if( oCandidacyPosition.getPiece().getPlayer() != oPlayer) {
					bIsValidMode.set(true);
					bCanContinue.set(false);
				}
				break;
			case MOVE_IFF_CAPTURE_POSSIBLE:
				if( oCandidacyPosition.getPiece() == null) {
					bIsValidMode.set(false);
					bCanContinue.set(true);
				} else if( oCandidacyPosition.getPiece().getPlayer() == oPlayer) {
					bIsValidMode.set(false);
					bCanContinue.set(false);
				} else if( oCandidacyPosition.getPiece().getPlayer() != oPlayer) {
					bIsValidMode.set(true);
					bCanContinue.set(false);
				}
				break;
		}
	}
}
