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
    private Thread m_oThread;
	private int m_nTimerLengthInSeconds;
	private int m_nTimerRemainingSeconds;	
	private int m_nTimerRecurrenceCount;
	private int m_nPauseTimerBeforeNextRecurrence;	
	private Boolean m_bNotifyEverySecond;
	private Boolean m_bNotifyWhenTimerEnds;
	
	private Boolean m_bActive;
	
    private final ArrayList<ITimerListener> m_lstLiteners;

    @Inject
	public Timer() {
        m_oThread = new Thread(this);
        m_lstLiteners = new ArrayList<ITimerListener>();
        
        m_bActive = false;
	}
	
    public void start(int nTimerLengthInSeconds, 
			int nTimerRecurrenceCount, 
			int nPauseTimerBeforeNextRecurrence, 
			Boolean bNotifyEverySecond, 
			Boolean bNotifyWhenTimerEnds) {
		m_nTimerLengthInSeconds = m_nTimerRemainingSeconds = nTimerLengthInSeconds;
		m_nTimerRecurrenceCount = nTimerRecurrenceCount;
		m_nPauseTimerBeforeNextRecurrence = nPauseTimerBeforeNextRecurrence;
		m_bNotifyEverySecond = bNotifyEverySecond;
		m_bNotifyWhenTimerEnds = bNotifyWhenTimerEnds;
		
		m_bActive = true;
        m_oThread.start();
    }

    public void stop() {
    	m_nTimerLengthInSeconds = 0;
    	m_nTimerRecurrenceCount = 0;
    	m_bActive = false;
    	
        /*try {
        	m_oThread.wait();
        } catch (InterruptedException exc) {
            System.out.println("Error blocking thread: " + exc);
        } catch (IllegalMonitorStateException exc1) {
            System.out.println("Error blocking thread: " + exc1);
        }
        */
    }


	@Override
    public void run() {
        while (m_nTimerRecurrenceCount > 0) {
			while (m_nTimerRemainingSeconds > 0) {	        	
	        	if (m_bNotifyEverySecond) {
	        		notifyListenersOnSecondElapsed(m_nTimerRemainingSeconds);
	        	}
	        	
	            try {
	                m_oThread.sleep(1000);
	            } catch (InterruptedException e) {
	                System.out.println("Some error in gameClock thread: " + e);
	            }
	
	            m_nTimerRemainingSeconds--;

	            if( !m_bActive)
	        		break;
	        }

        	if (m_bNotifyEverySecond) {
        		notifyListenersOnTimerEnds();
        	}
        	
        	m_nTimerRemainingSeconds = m_nTimerLengthInSeconds;
        	
        	m_nTimerRecurrenceCount--;
        	
        	if( !m_bActive)
        		break;
        }
        
        //this.stop();
    }

	
	
    private void timeOver() {
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

}
