package jchess.util;

import java.util.ArrayList;

import com.google.inject.Inject;

/**
 * This class is responsible to launch Timer and notify others.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public final class Timer implements ITimer {
	private java.util.Timer m_oTimer;
	private int m_nTimerRemainingSeconds;	
    private final ArrayList<ITimerListener> m_lstLiteners;

    @Inject
	public Timer() {
        m_lstLiteners = new ArrayList<ITimerListener>();
	}
	
    class NotifyTask extends java.util.TimerTask {
        public void run() {
        	m_nTimerRemainingSeconds--;
        	
        	if (m_nTimerRemainingSeconds == 0) {
            	m_oTimer.cancel();
        		notifyListenersOnTimerEnds();
        		return;
        	}

    		notifyListenersOnSecondElapsed(m_nTimerRemainingSeconds);
        }
    }
    
    public void start(int nTimerRemainingSeconds) {
    	m_nTimerRemainingSeconds = nTimerRemainingSeconds;
    	m_oTimer = new 	java.util.Timer();
		m_oTimer.schedule(new NotifyTask(), 1 * 1000, 1 * 1000);
	}

    public void stop() {
    	m_oTimer.cancel();
    }
    
    private void notifyListenersOnSecondElapsed(int nRemainingSeconds) {
        for (final ITimerListener listener : m_lstLiteners) {
            listener.onSecondElapsed(nRemainingSeconds);
        }
    }

    private void notifyListenersOnTimerEnds() {
        for (final ITimerListener listener : m_lstLiteners) {
            listener.onTimerElapsed();
        }
    }

    public void addListener(final ITimerListener listener) {
    	m_lstLiteners.add(listener);
    }

    public int getTimerRemainingSeconds() {
    	return m_nTimerRemainingSeconds;
    }
}
