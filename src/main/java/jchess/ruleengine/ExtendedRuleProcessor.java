package jchess.ruleengine;

import java.util.concurrent.atomic.AtomicReference;

import jchess.common.IPieceAgent;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRule;

public class ExtendedRuleProcessor extends DefaultRuleProcessor {
	public void checkForPositionMoveCandidacyAndContinuity(IPlayerAgent oPlayer, IPieceAgent oPiece, IRule oRule, IPositionAgent oCandidacyPosition, AtomicReference<Boolean> bIsValidMode, AtomicReference<Boolean> bCanContinue) {
		super.checkForPositionMoveCandidacyAndContinuity(oPlayer, oPiece, oRule, oCandidacyPosition, bIsValidMode, bCanContinue);		

		switch(oRule.getRuleType()) {
			case CUSTOM: {
				switch(oRule.getCustomName()) {
				case "MOVE_TRANSIENT[PAWN_FIRST_MOVE_EXCEPTION]":
					bIsValidMode.set(false);
					bCanContinue.set(true);
					break;
				case "MOVE[PAWN_FIRST_MOVE_EXCEPTION]":{
					if( oCandidacyPosition.getPiece() != null) {
						bIsValidMode.set(false);
						bCanContinue.set(false);
					} else {
						bIsValidMode.set(true);
						bCanContinue.set(true);
					}
					}
					break;
				}
			}
				break;
		}
	}
}