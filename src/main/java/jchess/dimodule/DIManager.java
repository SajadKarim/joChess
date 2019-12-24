package jchess.dimodule;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * This is the manager class to handle all the dependency modules.
 * Its goal is to hold all the dependency related modules and provide it to others when necessary.
 * 
 * @author  Sajad Karim
 * @since	14 Dec 2019
 */

@Singleton
public class DIManager implements IDIManager {
	private Injector m_oGlobalInjector;
	
	@Inject
	public DIManager() {
		
	}
	
	public void setGlobalInjector(Injector oGlobalInjector) {
		m_oGlobalInjector = oGlobalInjector;
	}
	
	public Injector createChildInjectorForNewGameModule() {
    	return m_oGlobalInjector.createChildInjector(new NewGameWndModule(m_oGlobalInjector));
	}

	public Injector createChildInjectorForGameModule(String stGameId, String stBoardName) {
    	return m_oGlobalInjector.createChildInjector(new GameWndModule(m_oGlobalInjector, stGameId, stBoardName));
	}
}
