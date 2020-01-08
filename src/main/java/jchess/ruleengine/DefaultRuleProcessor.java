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
 * This class provides functionality to process all the basic rules (defined in XML).
 * It evaluates the possible move candidates using the Rules acceptable in XML.
 * It also facilitate in executing the Rules.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class DefaultRuleProcessor implements IRuleProcessor {
	protected IAppLogger m_oLogger;
	
	/**
	 * Constructor for DefaultRuleProcessor.
	 * @param oLogger
	 */
	public DefaultRuleProcessor(IAppLogger oLogger) {
		m_oLogger = oLogger;
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating DefaultRuleProcessor.", "DefaultRuleProcessor", "DefaultRuleProcessor");
	}
	
	/**
	 * This method tries to find all the possible candidate moves that the selected Piece can make.
	 * @param oBoard the chess board
	 * @param oPiece the piece
	 * @param mpCandidatePositions the candidate positions
	 */
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

		// As one or more Rules can be defined against a Piece, and a Rule itself may have further rules, therefore, adding them in a Queue
		// so that while traversing a Rule if we encounter by another Rule, we would simply add it to this Queue and repeat the same process.
		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();		
    	for(IRuleAgent oRule : oPosition.getPiece().getRules()) {
    		oRule.reset();
    		
    		qData.add(new RuleProcessorData(oRule, oPosition, null));
    	}
    	
		tryFindPossibleCandidateMovePositions(oPiece, oPosition, oPlayer, qData, mpCandidatePositions);
    }

	/**
	 * A Rule can manoeuvre in two way therefore this method makes respective calls in this method.
	 */
	public void tryFindPossibleCandidateMovePositions(IPieceAgent oPieceToMove, IPositionAgent oSourcePosition, IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidate> mpCandidatePositions) {		
		m_oLogger.writeLog(LogLevel.INFO, "Finding candidate move positions.", "tryFindPossibleCandidateMovePositions", "DefaultRuleProcessor");

		RuleProcessorData oData = null;		
		while( (oData = qData.poll()) != null) {
			// The underlying Rule behind this move (operation).
			IRuleAgent oCurrentRule = oData.getRule();
			// The source or current Position where the Piece that has to be moved currently resides.
			IPositionAgent oCurrentPosition = oData.getCurrentPosition();
			// The destination Position where the Piece has to be moved.
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
	
	/**
	 * This method follows Blinker (Horse Blinkers) methodology to process any Rule.
	 * 
	 * Blinker Strategy:
	 * 
	 * |A1|A2|A3|A4|A5|A....
	 * |B1|B2|B3|B4|B5|B....
	 * |C1|C2|C3|C4|C5|C....
	 * |D1|D2|D3|D4|D5|D....
	 * |....................
	 * 
	 * Consider above matrix and cell [C3].
	 * Cell C3 is surrounded by 8 cells; cells that are at the following locations N,W,E,S,NE,NW,SE,SW.
	 * All the attached locations (directions) to C3 are stored in the form of Paths. (Please refer XML for details).
	 * 
	 *  NW	N	NE
	 * 	W		E
	 *  SW	S	WE
	 *  
	 * Scenario#1: Traversing to NE direction.
	 * Considering the start pointer is set to C3's SW location. 
	 * [Summary]: Blinker strategy looks in two directions in parallel and if it reaches the same points in both the direction it ends the search and returns the 
	 * position associated to that point/path.
	 * [Detail]: Blinker strategy launches two threads (or code flows) that keeps looking for the next available path until they reach the 
	 * same path where they initiated from or reaches a dead end.
	 * Eg. (Considering above example) One code flow starts looking in the direction that leads to 'W', and the other in the direction the leads to 'S'.
	 * After every iteration Blinker strategy compares the end-points both the directions reaches and if they met the same point it ends the search
	 * operation and return the position attached to that point/path and if required, repeats the same process on the next position.
	 * 
	 */
	void tryFindCandidateMovesForBlinkerStrategy(IRuleAgent oCurrentRule, IPositionAgent oCurrentPosition, IPositionAgent oLastPosition, IPieceAgent oPieceToMove, IPositionAgent oSourcePosition, IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidate> mpCandidatePositions) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Finding candidate move positions.", "tryFindCandidateMovesForBlinkerStrategy", "DefaultRuleProcessor");

		List<IPositionAgent> lstPosition = null;
		
		// Blinker strategy as explained above requires two positions (last and current) to perform or to decide its direction.
		// While running it for the first time, and when there is no information about the last position, we traverse through all
		// the available direction and from then onwards we follow Blinkers functionality.
		if( oLastPosition == null) {
			lstPosition = new ArrayList<IPositionAgent>();
			
			for( Map.Entry<String, IPathAgent> entry: oCurrentPosition.getAllPathAgents().entrySet()) {
				IPathAgent oPath = entry.getValue();
	        	if( oPath.getDirection() != oCurrentRule.getDirection()) {
	        		// Direction of the path is different than the one defined in the XML.
	        		continue;
	        	}
	        	
	        	lstPosition.addAll(entry.getValue().getAllPositionAgents());
			}
		} else {
			// Getting the opposite Position to the one provided in the argument.
			lstPosition = oCurrentPosition.tryGetOppositePath(oLastPosition);
		}
		
		if( lstPosition == null) {
			m_oLogger.writeLog(LogLevel.ERROR, "Empty list of Positions.", "tryFindCandidateMovesForBlinkerStrategy", "DefaultRuleProcessor");
			return;
		}
		
		Iterator<IPositionAgent> itPosition = lstPosition.iterator();
		while( itPosition.hasNext()) {
			// Possible candidate position.
			IPositionAgent oNextPosition = itPosition.next();

			// Validating position with the parameters defined in the Rule.
    		if( !oCurrentPosition.tryValidateRuleApplicability(oPlayer.getBoardMapping(), oCurrentRule.getFamily(), oCurrentRule.getFile(), oCurrentRule.getRank(), oNextPosition))
    			continue;

    		// Confirming if this Position is acceptable as a candidate and does the Rule allows to proceed further.
    		AtomicReference<Boolean> bIsValidMode = new AtomicReference<Boolean>(false);
    		AtomicReference<Boolean> bCanContinue = new AtomicReference<Boolean>(false);
    		
    		checkForPositionMoveCandidacyAndContinuity(oPlayer, oCurrentRule, oNextPosition, bIsValidMode, bCanContinue);

    		// Adding position to move candidates.
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
	
	/**
	 * This method only uses File and Rank information to process any Rule.
	 * 
	 * File & Rule Strategy:
	 * 
	 * |A1|A2|A3|A4|A5|A....
	 * |B1|B2|B3|B4|B5|B....
	 * |C1|C2|C3|C4|C5|C....
	 * |D1|D2|D3|D4|D5|D....
	 * |....................
	 * 
	 * The numbers mentioned in the above sample board represents File and Rank,
	 * 'A', 'B', 'C', .... etc represent File
	 * and similarly, '1', '2','3',..... etc represent Rank
	 * 
	 * 
	 * Consider above matrix and cell [C3].
	 * Cell C3 is surrounded by 8 cells; cells that are at the following locations N,W,E,S,NE,NW,SE,SW.
	 * All the attached locations (directions) to C3 are stored in the form of Paths. (Please refer XML for details).
	 * 
	 *  NW	N	NE
	 * 	W		E
	 *  SW	S	WE
	 *  
	 * Scenario#1: Moving to B4 cell.
	 * From C3 to B4 requires a decrease in File and an increase in Rank.
	 * In XML, increase and decrease in File and Rank is defined using FORWARD and BACKWARD.
	 * 
	 * Using above technique, any (even complex) manouevres can be made easily.
	 * 
	 * @param oCurrentRule the rule of the piece
	 * @param oCurrentPosition the next position of the piece
	 * @param oLastPosition the current of the piece
	 * @param oPlayer the current player
	 * @param qData the queue for next rule and next position
	 * @param mpCandidatePositions the candidate positions
	 */
	void tryFindCandidateMovesForFileAndRankStrategy(IRuleAgent oCurrentRule, IPositionAgent oCurrentPosition, IPositionAgent oLastPosition, IPieceAgent oPieceToMove, IPositionAgent oSourcePosition, IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidate> mpCandidatePositions) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Finding candidate move positions.", "tryFindCandidateMovesForFileAndRankStrategy", "DefaultRuleProcessor");

		for( Map.Entry<String, IPathAgent> entry: oCurrentPosition.getAllPathAgents().entrySet()) {
			IPathAgent oPath = entry.getValue();
        	if( oPath.getDirection() != oCurrentRule.getDirection())
        		continue;			
		    		
    		Iterator<IPositionAgent> itPosition = oPath.getAllPositionAgents().iterator();
        	while( itPosition.hasNext()) {
    			// Possible candidate position.
        		IPositionAgent oNextPosition = itPosition.next();        		
        			        		
    			// Validating position with the parameters defined in the Rule.
        		if( !oCurrentPosition.tryValidateRuleApplicability(oPlayer.getBoardMapping(), oCurrentRule.getFamily(), oCurrentRule.getFile(), oCurrentRule.getRank(), oNextPosition))
        			continue;

        		// Confirming if this Position is acceptable as a candidate and does the Rule allows to proceed further.
        		AtomicReference<Boolean> bIsValidMode = new AtomicReference<Boolean>(false);
        		AtomicReference<Boolean> bCanContinue = new AtomicReference<Boolean>(false);
        		
        		checkForPositionMoveCandidacyAndContinuity(oPlayer, oCurrentRule, oNextPosition, bIsValidMode, bCanContinue);
        		
        		// Adding position to move candidates.
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

	/**
	 * This method finds out whether the proivded Position can be a candidate to make a move, also it (with the help of Rule)
	 * deduces whether algorithm should proceed with the Position to find out the next possible candidate moves.
	 * 
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
