package jchess.common;

/**
 * IMoveCandidacy
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IMoveCandidacy {
	public IPositionAgent getSourcePosition();
	public void setSourcePosition(IPositionAgent oPosition);
	public IPositionAgent getCandidatePosition();
	public IRuleAgent getRule();
	public String toLog();
}
