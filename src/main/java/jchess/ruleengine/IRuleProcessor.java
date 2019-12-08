package jchess.ruleengine;

import java.util.Map;

import org.javatuples.Pair;

import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;

/**
 * This interface provides abstraction to different RuleProcessors.
 * It is designed to keep Rule loosely coupled of the main game logic
 * and developer can create different code files for different boards.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IRuleProcessor {
	public Map<String,Pair<IPositionAgent, IRuleAgent>> tryFindPossibleCandidateMovePositions( IPositionAgent oPosition);
}
