package jchess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.*;

import org.javatuples.Pair;

import jchess.common.IPath;
import jchess.common.IPosition;
import jchess.common.IRule;
import jchess.common.enumerator.*;
public class RuleEngine {
	public static Map<String,Pair<IPosition, IRule>> tryFindPossibleMove( IPosition oPosition) {		
		Map<String,Pair<IPosition, IRule>> lstPosition = new HashMap<String, Pair<IPosition, IRule>>();
    	
    	for( IRule oRule : oPosition.getPiece().getRules()) {
    		
    		oRule.reset();
    		
    		tryFindPossibleMoves(new BoardMapping(), lstPosition, oRule, oPosition);			
    	}
    	
    	return lstPosition;
    }

	public static void tryFindPossibleMoves(IBoardMapping oBoardMapping, Map<String,Pair<IPosition, IRule>> lstPosition, IRule oRule, IPosition oPosition) {		
		if ( oRule == null)
			return;

		Iterator<IPath> it = oPosition.getPaths().iterator();

		Direction enDirection = oRule.getDirection();
		switch( enDirection) {
		case EDGE: 
		{
			while( it.hasNext()) {
	        	
	        	IPath oPathData = it.next();
	        	if( oPathData.getDirection() != Direction.EDGE)
	        		continue;
	    		
	    		Iterator<IPosition> itt = oPathData.getPositions().iterator();
	        	while( itt.hasNext()) {

	        		IPosition iPosition = itt.next();
	        		
	        		tryFindPossibleMoves(oBoardMapping, lstPosition, oRule.clone(), oPosition, iPosition);
	        	}
	        	
	        	}
		}

			break;
		case VERTEX:
		{			
			while( it.hasNext()) {
	        	
	        	IPath oPathData = it.next();
	        	if( oPathData.getDirection() != Direction.VERTEX)
	        		continue;
	    		
	    		Iterator<IPosition > itt = oPathData.getPositions().iterator();
	        	while( itt.hasNext()) {

	        		IPosition iPosition = itt.next();
	        		
	        		tryFindPossibleMoves(oBoardMapping, lstPosition, oRule.clone(), oPosition, iPosition);
	        	}
	        	
	        	}

			/*it = positions.entrySet().iterator(); 
	        while( it.hasNext()) {
	        	
	        	Map.Entry<String, Path> entry = it.next();
	        	if( ((Path)entry.getValue()).getDirection()!= Direction.VERTEX)
	        		continue;
	
	    		Iterator<PositionData> it2 = entry.getValue().getAllPositions().iterator();
	        	while( it2.hasNext()) {

	        		PositionData __ = it2.next();
	        		
	        		tryFindPossibleMoves(oBoardMapping, lstPosition, new Rule(rule), position, boardManager.getInstance().getPosition(__.getName()));
	        	}
	        */	
	        

	    		/*Iterator<PositionAgent> it2 = entry.getValue().getAllPositions().iterator();
	        	while( it2.hasNext()) {


	        		tryFindPossibleMoves(oBoardMapping, lstPosition, new RuleAgent(rule), position, (it2.next()));
	        	}*/

//	        	tryFindPossibleMoves(oBoardMapping, lstPosition, new Rule((Rule)rule), position, (Position)(((Path)entry.getValue()).m_oPosition));
		}
			break;
		default:
			break;
		}
		
		oRule.makeRuleDead();		
	}

	public static void tryFindPossibleMoves(IBoardMapping oBoardMapping, Map<String,Pair<IPosition, IRule>> lstPosition, IRule rule, IPosition position, IPosition nextPosition) {		
		
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
			lstPosition.put(nextPosition.getName(), new Pair<IPosition, IRule>(nextPosition, rule));
			//lstPosition.add(nextPosition);
		
		if( !bCanContinue.get()) {
			rule.makeRuleDead();
			return;
		}
		
		IRule newrule = null;
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
	public static void tryFindPossibleMovesUsingBlinkers(IBoardMapping oBoardMapping, Map<String,Pair<IPosition, IRule>> lstPosition, IRule rule, IPosition oldposition, IPosition newPosition) {		
		
		if( newPosition == null) {
			rule.makeRuleDead();
			return;
		}
				
		List<IPosition> pos = newPosition.tryGetOppositePath(oldposition);
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

		Iterator<IPosition> it = pos.iterator();
    	while( it.hasNext()) {

    		IRule _rule = rule.clone();
    		
    		IRule newrule = null;
    		IPosition _abc =  it.next();
    		//while( (newrule = _rule.getNextRule()) != null)
    			tryFindPossibleMoves(oBoardMapping, lstPosition, _rule, newPosition, _abc);
    		
    		_rule.makeRuleDead();
    	}
    	
    	rule.makeRuleDead();
}

	public static Boolean validateMove(IBoardMapping oBoardMapping, IRule rule, IPosition position, IPosition nextPosition) {		
		
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
