package jchess.util;

import java.util.ArrayList;

import com.google.inject.Inject;

/**
 * This class is responsible to launch Timer and notify others.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public class Timer implements ITimer {
    private Thread m_oThread;
	private int m_nTimerLengthInSeconds;
	private int m_nTimerRemainingSeconds;	
	private int m_nTimerRecurrenceCount;
	private int m_nPauseTimerBeforeNextRecurrence;	
	private Boolean m_bNotifyEverySecond;
	private Boolean m_bNotifyWhenTimerEnds;
	
    private final ArrayList<ITimerListener> lstLiteners;

    @Inject
	public Timer() {
        m_oThread = new Thread(this);        
        lstLiteners = new ArrayList<ITimerListener>();
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
		
        m_oThread.start();
    }

    public void stop()
    {
    	m_nTimerLengthInSeconds = 0;
    	m_nTimerRecurrenceCount = 0;
    	
        try
        {
        	m_oThread.wait();
        }
        catch (java.lang.InterruptedException exc)
        {
            System.out.println("Error blocking thread: " + exc);
        }
        catch (java.lang.IllegalMonitorStateException exc1)
        {
            System.out.println("Error blocking thread: " + exc1);
        }
    }


	@Override
    public void run()
    {
        while (m_nTimerRecurrenceCount > 0)
        {
			while (m_nTimerRemainingSeconds > 0)
	        {	        	
	        	if( m_bNotifyEverySecond) {
	        		notifyListenersOnSecondElapsed(m_nTimerRemainingSeconds);
	        	}
	        	
	            try
	            {
	                m_oThread.sleep(1000);
	            }
	            catch (InterruptedException e)
	            {
	                System.out.println("Some error in gameClock thread: " + e);
	            }
	
	            m_nTimerRemainingSeconds--;
	        }

        	if( m_bNotifyEverySecond) {
        		notifyListenersOnTimerEnds();
        	}
        	
        	m_nTimerRemainingSeconds = m_nTimerLengthInSeconds;
        	
        	m_nTimerRecurrenceCount--;
        }
        
        this.stop();
    }

    private void timeOver()
    {
    }
    
    private void notifyListenersOnSecondElapsed(int nRemainingSeconds) {
        for (final ITimerListener listener : lstLiteners) {
            listener.onSecondElapsed(nRemainingSeconds);
        }
    }

    private void notifyListenersOnTimerEnds() {
        for (final ITimerListener listener : lstLiteners) {
            listener.onTimerElapsed();
        }
    }

    public void addListener(final ITimerListener listener) {
        lstLiteners.add(listener);
    }

}
