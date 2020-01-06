package jchess.dimodule;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

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

	public Injector createChildInjectorForGameModule(String stGameId, String stBoardName, String stBoardFileName) {
    	return m_oGlobalInjector.createChildInjector(new GameWndModule(m_oGlobalInjector, stGameId, stBoardName, stBoardFileName));
	}
}
