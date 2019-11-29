package jchess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.*;
public class RuleEngine {
	public static ArrayList getTryFindPossibleMove( Piece oPiece, IPosition oPosition) {		
    	ArrayList lstPosition = new ArrayList();
    	
    	for( IRule rule : oPiece.m_lstRules) {
    		
    		rule.reset();
    		
    		RULE_TYPE enRuleType = ((Rule)rule).m_enRuleType;
    		switch( enRuleType) {
    		case MOVE:
    		case MOVE_TRANSIENT:	
    			tryFindPossibleMoves(oPiece.player.m_oBoardMapping, lstPosition, (Rule)rule, (Position)oPosition);
    			break;
    		case MOVE_IFF_CAPTURE_POSSIBLE:
    			tryFindPossibleMoves(oPiece.player.m_oBoardMapping, lstPosition, (Rule)rule, (Position)oPosition);
    			//if( ((Position) lstPosition.get(lstPosition.size() -1)).getPiece() == null)
    			//	lstPosition.clear();
    			break;
    		case MOVE_AND_CAPTURE:
    			tryFindPossibleMoves(oPiece.player.m_oBoardMapping, lstPosition, (Rule)rule, (Position)oPosition);
    			break;
    		default:
    			break;
    		}
    	}
    	
    	return lstPosition;
    }

	public static void tryFindPossibleMoves(IBoardMapping oBoardMapping, ArrayList lstPosition, Rule rule, Position position) {		
		if ( rule == null)
			return;

		List<IPosition> positions = null;
    	Iterator<IPosition> it = null;

		DIRECTION enDirection = rule.m_enDirection;
		switch( enDirection) {
		case EDGE:
			positions = position.getEdges();
	    	it = positions.iterator();
	    	while( it.hasNext()) {
	    		Position nextPosition = (Position)it.next();
				tryFindPossibleMoves(oBoardMapping, lstPosition, rule, position, nextPosition);
	    	}

			break;
		case VERTEX:
			positions = position.getVertexes();
	    	it = positions.iterator();
	    	while( it.hasNext()) {
	    		Position nextPosition = (Position)it.next();
				tryFindPossibleMoves(oBoardMapping, lstPosition, rule, position, nextPosition);
	    	}
			break;
		default:
			break;
		}
	}

	public static void tryFindPossibleMoves(IBoardMapping oBoardMapping, ArrayList lstPosition, Rule rule, Position position, Position nextPosition) {		
		FAMILY enFamily = rule.m_enFamily;
		switch( enFamily) {
		case DIFFERENT:
		case ANY:
			break;
		default:
			return;
		}
		
		int nFileSource = oBoardMapping.getMapping(position.getFile() );
		int nFileDestination = oBoardMapping.getMapping(nextPosition.getFile() );
		
		FILE enFile = rule.m_enFile;
		switch( enFile) {
		case SAME:
			if( nFileSource != nFileDestination)
				return;
			break;
		case FORWARD:
			if( nFileDestination <= nFileSource)
				return;
			break;
		case BACKWARD:
			if( nFileDestination >= nFileSource)
				return;
			break;
		default:
			break;
		}

		int nRankSource = oBoardMapping.getMapping(position.getRank() );
		int nRankDestination = oBoardMapping.getMapping(nextPosition.getRank() );

	
		RANK enRank = rule.m_enRank;
		switch( enRank) {
		case SAME:
			if( nRankSource != nRankDestination)
				return;
			break;
		case FORWARD:
			if( nRankDestination <= nRankSource)
				return;
			break;
		case BACKWARD:
			if( nRankDestination >= nRankSource)
				return;
			break;
		default:
			break;
		}

		AtomicReference<Boolean> bIsValidMode = new AtomicReference<Boolean>(false);
		AtomicReference<Boolean> bCanContinue = new AtomicReference<Boolean>(false);
		
		//Boolean bIsValidMode = false, bCanContinue = false;
		
		rule.isValidMove( nextPosition, bIsValidMode, bCanContinue);
			
		if( bIsValidMode.get())
			lstPosition.add(nextPosition);
		
		if( !bCanContinue.get())
			return;
		
		/*if( nextPosition.getPiece() != null) {
			if( rule.canCapture())
				lstPosition.add(nextPosition);
				
			return;
		}
		
		if( rule.canMove())
			lstPosition.add(nextPosition);
		*/
		IRule newrule = null;
		while( (newrule = rule.getNextRule()) != null)
			tryFindPossibleMoves(oBoardMapping, lstPosition, (Rule)newrule, nextPosition);

}

}
