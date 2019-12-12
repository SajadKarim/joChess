package jchess.dimodule;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;

/**
 * This class binds all the dependencies that are required for Game window.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class GameWndModule extends AbstractModule {
	Injector m_oGlobalInjector = null;
	
	public GameWndModule(Injector oGlobalInjector) {
	}
	
	@Override 
	protected void configure() {
	}
}