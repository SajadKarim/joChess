package jchess.dimodule;

import com.google.inject.Injector;

/**
 * This interface ensures that the manager class do handle all the dependency modules.
 * Its goal is to hold all the dependency related modules and provide it to others when necessary.
 * 
 * @author  Sajad Karim
 * @since	14 Dec 2019
 */

public interface IDIManager {
	public void setGlobalInjector(Injector oGlobalInjector);
	public Injector createChildInjectorForNewGameModule();
	public Injector createChildInjectorForGameModule(String stGameId, String stBoardName, String stBoardFileName);
}
