package jchess.ruleengine;

import jchess.common.IBoardActivity;
import jchess.common.IBoardAgent;
import jchess.common.IMoveCandidate;
import jchess.common.IPieceAgent;
import jchess.common.IPlayerAgent;
import jchess.gamelogic.BoardActivity;

/**
 * This is a custom class specific to process the custom rules that are peculiar to Pawn piece only.
 * This class defines all the custom rules that at the moments are not supportable in XML.
 *  
 * @author	Sajad Karim
 * @since	19 Jan 2020
 */

public final class CannonRulesProcessor {
	
	/**
	 * This method executes cannon rule.
	 * 
	 * @param IBoardAgent
	 * @param IMoveCandidate
	 * @return IBoardActivity
	 */
	public static IBoardActivity tryExecuteBombAndPromotionRule(IBoardAgent oBoard, IMoveCandidate oMoveCandidate) {
		IBoardActivity oActivity = new BoardActivity(oMoveCandidate);
		
		IPieceAgent oSourcePiecePriorMove = oMoveCandidate.getSourcePosition().getPiece();
		IPieceAgent oDestinationPiecePriorMove = oMoveCandidate.getCandidatePosition().getPiece();

		IPlayerAgent oPlayer = oSourcePiecePriorMove.getPlayer();
	
		if (oDestinationPiecePriorMove != null && oDestinationPiecePriorMove.getPlayer() == oSourcePiecePriorMove.getPlayer()) {
			return null;
		}
		
		Object oData = oSourcePiecePriorMove.getCustomData();
		if (oData == null) {
			IPieceAgent oSourcePieceAfterMove = (IPieceAgent) oSourcePiecePriorMove.clone();
			oSourcePieceAfterMove.setPosition(oMoveCandidate.getSourcePosition());

			oSourcePieceAfterMove.setCustomData(1);
			oSourcePieceAfterMove.updateImage(getImageFilePathForConsumedCannon(oSourcePiecePriorMove.getName()));
			
			oMoveCandidate.getSourcePosition().setPiece(oSourcePieceAfterMove);
			oMoveCandidate.getCandidatePosition().setPiece(null);
			
			oActivity.addPriorMoveEntry(oMoveCandidate.getSourcePosition(), oSourcePiecePriorMove);
			oActivity.addPriorMoveEntry(oMoveCandidate.getCandidatePosition(), oDestinationPiecePriorMove);
			oActivity.addPostMoveEntry(oMoveCandidate.getSourcePosition(), oSourcePieceAfterMove);
			oActivity.addPostMoveEntry(oMoveCandidate.getCandidatePosition(), null);
			oActivity.setPlayer(oPlayer);
		} else {
			int nRemainingBombs = (int)oData;
			if (nRemainingBombs == 1) {
				IPieceAgent oSourcePieceAfterMove = (IPieceAgent)oBoard.getUnlinkedPieceAgent(getPawnPieceName(oSourcePiecePriorMove.getName())).clone(); 
				
				oSourcePieceAfterMove.setPosition(oMoveCandidate.getSourcePosition());
				oSourcePieceAfterMove.setPlayer(oPlayer);
				
				oMoveCandidate.getSourcePosition().setPiece(oSourcePieceAfterMove);
				oMoveCandidate.getCandidatePosition().setPiece(null);
				
				oActivity.addPriorMoveEntry(oMoveCandidate.getSourcePosition(), oSourcePiecePriorMove);
				oActivity.addPriorMoveEntry(oMoveCandidate.getCandidatePosition(), oDestinationPiecePriorMove);
				oActivity.addPostMoveEntry(oMoveCandidate.getSourcePosition(), oSourcePieceAfterMove);
				oActivity.addPostMoveEntry(oMoveCandidate.getCandidatePosition(), null);
				oActivity.setPlayer(oPlayer);
				
			}
		}
		
		return oActivity;
	}
	
	// This method takes the piece name and returns the respective image for the cannon when it is consumed.
	public static String getImageFilePathForConsumedCannon(String stName) {
		switch (stName) {
			case "CannonWhiteR":
				return "CannonR-W-Consumed.png";
			case "CannonWhiteL":
				return "CannonL-W-Consumed.png";
			case "CannonBlackR":
				return "CannonR-B-Consumed.png";
			case "CannonBlackL":
				return "CannonL-B-Consumed.png";
			case "CannonRedR":
				return "CannonR-R-Consumed.png";
			case "CannonRedL":
				return "CannonL-R-Consumed.png";
			default:
				return "";
		}
	}

	// This method takes the piece name and returns the respective pawn piece name.
	public static String getPawnPieceName(String stName) {
		switch (stName) {
			case "CannonWhiteR":
			case "CannonWhiteL":
				return "PawnWhite";
			case "CannonBlackR":
			case "CannonBlackL":
				return "PawnBlack";
			case "CannonRedR":
			case "CannonRedL":
				return "PawnRed";
			default:
				return "";
		}
	}
}
