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
public final class DIManager implements IDIManager {
	private Injector m_oGlobalInjector;
	
	@Inject
	/**
	 * Default constructor.
	 */
	public DIManager() {
		m_oGlobalInjector = null;
	}
	
	/**
	 * This is a setter method to update Injector for GlobalModule.
	 */
	public void setGlobalInjector(Injector oGlobalInjector) {
		m_oGlobalInjector = oGlobalInjector;
	}
	
	/**
	 * This method creates a child Injector instance for game module.
	 */
	public Injector createChildInjectorForNewGameModule() {
    	return m_oGlobalInjector.createChildInjector(new NewGameWndModule(m_oGlobalInjector));
	}

	/**
	 * This method creates a child Injector instance for new game window module.
	 */
	public Injector createChildInjectorForGameModule(String stGameId, String stBoardName, String stBoardFileName) {
    	return m_oGlobalInjector.createChildInjector(new GameWndModule(m_oGlobalInjector, stGameId, stBoardName, stBoardFileName));
	}
}
