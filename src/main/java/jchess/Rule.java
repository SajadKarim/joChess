package jchess;

import java.util.Iterator;
import java.util.List;
import java.util.*;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

public class Rule implements IRule {
	public RULE_TYPE m_enRuleType;
	public DIRECTION m_enDirection;
	public int	m_nRecurrence;
	public FAMILY m_enFamily;
	public FILE m_enFile;
	public RANK m_enRank;
	public List<IRule> m_lstRule;
	public Boolean m_bIgnorePiece;
	private int m_nRepeatCount;
	private Queue<IRule> m_qRules;
	
	public Rule(RULE_TYPE enRuleType, DIRECTION enDirection, int nRecurrence, FAMILY enFamily, FILE enFile, RANK enRank, Boolean bIgnorePiece, List<IRule> lstRule) {
		m_enRuleType = enRuleType;
		m_enDirection = enDirection;
		m_nRecurrence = nRecurrence;
		m_enFamily = enFamily;
		m_enFile = enFile;
		m_enRank = enRank;
		m_bIgnorePiece = bIgnorePiece;
		m_lstRule = lstRule;
		
		m_qRules = new LinkedList<>();
	}
	
	public IRule getNextRule() {
		if( m_nRecurrence > 0 && ++m_nRepeatCount < m_nRecurrence) {
			return this;
		}
		
		if( m_qRules.size() > 0) {
		IRule oRule = m_qRules.remove(); 
		if( oRule != null) {
			return oRule;
		}
		}
		
		return null;
	}
	
	public void reset() {
		m_nRepeatCount = 0;
		
		m_qRules.clear();
		
		if( m_lstRule != null) {
		Iterator<IRule> it = m_lstRule.iterator();
    	while( it.hasNext()) {
    		m_qRules.add(it.next());
    	}
		}
	}
	
	public Boolean canCapture() {
		switch( m_enRuleType) {
		case MOVE_IFF_CAPTURE_POSSIBLE:
		case MOVE_AND_CAPTURE:
			return true;
		}
		return false;
	}

	public Boolean canMove() {
		switch( m_enRuleType) {
		case MOVE_TRANSIENT:
			return false;
		}
		return true;
	}
	
	public void __(Object hello)
	{
		hello = (Object)false;
	}
	public void isValidMove( IPosition oPosition, AtomicReference<Boolean> bIsValidMode, AtomicReference<Boolean> bCanContinue) {
		bIsValidMode.set(false);
		bCanContinue.set(false);
		
		switch( m_enRuleType) {
			case MOVE_TRANSIENT:
				bIsValidMode.set(false);
				bCanContinue.set(true);
				break;
			case MOVE:
				if( oPosition.getPiece() != null) {
					bIsValidMode.set(false);
					bCanContinue.set(false);
				} else {
					bIsValidMode.set(true);
					bCanContinue.set(true);
				}
				break;
			case MOVE_AND_CAPTURE:
				bIsValidMode.set(true);
				if( oPosition.getPiece() == null) {
					bCanContinue.set(true);
				}
				break;
			case MOVE_IFF_CAPTURE_POSSIBLE:
				if( oPosition.getPiece() == null) {
					bCanContinue.set(true);
				}
				else
				{
					bIsValidMode.set(true);					
					bCanContinue.set(false);
				}
				break;
		}
	}

	public void canContinue( IPosition oPosition) {
		
	}

}
