/*
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jchess;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.View;

import com.google.inject.Guice;
import com.google.inject.Injector;

import jchess.dimodule.DIManager;
import jchess.dimodule.GlobalModule;
import jchess.dimodule.IDIManager;
import jchess.gui.GUIManager;
import jchess.gui.IGUIManager;
import jchess.util.AppLogger;
import jchess.util.IAppLogger;
import jchess.util.LogLevel;

/**
 * The main class of the application.
 */
public class Main extends SingleFrameApplication implements IMain{
	private static IAppLogger m_oLogger; 
 	private static Injector m_oGlobalModuleInjector;

 	private static IGUIManager m_oGUIManager;
     /**
     * At startup create and show the main frame of the application.
     */
    @Override 
    protected void startup() {
     	m_oLogger.writeLog(LogLevel.INFO, "Launching main window.", "startup", "Main");

    	// Initializing and launching main window.
    	m_oGUIManager.showMainWindow();
    }
    
    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    public void showView(View oView) {
    	this.show(oView);
    }
    
    public void showDialog(JDialog oDialog) {
    	this.show(oDialog);
    }
    
    public JFrame getAppMainFrame() {
    	return Application.getInstance(Main.class).getMainFrame();
    }
    
    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
    	// Initializing DI for objects that should instantiate once.
    	m_oGlobalModuleInjector = Guice.createInjector(new GlobalModule());

    	IDIManager m_oDIManager = m_oGlobalModuleInjector.getInstance(DIManager.class);
    	m_oDIManager.setGlobalInjector(m_oGlobalModuleInjector);
    	
    	// Acquiring AppLogger.
    	m_oLogger = m_oGlobalModuleInjector.getInstance(AppLogger.class);    	
    	m_oLogger.writeLog(LogLevel.INFO, "Game startup.");
    	
    	m_oGUIManager = m_oGlobalModuleInjector.getInstance(GUIManager.class);    	

    	// **IMPORTANT
    	// TODO: before launching main window, we need to call CacheManager and load cache with preliminary details about all the boards available.
    	
    	// Launching main window.
        launch(Main.class, args);
    }
}
