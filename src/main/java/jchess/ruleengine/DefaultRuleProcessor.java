package jchess.ruleengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import jchess.common.IBoardAgent;
import jchess.common.IBoardFactory;
import jchess.common.IMoveCandidate;
import jchess.common.IPathAgent;
import jchess.common.IPieceAgent;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRule;
import jchess.common.IRuleAgent;
import jchess.gamelogic.BoardAgentFactory;
import jchess.gamelogic.MoveCandidate;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

/**
 * This class provides functionality to process all the basic rules that can be provided in XML.
 * It evaluates the pieces and with the help of the rules defined against them, it finds out all the possible move 
 * candidates the piece can perform.
 * It also facilitates the game engine in the execution of the rules.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class DefaultRuleProcessor implements IRuleProcessor {
	protected IAppLogger m_oLogger;
	
	/**
	 * Constructor for DefaultRuleProcessor.
	 * 
	 * @param oLogger IAppLogger
	 */
	public DefaultRuleProcessor(IAppLogger oLogger) {
		m_oLogger = oLogger;
		
		m_oLogger.writeLog(LogLevel.INFO, "Instantiating DefaultRuleProcessor.", "DefaultRuleProcessor", "DefaultRuleProcessor");
	}
	
	/**
	 * This method finds out whether the provided position can be a candidate position to make a move, and it also (with the help of the rule)
	 * deduces whether algorithm should proceed with the position to find out the next possible candidate moves.
	 * 
	 * @param oBoard IBoardAgent
	 * @param oPiece IPieceAgent
	 * @param mpCandidatePositions Map of String and IMoveCandidate
	 */
	public void tryEvaluateAllRules(IBoardAgent oBoard, IPieceAgent oPiece, Map<String, IMoveCandidate> mpCandidatePositions) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Evaluating selected move candidate. Piece=" + oPiece.toLog(), "tryEvaluateAllRules", "DefaultRuleProcessor");

		IPositionAgent oPosition = oPiece.getPosition();
		IPlayerAgent oPlayer = oPiece.getPlayer();
		
		if (oPosition == null) {
			m_oLogger.writeLog(LogLevel.ERROR, "Piece is not attached to the Position.", "tryEvaluateAllRules", "DefaultRuleProcessor");
			return;
		}
		
		if (oPlayer == null) {
			m_oLogger.writeLog(LogLevel.ERROR, "No Player is attached to the Position.", "tryEvaluateAllRules", "DefaultRuleProcessor");
			return;
		}

		// As one or more Rules can be defined against a Piece, and a Rule itself may have further rules, therefore, adding them in a Queue
		// so that while traversing a Rule if we encounter by another Rule, we would simply add it to this Queue and repeat the same process.
		Queue<RuleProcessorData> qData = new LinkedList<RuleProcessorData>();		
    	for (IRuleAgent oRule : oPosition.getPiece().getRules()) {
    		oRule.reset();    		
    		qData.add(new RuleProcessorData(oRule, oPosition, null));
    	}
    	
		tryFindPossibleCandidateMovePositions(oPiece, oPosition, oPlayer, qData, mpCandidatePositions);
    }

	/**
	 * This method finds out whether the provided position can be a candidate position to make a move, and it also (with the help of the rule)
	 * deduces whether algorithm should proceed with the position to find out the next possible candidate moves.
	 * A Rule can manoeuvre in two ways therefore this method makes respective calls in this method.
	 * 
	 * @param oPieceToMove IPieceAgent
	 * @param oSourcePosition IPositionAgent
	 * @param oPlayer IPlayerAgent
	 * @param qData Queue of RuleProcessorData
	 * @param mpCandidatePositions Map of String and IMoveCandidate
	 */
	public void tryFindPossibleCandidateMovePositions(IPieceAgent oPieceToMove, IPositionAgent oSourcePosition, IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidate> mpCandidatePositions) {
		m_oLogger.writeLog(LogLevel.INFO, "Finding candidate move positions.", "tryFindPossibleCandidateMovePositions", "DefaultRuleProcessor");

		RuleProcessorData oData = null;		

		while ((oData = qData.poll()) != null) {
			// The underlying Rule behind this move (operation).
			IRuleAgent oCurrentRule = oData.getRule();
			// The source or current Position where the Piece that has to be moved currently resides.
			IPositionAgent oCurrentPosition = oData.getCurrentPosition();
			// The destination Position where the Piece has to be moved.
			IPositionAgent oLastPosition = oData.getLastPosition();
			
			switch (oCurrentRule.getManoeuvreStrategy()) {
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
	 * This method follows Blinker (Horse Blinkers) methodology to make movements. It follows the straight path and does not support left
	 * or right manoeuvres. It requires the last two positions (current and last positions) to find out the next opposite position. 
	 * If the last position is not known then it first moves to every linked position and from their it takes straight path in each direction.
	 * 
	 * |A1|A2|A3|A4|A5|A....
	 * |B1|B2|B3|B4|B5|B....
	 * |C1|C2|C3|C4|C5|C....
	 * |D1|D2|D3|D4|D5|D....
	 * |....................
	 * 
	 * Fig 3: 2-Players chess board.
	 * 
	 * Consider Fig 3 (2 Player chess board) and cell 'C3' is the active position, and the game engine has to find all the possible moves using
	 * Blinker manoeuvre strategy.
	 * 
	 * Cell 'C3' is surrounded by 8 cells; the cells are B3, B2, C2, D2, D3, D4, C4, B4 and they are in direction West, SouthWest, South, 
	 * SouthEast, East, NorthEast, North, NorthWest respectively. All this information is stored in elements 'Directions' and “Connections'
	 * under the element “Position' (Refer to positions section of XML for details).
	 * 
	 * NW	N	NE
	 * W	○	E
	 * SW	S	WE
	 * 
	 * Table 1: Illustration for directions surrounding by a typical position ‘○’.
	 * 
	 * Example Scenario #1: Traversing towards NE direction.
	 * Assumption: Current position is ‘○’ and last position is the position linked to “SW'.
	 * In the above given scenario and assumption, Blinker strategy starts its search from “SW' and looks in two directions in parallel, 
	 * “W' and “S' directions, until both the search paths leads to the same position, or any of them reaches a dead end. 
	 * 
	 * For instance, following are the search paths that above scenarios would produce:
	 * Search path 1: SW	W	NW	N	NE
	 * Search path 2: SW	S	WE	E	NE
	 * As both the paths meets the same position at the end, the strategy ends its search and returns the position attached to “NE'. 
	 * It would have return NULL position if search paths would not have reached the same positions.
	 * 
	 *
	 * @param IRuleAgent
	 * @param IPositionAgent
	 * @param IPositionAgent
	 * @param IPieceAgent
	 * @param IPositionAgent
	 * @param IPlayerAgent
	 * @param Queue<RuleProcessorData>
	 * @param Map<String, IMoveCandidate> 
	 */
	protected void tryFindCandidateMovesForBlinkerStrategy(IRuleAgent oCurrentRule, IPositionAgent oCurrentPosition, IPositionAgent oLastPosition, IPieceAgent oPieceToMove, IPositionAgent oSourcePosition, IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidate> mpCandidatePositions) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Finding candidate move positions.", "tryFindCandidateMovesForBlinkerStrategy", "DefaultRuleProcessor");

		List<IPositionAgent> lstPosition = null;
		
		// Blinker strategy as explained above requires two positions (last and current) to perform or to decide its direction.
		// While running it for the first time, and when there is no information about the last position, we traverse through all
		// the available direction and from then onwards we follow Blinkers functionality.
		if (oLastPosition == null) {
			lstPosition = new ArrayList<IPositionAgent>();
			
			for (Map.Entry<String, IPathAgent> entry: oCurrentPosition.getAllPathAgents().entrySet()) {
				IPathAgent oPath = entry.getValue();
	        	if (oPath.getDirection() != oCurrentRule.getDirection()) {
	        		// Direction of the path is different than the one defined in the XML.
	        		continue;
	        	}
	        	
	        	lstPosition.addAll(entry.getValue().getAllPositionAgents());
			}
		} else {
			// Getting the opposite Position to the one provided in the argument.
			lstPosition = oCurrentPosition.tryGetOppositePath(oLastPosition);
		}
		
		if (lstPosition == null) {
			m_oLogger.writeLog(LogLevel.ERROR, "Empty list of Positions.", "tryFindCandidateMovesForBlinkerStrategy", "DefaultRuleProcessor");
			return;
		}
		
		Iterator<IPositionAgent> itPosition = lstPosition.iterator();
		while (itPosition.hasNext()) {
			// Possible candidate position.
			IPositionAgent oNextPosition = itPosition.next();

			// Validating position with the parameters defined in the Rule.
    		if (!oCurrentPosition.tryValidateRuleApplicability(oPlayer.getBoardMapping(), oCurrentRule.getFamily(), oCurrentRule.getFile(), oCurrentRule.getRank(), oNextPosition)) {
    			continue;
    		}
    		
    		// Confirming if this Position is acceptable as a candidate and does the Rule allows to proceed further.
    		AtomicReference<Boolean> bIsValidMode = new AtomicReference<Boolean>(false);
    		AtomicReference<Boolean> bCanContinue = new AtomicReference<Boolean>(false);
    		
    		checkForPositionMoveCandidacyAndContinuity(oPlayer, oCurrentRule, oNextPosition, bIsValidMode, bCanContinue);

    		// Adding position to move candidates.
    		if (bIsValidMode.get()) {
    			mpCandidatePositions.put(oNextPosition.getName(), new MoveCandidate(oCurrentRule, oPieceToMove, oSourcePosition, oNextPosition));
    		}
    		
    		if (!bCanContinue.get()) {
    			continue;
    		}
    		
			IRuleAgent oDuplicateRule = oCurrentRule.clone();
    		if (oDuplicateRule.canProceedWithThisRule()) {
    			qData.add(new RuleProcessorData(oDuplicateRule, oNextPosition, oCurrentPosition));
    		} else {
    			IRuleAgent oChildRule = null;
        		while ((oChildRule = oDuplicateRule.getNextChildRule()) != null) {
            		qData.add(new RuleProcessorData(oChildRule, oNextPosition, oCurrentPosition));
        		}
    		}        		
		}
	}
	
	/**
	 * 
	 * This method uses File and Rank information to make movements. It can move towards any direction at any stage during the
	 *  processing of the rule. Parameters File and Rank of the rule assists this strategy in manoeuvring through positions.
	 * 
	 * |A1|A2|A3|A4|A5|A....
	 * |B1|B2|B3|B4|B5|B....
	 * |C1|C2|C3|C4|C5|C....
	 * |D1|D2|D3|D4|D5|D....
	 * |....................
	 * 
	 * Fig 3: 2-Players chess board.
	 * 
	 * 
	 * Consider Fig 3 (2 Player chess board) and cell 'C3' is the active position, and the game engine has to find all the possible
	 *  moves using File & Rank manoeuvre strategy.
	 * Cell 'C3' is surrounded by 8 cells; the cells are B3, B2, C2, D2, D3, D4, C4, and B4. All this information is stored in elements 'Directions'
	 *  and 'Connections' under the element 'Position' (Refer to positions section of XML for details).
	 * 
	 * Example Scenario #1: Move to position 'D4'.
	 * Assumption: Position 'D4' is one File and one Rank ahead the current position, therefore, parameters File and Rank should have
	 * value 'FORWARD' assigned.
	 * In the above given scenario, the strategy starts its search by traversing all the attached positions and comparing their File and Ranks.
	 * The position 'D4' is one File and one Rank above than the current position - and simply it would compare all the linked positions and
	 * stops its search when it comes across the condition (where both File and Rank have greater values than the current position). 
	 * 
	 * Example Scenario #2: Move to position 'E4'.
	 * In the above given scenario, there are numerous paths that can be provided to reach the desired position. Some are;
	 * C3	->	D4	->	E4
	 * C3	->	D3	->	E3	->	E4
	 * C3	->	C4	->	D4	->	E4
	 * The strategy starts its search by traversing all the attached positions and comparing their File and Ranks. Depending on the rule
	 * strategy looks for desired position and repeats its search until it reaches the final position.
	 * For instance if the second path is mentioned then it would first proceed in the direction twice when the Rank is same and File is
	 * greater than the current position, and lastly it would proceed when File is same and Rank is greater.
	 * 
	 * for more details, visit https://docs.google.com/document/d/1KiicUFyHlNJx7MD8-wBBwc9jcpXSSnzioZWuMbWXLnU/edit
	 *
	 *
	 * @param IRuleAgent
	 * @param IPositionAgent
	 * @param IPositionAgent
	 * @param IPieceAgent
	 * @param IPositionAgent
	 * @param IPlayerAgent
	 * @param Queue<RuleProcessorData>
	 * @param Map<String, IMoveCandidate> 
	 */
	protected void tryFindCandidateMovesForFileAndRankStrategy(IRuleAgent oCurrentRule, IPositionAgent oCurrentPosition, IPositionAgent oLastPosition, IPieceAgent oPieceToMove, IPositionAgent oSourcePosition, IPlayerAgent oPlayer, Queue<RuleProcessorData> qData, Map<String, IMoveCandidate> mpCandidatePositions) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Finding candidate move positions.", "tryFindCandidateMovesForFileAndRankStrategy", "DefaultRuleProcessor");

		for (Map.Entry<String, IPathAgent> entry: oCurrentPosition.getAllPathAgents().entrySet()) {
			IPathAgent oPath = entry.getValue();
        	if (oPath.getDirection() != oCurrentRule.getDirection()) {
        		continue;
        	}
		    		
    		Iterator<IPositionAgent> itPosition = oPath.getAllPositionAgents().iterator();
        	while (itPosition.hasNext()) {
    			// Possible candidate position.
        		IPositionAgent oNextPosition = itPosition.next();        		
        			        		
    			// Validating position with the parameters defined in the Rule.
        		if (!oCurrentPosition.tryValidateRuleApplicability(oPlayer.getBoardMapping(), oCurrentRule.getFamily(), oCurrentRule.getFile(), oCurrentRule.getRank(), oNextPosition)) {
        			continue;
        		}
        		
        		// Confirming if this Position is acceptable as a candidate and does the Rule allows to proceed further.
        		AtomicReference<Boolean> bIsValidMode = new AtomicReference<Boolean>(false);
        		AtomicReference<Boolean> bCanContinue = new AtomicReference<Boolean>(false);
        		
        		checkForPositionMoveCandidacyAndContinuity(oPlayer, oCurrentRule, oNextPosition, bIsValidMode, bCanContinue);
        		
        		// Adding position to move candidates.
        		if (bIsValidMode.get()) {
        			mpCandidatePositions.put(oNextPosition.getName(), new MoveCandidate(oCurrentRule, oPieceToMove, oSourcePosition, oNextPosition));
        		}
        		
        		if (!bCanContinue.get()) {
        			continue;
        		}
        		
    			IRuleAgent oDuplicateRule = oCurrentRule.clone();
        		if (oDuplicateRule.canProceedWithThisRule()) {
        			qData.add(new RuleProcessorData(oDuplicateRule, oNextPosition, null));
        		} else {
        			IRuleAgent oChildRule = null;
            		while ((oChildRule = oDuplicateRule.getNextChildRule()) != null) {
                		qData.add(new RuleProcessorData(oChildRule, oNextPosition, null));
            		}
        		}        		
        	}
		}
	}

	/**
	 * This method checks whether the position meets the requirements defined in the rule to be a possible move candidate, and it
	 * also finds out whether to seize the search process or not.
	 * 
	 * @param oPlayer IPlayerAgent
	 * @param oRule IRule
	 * @param oCandidacyPosition IPositionAgent
	 * @param bIsValidMode AtomicReference for Boolean
	 * @param bCanContinue AtomicReference for Boolean
	 */
	public void checkForPositionMoveCandidacyAndContinuity(IPlayerAgent oPlayer, IRule oRule, IPositionAgent oCandidacyPosition, AtomicReference<Boolean> bIsValidMode, AtomicReference<Boolean> bCanContinue) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Verifying if position can be a candidate move and can continue as the next position.", "checkForPositionMoveCandidacyAndContinuity", "DefaultRuleProcessor");

		bIsValidMode.set(false);
		bCanContinue.set(false);
		
		switch (oRule.getRuleType()) {
			case MOVE_TRANSIENT:
				bIsValidMode.set(false);
				bCanContinue.set(true);
			break;
			case MOVE:
				if (oCandidacyPosition.getPiece() != null) {
					bIsValidMode.set(false);
					bCanContinue.set(false);
				} else {
					bIsValidMode.set(true);
					bCanContinue.set(true);
				}
				break;
			case MOVE_AND_CAPTURE:
				if (oCandidacyPosition.getPiece() == null) {
					bIsValidMode.set(true);
					bCanContinue.set(true);
				} else if (oCandidacyPosition.getPiece().getPlayer() == oPlayer) {
					bIsValidMode.set(false);
					bCanContinue.set(false);
				} else if (oCandidacyPosition.getPiece().getPlayer() != oPlayer) {
					bIsValidMode.set(true);
					bCanContinue.set(false);
				}
				break;
			case MOVE_IFF_CAPTURE_POSSIBLE:
				if (oCandidacyPosition.getPiece() == null) {
					bIsValidMode.set(false);
					bCanContinue.set(true);
				} else if (oCandidacyPosition.getPiece().getPlayer() == oPlayer) {
					bIsValidMode.set(false);
					bCanContinue.set(false);
				} else if (oCandidacyPosition.getPiece().getPlayer() != oPlayer) {
					bIsValidMode.set(true);
					bCanContinue.set(false);
				}
				break;
			default:
				break;
		}
	}
	
	private IPieceAgent isPositionUnderAttack(IBoardAgent oBoard, String stPositionName, IPlayerAgent oPlayerLinkedToPosition) {
		IBoardFactory oBoardFactory = new BoardAgentFactory();
		IPieceAgent oPiece = (IPieceAgent) oBoardFactory.createPiece();
		oPiece.setPlayer(oPlayerLinkedToPosition);
		oPiece.setPosition(oBoard.getPositionAgent(stPositionName));
		
		return isPieceEndangered(oBoard, oPiece);
	}
	
	public IPieceAgent isPieceEndangered(IBoardAgent oBoard, IPieceAgent oPiece) {
		// Iterating opponent players piece to check if they can capture the provided piece or not.	
		for (Map.Entry<String, IPlayerAgent> itPlayer : oBoard.getAllPlayerAgents().entrySet()) {
			if (itPlayer.getValue().equals(oPiece.getPlayer())) {
				// Ignore piece from same player.
				continue;
			}
			// Iterating the pieces of the current player.
			for (Map.Entry<String, IPieceAgent> itPiece : itPlayer.getValue().getAllPieces().entrySet()) {
				IPieceAgent oOpponentPiece = itPiece.getValue();
				if (oOpponentPiece.getPosition() == null) {
					// Piece is not linked to any piece indicates the piece has been captured.
					continue;
				}

				// Fetching the possible moves the piece can make.
				Map<String, IMoveCandidate> mpCandidateMovePositions = new HashMap<String, IMoveCandidate>();
				tryEvaluateAllRules(oBoard, oOpponentPiece, mpCandidateMovePositions);
				for (Map.Entry<String, IMoveCandidate> itCandidateMove : mpCandidateMovePositions.entrySet()) {				
					if (itCandidateMove.getValue().getCandidatePosition().equals(oPiece.getPosition())) {
						// The current opponent's piece can capture the provided piece.
						return oOpponentPiece;
					}
				}
			}
		}
		
		return null;
	}	

	
	public IPlayerAgent tryCheckIfPlayerEndengered(IBoardAgent oBoard, IPlayerAgent oPlayer) {
		IPieceAgent oOpponentPiece = isPieceEndangered(oBoard, oPlayer.getKingPiece());
		if (oOpponentPiece != null) {
			return oOpponentPiece.getPlayer();
		}
		
		return null;
	}

	/**
	 * Checking for Stalemate Rule
	 * 
	 * Stalemate is a situation in the game of chess where the player whose turn 
	 * it is to move is not in check but has no legal move. 
	 * The rules of chess provide that when stalemate occurs, the game ends as a draw.
	 */
	public Boolean checkStalemate(IBoardAgent oBoard, IPlayerAgent oPlayer) {
		IPieceAgent oKingPiece = oPlayer.getKingPiece();

		// Checking if any piece other than King can make any move.
		for (Map.Entry<String, IPieceAgent> itPiece : oPlayer.getAllPieces().entrySet()) {
			IPieceAgent oPiece = itPiece.getValue();
			
			if (oPiece.getPosition() == null) {
				continue;
			}
			
			if (oPiece.equals(oKingPiece)) {
				continue;
			}
			
			Map<String, IMoveCandidate> mpCandidateMovePosition = new HashMap<String, IMoveCandidate>();
			tryEvaluateAllRules(oBoard, oPiece, mpCandidateMovePosition);
			
			if (!mpCandidateMovePosition.isEmpty()) {
				return false;
			}
		}
		
		// Fetching all the possible moves King can make.
		Map<String, IMoveCandidate> mpCandidateMovePositionForKing = new HashMap<String, IMoveCandidate>();
		tryEvaluateAllRules(oBoard, oKingPiece, mpCandidateMovePositionForKing);
		
		// Iterating through the opponent's pieces and see if there is any candidate move that King can make
		// without endangering itself.
		for (Map.Entry<String, IPlayerAgent> itPlayer : oBoard.getAllPlayerAgents().entrySet()) {
			if (itPlayer.getValue().equals(oPlayer)) {
				// Ignore piece from same player.
				continue;
			}
			// Iterating the pieces of the current player.
			for (Map.Entry<String, IPieceAgent> itPiece : itPlayer.getValue().getAllPieces().entrySet()) {
				IPieceAgent oOpponentPiece = itPiece.getValue();
				if (oOpponentPiece.getPosition() == null) {
					// Piece is not linked to any piece indicates the piece has been captured.
					continue;
				}
				// Fetching the possible moves the piece can make.
				Map<String, IMoveCandidate> mpCandidateMovePositionsForOpponentPlayerPiece = new HashMap<String, IMoveCandidate>();
				tryEvaluateAllRules(oBoard, oOpponentPiece, mpCandidateMovePositionsForOpponentPlayerPiece);
				for (Map.Entry<String, IMoveCandidate> itCandidateMove : mpCandidateMovePositionsForOpponentPlayerPiece.entrySet()) {				
					if (mpCandidateMovePositionForKing.containsKey(itCandidateMove.getKey())) {
						mpCandidateMovePositionForKing.remove(itCandidateMove.getKey());
						
						if (mpCandidateMovePositionForKing.size() == 0) {
							return true;
						}		
					}
				}
			}
		}

		if (mpCandidateMovePositionForKing.size() > 0) {
			return false;
		}
			
		return true;
	}	
}
