package jchess.ruleengine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import jchess.common.IBoardAgent;
import jchess.common.IMoveCandidacy;
import jchess.common.IPathAgent;
import jchess.common.IPieceAgent;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRule;
import jchess.common.IRuleAgent;
import jchess.gamelogic.MoveCandidacy;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

public class DefaultRuleProcessor implements IRuleProcessor {
	protected IAppLogger m_oLogger;
	
	public DefaultRuleProcessor(IAppLogger oLogger) {
		m_oLogger = oLogger;
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating DefaultRuleProcessor.", "DefaultRuleProcessor", "DefaultRuleProcessor");
	}
	/**
	 * try evaluate all the rules of the piece
	 * @param oBoard the chess board
	 * @param oPosition the position of the piece
	 * @param mpCandidatePositions the candidate positions
	 */
	public void tryEvaluateAllRules(IBoardAgent oBoard, IPositionAgent oPosition, Map<String, IMoveCandidacy> mpCandidatePositions) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Evaluating selected move candidate.", "tryEvaluateAllRules", "DefaultRuleProcessor");

		IPieceAgent oPiece = oPosition.getPiece();
		IPlayerAgent oPlayer = oPiece.getPlayer();
		
		if( oPiece == null) {
			m_oLogger.writeLog(LogLevel.ERROR, "Piece is not attached to the Position.", "tryEvaluateAllRules", "DefaultRuleProcessor");
			return;
		}
		
		if( oPlayer == null) {
			m_oLogger.writeLog(LogLevel.ERROR, "No Player is attached to the Position.", "tryEvaluateAllRules", "DefaultRuleProcessor");
			return;
		}

		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();
		
    	for(IRuleAgent oRule : oPosition.getPiece().getRules()) {
    		oRule.reset();
    		
    		qData.add(new RuleProcessorData(oRule, oPosition, null));
    	}
    	
		tryFindPossibleCandidateMovePositions(oPlayer, qData, mpCandidatePositions);
    }
	/**
	 * Find the possible candidate move positions
	 * @param oPlayer the current player
	 * @param qData queue Data contains: the rule, the next position, the previous position
	 * @param mpCandidatePositions the possible candidate positions
	 */
	public void tryFindPossibleCandidateMovePositions(IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidacy> mpCandidatePositions) {		
		m_oLogger.writeLog(LogLevel.INFO, "Finding candidate move positions.", "tryFindPossibleCandidateMovePositions", "DefaultRuleProcessor");

		RuleProcessorData oData = null;		
		while( (oData = qData.poll()) != null) {
			IRuleAgent oCurrentRule = oData.getRule();			
			IPositionAgent oCurrentPosition = oData.getCurrentPosition();
			IPositionAgent oLastPosition = oData.getLastPosition();
			
			switch( oCurrentRule.getManoeuvreStrategy()) {
			case BLINKER:
				tryFindCandidateMovesForBlinkerStrategy(oCurrentRule, oCurrentPosition, oLastPosition, oPlayer, qData, mpCandidatePositions);
				break;
			case FILE_AND_RANK:
				tryFindCandidateMovesForFileAndRankStrategy(oCurrentRule, oCurrentPosition, oLastPosition, oPlayer, qData, mpCandidatePositions);
				break;
			default:
				break;
			}			
		}
	}
	
	void tryFindCandidateMovesForBlinkerStrategy(IRuleAgent oCurrentRule, IPositionAgent oCurrentPosition, IPositionAgent oLastPosition, IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidacy> mpCandidatePositions) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Finding candidate move positions.", "tryFindCandidateMovesForBlinkerStrategy", "DefaultRuleProcessor");

		List<IPositionAgent> lstPosition = null;
		
		if( oLastPosition == null) {
			lstPosition = new ArrayList<IPositionAgent>();
			
			for( Map.Entry<String, IPathAgent> entry: oCurrentPosition.getAllPathAgents().entrySet()) {
				IPathAgent oPath = entry.getValue();
	        	if( oPath.getDirection() != oCurrentRule.getDirection())
	        		continue;
	        	
	        	lstPosition.addAll(entry.getValue().getAllPositionAgents());
			}
		} else {
			lstPosition = oCurrentPosition.tryGetOppositePath(oLastPosition);
		}
		
		if( lstPosition == null) 
			return;
		
		Iterator<IPositionAgent> itPosition = lstPosition.iterator();
		while( itPosition.hasNext()) {
			IPositionAgent oNextPosition = itPosition.next();

    		if( !oCurrentPosition.tryValidateRuleApplicability(oPlayer.getBoardMapping(), oCurrentRule.getFamily(), oCurrentRule.getFile(), oCurrentRule.getRank(), oNextPosition))
    			continue;

    		AtomicReference<Boolean> bIsValidMode = new AtomicReference<Boolean>(false);
    		AtomicReference<Boolean> bCanContinue = new AtomicReference<Boolean>(false);
    		
    		checkForPositionMoveCandidacyAndContinuity(oPlayer, oCurrentRule, oNextPosition, bIsValidMode, bCanContinue);
    		
    		if( bIsValidMode.get())
    			mpCandidatePositions.put(oNextPosition.getName(), new MoveCandidacy(null, oNextPosition, oCurrentRule));
    				
    		if( !bCanContinue.get())
    			continue;

    		IRuleAgent oNextRule = oCurrentRule.clone().getNextRule();
    		if( oNextRule == null)
    			continue;
    		
    		qData.add(new RuleProcessorData(oNextRule, oNextPosition, oCurrentPosition));
		}
	}
	/**
	 * 	Try the candidate move for column and row 
	 * @param oCurrentRule the rule of the piece
	 * @param oCurrentPosition the next position of the piece
	 * @param oLastPosition the current of the piece
	 * @param oPlayer the current player
	 * @param qData the queue for next rule and next position
	 * @param mpCandidatePositions the candidate positions
	 */
	void tryFindCandidateMovesForFileAndRankStrategy(IRuleAgent oCurrentRule, IPositionAgent oCurrentPosition, IPositionAgent oLastPosition, IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidacy> mpCandidatePositions) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Finding candidate move positions.", "tryFindCandidateMovesForFileAndRankStrategy", "DefaultRuleProcessor");

		for( Map.Entry<String, IPathAgent> entry: oCurrentPosition.getAllPathAgents().entrySet()) {
			IPathAgent oPath = entry.getValue();
        	if( oPath.getDirection() != oCurrentRule.getDirection())
        		continue;			
		    		
    		Iterator<IPositionAgent> itPosition = oPath.getAllPositionAgents().iterator();
        	while( itPosition.hasNext()) {
        		IPositionAgent oNextPosition = itPosition.next();        		
        			        		
        		if( !oCurrentPosition.tryValidateRuleApplicability(oPlayer.getBoardMapping(), oCurrentRule.getFamily(), oCurrentRule.getFile(), oCurrentRule.getRank(), oNextPosition))
        			continue;

        		AtomicReference<Boolean> bIsValidMode = new AtomicReference<Boolean>(false);
        		AtomicReference<Boolean> bCanContinue = new AtomicReference<Boolean>(false);
        		
        		checkForPositionMoveCandidacyAndContinuity(oPlayer, oCurrentRule, oNextPosition, bIsValidMode, bCanContinue);
        		
        		if( bIsValidMode.get())
        			mpCandidatePositions.put(oNextPosition.getName(), new MoveCandidacy(null, oNextPosition, oCurrentRule));
        				
        		if( !bCanContinue.get())
        			continue;

        		IRuleAgent oNextRule = oCurrentRule.clone().getNextRule();
        		if( oNextRule == null)
        			continue;
        		
        		qData.add(new RuleProcessorData(oNextRule, oNextPosition, null));
        	}
		}
	}
	/**
	 * Check position move candidate continuity
	 * @param oPlayer the current player
	 * @param oRule the rule of the piece
	 * @param oCandidacyPosition the candidate position for the piece
	 * @param bIsValidMode can capture or not 
	 * @param bCanContinue can continue or not
	 */
	public void checkForPositionMoveCandidacyAndContinuity(IPlayerAgent oPlayer, IRule oRule, IPositionAgent oCandidacyPosition, AtomicReference<Boolean> bIsValidMode, AtomicReference<Boolean> bCanContinue) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Verifying if position can be a candidate move and can continue as the next position.", "checkForPositionMoveCandidacyAndContinuity", "DefaultRuleProcessor");

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
