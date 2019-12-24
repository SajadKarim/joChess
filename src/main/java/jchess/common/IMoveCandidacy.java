package jchess.common;

public interface IMoveCandidacy {
	public IPositionAgent getSourcePosition();
	public void setSourcePosition(IPositionAgent oPosition);
	public IPositionAgent getCandidatePosition();
	public IRuleAgent getRule();
}
