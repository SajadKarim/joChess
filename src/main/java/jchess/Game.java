package jchess;

import jchess.util.Timer;
import jchess.presenter.IGamePresenter;
import jchess.util.ITimerListener;

public class Game implements ITimerListener{
	private IGamePresenter m_oGamePresenter;
	private Timer m_oTimer;
	
	public Game(IGamePresenter oGamePresenter){
		m_oGamePresenter = oGamePresenter;
		
		m_oTimer = new Timer(60*1, 100000, 0, true, true);
		m_oTimer.addListener(this);
		
		m_oTimer.start();
	}
	
	@Override
	public void onSecondElapsed(int nRemainingSeconds) {
		m_oGamePresenter.timerUpdateForRemainingSeconds(nRemainingSeconds);
	}

	@Override
	public void onTimerEnds() {
		m_oGamePresenter.timerElapsed();
		
	}
}
