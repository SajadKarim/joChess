package jchess.dimodule;

import com.google.inject.Injector;

public interface IDIManager {
	public void setGlobalInjector(Injector oGlobalInjector);
	public Injector createChildInjectorForNewGameModule();
	public Injector createChildInjectorForGameModule(String stGameId, String stBoardName, String stBoardFileName);
}
