package jchess.ruleengine;

import java.util.HashMap;

import java.util.Map;

import org.javatuples.Pair;

import jchess.common.*;
import jchess.gui.IGUIHandle;

/**
 * The default logic to execute a Rule.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

class DefaultRuleEngine implements IRuleEngine{
	protected IRuleProcessor m_oRuleProcessor;
	protected IGUIHandle m_oGUIHandler;
	
	public DefaultRuleEngine(IRuleProcessor oRuleProcessor) {
		m_oRuleProcessor = oRuleProcessor;
	}
	
	public void setGUIHandle(IGUIHandle oGUIHandler) {
		m_oGUIHandler = oGUIHandler;
	}
	
	public void setRuleProcessor(IRuleProcessor oRuleProcessor) {
		m_oRuleProcessor = oRuleProcessor;
	}
	
	public Map<String,Pair<IPositionAgent, IRuleAgent>> tryEvaluateAllRules(IBoardAgent oBoard, IPositionAgent oPosition) {	
		Map<String,Pair<IPositionAgent, IRuleAgent>> mpCandidateMovePositions = new HashMap<String,Pair<IPositionAgent, IRuleAgent>>();

		m_oRuleProcessor.tryEvaluateAllRules(oPosition, mpCandidateMovePositions);
		
		return mpCandidateMovePositions;
    }

	public void tryExecuteRule(IBoardAgent oBoard, IPositionAgent oSourcePosition, Pair<IPositionAgent, IRuleAgent> oDestinationPositionAndRule) {
		switch( oDestinationPositionAndRule.getValue1().getRuleType()) {
			case MOVE:{
				if( oDestinationPositionAndRule.getValue0().getPiece() == null) {
					jchess.common.IPieceAgent oPiece = oSourcePosition.getPiece();
					oDestinationPositionAndRule.getValue0().setPiece((jchess.gamelogic.PieceAgent)oPiece);
					oSourcePosition.setPiece(null);
				}
			}
				break;
			case MOVE_AND_CAPTURE:{
				jchess.common.IPieceAgent oPiece = oSourcePosition.getPiece();
				oDestinationPositionAndRule.getValue0().setPiece((jchess.gamelogic.PieceAgent)oPiece);
				oSourcePosition.setPiece(null);
			}
				break;
			case MOVE_IFF_CAPTURE_POSSIBLE:{
				jchess.common.IPieceAgent oPiece = oSourcePosition.getPiece();
				oDestinationPositionAndRule.getValue0().setPiece((jchess.gamelogic.PieceAgent)oPiece);
				oSourcePosition.setPiece(null);				
			}
				break;
			case MOVE_TRANSIENT:{
				
			}
				break;
			case CUSTOM:
			default:
				break;
		}
	}
}
