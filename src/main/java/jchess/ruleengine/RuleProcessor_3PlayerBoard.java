package jchess.ruleengine;

import java.util.Map;

import org.javatuples.Pair;

import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;

/**
 * Extended logic to execute a Rule for 3 Player board.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

class RuleProcessor_3PlayerBoard extends DefaultProcesssor {
	@Override
	public Map<String,Pair<IPositionAgent, IRuleAgent>> tryFindPossibleCandidateMovePositions( IPositionAgent oPosition) {		
		return super.tryFindPossibleCandidateMovePositions(oPosition);
	}
}
