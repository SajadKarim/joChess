package jchess.ruleengine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import jchess.common.IBoardAgent;
import jchess.common.IMoveCandidate;
import jchess.common.IPathAgent;
import jchess.common.IPieceAgent;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRule;
import jchess.common.IRuleAgent;
import jchess.gamelogic.MoveCandidate;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

/**
 * DefaultRuleProcessor
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class DefaultRuleProcessor implements IRuleProcessor {
	protected IAppLogger m_oLogger;
	
	public DefaultRuleProcessor(IAppLogger oLogger) {
		m_oLogger = oLogger;
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating DefaultRuleProcessor.", "DefaultRuleProcessor", "DefaultRuleProcessor");
	}
	
	public void tryEvaluateAllRules(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidatePositions) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Evaluating selected move candidate.", "tryEvaluateAllRules", "DefaultRuleProcessor");

		IPositionAgent oPosition = oPiece.getPosition();
		IPlayerAgent oPlayer = oPiece.getPlayer();
		
		if( oPosition == null) {
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
    	
		tryFindPossibleCandidateMovePositions(oPiece, oPosition, oPlayer, qData, mpCandidatePositions);
    }

	public void tryFindPossibleCandidateMovePositions(IPieceAgent oPieceToMove, IPositionAgent oSourcePosition, IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidate> mpCandidatePositions) {		
		m_oLogger.writeLog(LogLevel.INFO, "Finding candidate move positions.", "tryFindPossibleCandidateMovePositions", "DefaultRuleProcessor");

		RuleProcessorData oData = null;		
		while( (oData = qData.poll()) != null) {
			IRuleAgent oCurrentRule = oData.getRule();			
			IPositionAgent oCurrentPosition = oData.getCurrentPosition();
			IPositionAgent oLastPosition = oData.getLastPosition();
			
			switch( oCurrentRule.getManoeuvreStrategy()) {
			case BLINKER:
				tryFindCandidateMovesForBlinkerStrategy(oCurrentRule, oCurrentPosition, oLastPosition, oPieceToMove, oSourcePosition, oPlayer, qData, mpCandidatePositions);
				break;
			case FILE_AND_RANK:
				tryFindCandidateMovesForFileAndRankStrategy(oCurrentRule, oCurrentPosition, oLastPosition, oPieceToMove, oSourcePosition, oPlayer, qData, mpCandidatePositions);
				break;
			default:
				break;
			}			
		}
	}
	
	void tryFindCandidateMovesForBlinkerStrategy(IRuleAgent oCurrentRule, IPositionAgent oCurrentPosition, IPositionAgent oLastPosition, IPieceAgent oPieceToMove, IPositionAgent oSourcePosition, IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidate> mpCandidatePositions) {
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
    			mpCandidatePositions.put(oNextPosition.getName(), new MoveCandidate(oCurrentRule, oPieceToMove, oSourcePosition, oNextPosition));
    				
    		if( !bCanContinue.get())
    			continue;

			IRuleAgent oDuplicateRule = oCurrentRule.clone();
    		if( oDuplicateRule.canProceedWithThisRule()) {
    			qData.add(new RuleProcessorData(oDuplicateRule, oNextPosition, oCurrentPosition));
    		}
    		else {
    			IRuleAgent oChildRule = null;
        		while( (oChildRule = oDuplicateRule.getNextChildRule()) != null){
            		qData.add(new RuleProcessorData(oChildRule, oNextPosition, oCurrentPosition));
        		}
    		}        		
		}
	}
	
	void tryFindCandidateMovesForFileAndRankStrategy(IRuleAgent oCurrentRule, IPositionAgent oCurrentPosition, IPositionAgent oLastPosition, IPieceAgent oPieceToMove, IPositionAgent oSourcePosition, IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidate> mpCandidatePositions) {
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
        			mpCandidatePositions.put(oNextPosition.getName(), new MoveCandidate(oCurrentRule, oPieceToMove, oSourcePosition, oNextPosition));
        				
        		if( !bCanContinue.get())
        			continue;

    			IRuleAgent oDuplicateRule = oCurrentRule.clone();
        		if( oDuplicateRule.canProceedWithThisRule()) {
        			qData.add(new RuleProcessorData(oDuplicateRule, oNextPosition, null));
        		}
        		else {
        			IRuleAgent oChildRule = null;
            		while( (oChildRule = oDuplicateRule.getNextChildRule()) != null){
                		qData.add(new RuleProcessorData(oChildRule, oNextPosition, null));
            		}
        		}        		
        	}
		}
	}

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
