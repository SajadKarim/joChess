package jchess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.*;
import jchess.common.*;
import jchess.common.enumerator.*;
public class RuleEngine {
	public static ArrayList<Position> getTryFindPossibleMove( jchess.common.Piece oPiece, Position oPosition) {		
    	ArrayList lstPosition = new ArrayList();
    	
    	/****for( Rule rule : oPiece.getAllRules()) {
    		
    		rule.reset();
    		
    		tryFindPossibleMoves(new BoardMapping(), lstPosition, (Rule)rule, oPosition);
			
    		/*RULE_TYPE enRuleType = ((Rule)rule).m_enRuleType;
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
    		}*/
    	//}***/
    	
    	return lstPosition;
    }

	public static void tryFindPossibleMoves(IBoardMapping oBoardMapping, ArrayList lstPosition, Rule rule, Position position) {		
		if ( rule == null)
			return;

		/*Map<String, Path> positions = position.getAllPaths();
		Iterator<Map.Entry<String, Path>> it = null;

		Direction enDirection = rule.getDirection();
		switch( enDirection) {
		case EDGE: 
		{			
			it = positions.entrySet().iterator(); 
	        while( it.hasNext()) {
	        	
	        	Map.Entry<String, Path> entry = it.next();
	        	if( ((Path)entry.getValue()).getDirection() != Direction.EDGE)
	        		continue;
	    		
	    		Iterator<PositionData> it2 = entry.getValue().getAllPositions().iterator();
	        	while( it2.hasNext()) {

	        		PositionData __ = it2.next();
	        		
	        		tryFindPossibleMoves(oBoardMapping, lstPosition, new Rule(rule), position, boardManager.getInstance().getPosition(__.getName()));
	        	}
	        	
	        	}
		}

			break;
		case VERTEX:
		{			
			it = positions.entrySet().iterator(); 
	        while( it.hasNext()) {
	        	
	        	Map.Entry<String, Path> entry = it.next();
	        	if( ((Path)entry.getValue()).getDirection()!= Direction.VERTEX)
	        		continue;
	
	    		Iterator<PositionData> it2 = entry.getValue().getAllPositions().iterator();
	        	while( it2.hasNext()) {

	        		PositionData __ = it2.next();
	        		
	        		tryFindPossibleMoves(oBoardMapping, lstPosition, new Rule(rule), position, boardManager.getInstance().getPosition(__.getName()));
	        	}
	        	
	        

	    		/*Iterator<PositionAgent> it2 = entry.getValue().getAllPositions().iterator();
	        	while( it2.hasNext()) {


	        		tryFindPossibleMoves(oBoardMapping, lstPosition, new RuleAgent(rule), position, (it2.next()));
	        	}*/

//	        	tryFindPossibleMoves(oBoardMapping, lstPosition, new Rule((Rule)rule), position, (Position)(((Path)entry.getValue()).m_oPosition));
	    /***	}
		}
			break;
		default:
			break;
		}
		*/
		rule.makeRuleDead();
		
	}

	public static void tryFindPossibleMoves(IBoardMapping oBoardMapping, ArrayList lstPosition, Rule rule, Position position, Position nextPosition) {		
		
		if( nextPosition == null) {
			rule.makeRuleDead();
			return;
		}
		
		if( !validateMove(oBoardMapping, rule, position, nextPosition))
			return;
		
		AtomicReference<Boolean> bIsValidMode = new AtomicReference<Boolean>(false);
		AtomicReference<Boolean> bCanContinue = new AtomicReference<Boolean>(false);
		
		//Boolean bIsValidMode = false, bCanContinue = false;
		
		rule.isValidMove( nextPosition, bIsValidMode, bCanContinue);
			
		if( bIsValidMode.get())
			lstPosition.add(nextPosition);
		
		if( !bCanContinue.get()) {
			rule.makeRuleDead();
			return;
		}
		
		Rule newrule = null;
		if ( (newrule = rule.getNextRule()) == null)
			return;
			
		switch( newrule.getManoeuvreStrategy()) {
		case BLINKER:
			tryFindPossibleMovesUsingBlinkers(oBoardMapping, lstPosition, newrule, position, nextPosition);
			break;
		case FILE_AND_RANK:
			tryFindPossibleMoves(oBoardMapping, lstPosition, newrule, nextPosition);
			break;
		default:
			break;
		}
		
    	rule.makeRuleDead();
}
/*
	public static void tryFindPossibleMovesByFileAndRank(IBoardMapping oBoardMapping, ArrayList lstPosition, Rule rule, Position position, Position nextPosition) {		
		
		if( nextPosition == null) {
			rule.makeRuleDead();
			return;
		}

		if( !validateMove(oBoardMapping, rule, position, nextPosition))
			return;
		
		
				
		IRule newrule = null;
		while( (newrule = rule.getNextRule()) != null)
			tryFindPossibleMoves(oBoardMapping, lstPosition, (Rule)newrule, nextPosition);
    	
    	rule.makeRuleDead();
}
*/
	public static void tryFindPossibleMovesUsingBlinkers(IBoardMapping oBoardMapping, ArrayList lstPosition, Rule rule, Position oldposition, Position newPosition) {		
		
		if( newPosition == null) {
			rule.makeRuleDead();
			return;
		}
				
		List<Position> pos = newPosition.tryGetOppositePath(oldposition);
		if( pos == null) {
			rule.makeRuleDead();
			return;
		}

		/*if( !validateMove(oBoardMapping, rule, oldposition, newPosition))
		return;
	
	AtomicReference<Boolean> bIsValidMode = new AtomicReference<Boolean>(false);
	AtomicReference<Boolean> bCanContinue = new AtomicReference<Boolean>(false);
	
	//Boolean bIsValidMode = false, bCanContinue = false;
	
	rule.isValidMove( newPosition, bIsValidMode, bCanContinue);
		
	if( bIsValidMode.get())
		lstPosition.add(newPosition);
	
	if( !bCanContinue.get()) {
		rule.makeRuleDead();
		return;
	}
	*/

		Iterator<Position> it = pos.iterator();
    	while( it.hasNext()) {

    		Rule _rule = rule.clone();
    		
    		Rule newrule = null;
    		Position _abc =  it.next();
    		//while( (newrule = _rule.getNextRule()) != null)
    			tryFindPossibleMoves(oBoardMapping, lstPosition, (Rule)_rule, newPosition, _abc);
    		
    		((Rule)_rule).makeRuleDead();
    	}
    	
    	rule.makeRuleDead();
}

	public static Boolean validateMove(IBoardMapping oBoardMapping, Rule rule, Position position, Position nextPosition) {		
		
		String stCategorySource = position.getCategory().toUpperCase();
		String stCategoryDestination = nextPosition.getCategory().toUpperCase();

		Family enFamily = rule.getFamily();
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
		
		int nFileSource = oBoardMapping.getMapping(position.getFile() );
		int nFileDestination = oBoardMapping.getMapping(nextPosition.getFile() );
		
		File enFile = rule.getFile();
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

		int nRankSource = oBoardMapping.getMapping(position.getRank() );
		int nRankDestination = oBoardMapping.getMapping(nextPosition.getRank() );

	
		Rank enRank = rule.getRank();
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
}
