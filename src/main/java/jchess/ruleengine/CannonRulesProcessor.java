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
			oSourcePieceAfterMove.setCustomData(1);
			oSourcePieceAfterMove.updateImage(getImageFilePathForConsumedCannon(oSourcePiecePriorMove.getName()));
			
			oSourcePieceAfterMove.setPosition(oMoveCandidate.getSourcePosition());
			oMoveCandidate.getSourcePosition().setPiece(oSourcePieceAfterMove);

			if (oDestinationPiecePriorMove != null) {
				oDestinationPiecePriorMove.setPosition(null);
			}
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
				oSourcePieceAfterMove.setPlayer(oPlayer);
				
				oSourcePieceAfterMove.setPosition(oMoveCandidate.getSourcePosition());
				oMoveCandidate.getSourcePosition().setPiece(oSourcePieceAfterMove);

				if (oDestinationPiecePriorMove != null) {
					oDestinationPiecePriorMove.setPosition(null);
				}
				oMoveCandidate.getCandidatePosition().setPiece(null);
				
				oActivity.addPriorMoveEntry(oMoveCandidate.getSourcePosition(), oSourcePiecePriorMove);
				oActivity.addPriorMoveEntry(oMoveCandidate.getCandidatePosition(), oDestinationPiecePriorMove);
				oActivity.addPostMoveEntry(oMoveCandidate.getSourcePosition(), oSourcePieceAfterMove);
				oActivity.addPostMoveEntry(oMoveCandidate.getCandidatePosition(), null);
				oActivity.setPlayer(oPlayer);
				
			}
		}
		
		oSourcePiecePriorMove.setPosition(null);

		return oActivity;
	}
	/**
	 * This method take the Cannon name and return the Cannon consumed image
	 * 
	 * @param stName String the name of the cannon
	 * @return String the name of the picture of the consumed cannon
	 */
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

	/**
	 * This method take the Cannon name and return the pawn image
	 * 
	 * @param stName String the name of the cannon
	 * @return String the name of the picture of the pawn image
	 */
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
