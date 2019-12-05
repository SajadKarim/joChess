package jchess.presenter;

public interface IGamePresenter {
	public void timerUpdateForRemainingSeconds(int nRemainingSeconds);
	public void timerElapsed();
}
