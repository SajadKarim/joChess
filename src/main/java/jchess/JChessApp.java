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

import javax.swing.SwingUtilities;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import jchess.cache.BoardData;
import jchess.cache.CacheManager;
import jchess.cache.ICacheManager;
import jchess.common.IBoardData;
import jchess.gamelogic.BoardAgent;
import jchess.service.StorageType;
import jchess.service.StorageService;

/**
 * The main class of the application.
 */
public class JChessApp extends SingleFrameApplication {
	
	//private final static JChessLogger logger = JChessLogger.getInstance();
	
     static JChessView jcv;
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {

    	CacheManager.provideCacheManager().init();
    	//StorageService o = StorageService.create(STORAGE_TYPE.FBDB);
    	//IBoardData b = o.getBoard();
    	
    	jcv = new JChessView(this);
        show(jcv);
    	
    	//final viewTemp view = new viewTemp();
        //final modelTemp model = new modelTemp();
        //new presenterTemp(view, model);

    }

    public JChessApp() {
    	//final viewTemp view = new viewTemp();
        //final modelTemp model = new modelTemp();
        //new presenterTemp(view, model);
    }
    
    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of JChessApp
     */
    public static JChessApp getApplication() {
        return Application.getInstance(JChessApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
    	/*SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	new JChessApp();
            }
        });
    	*/
        launch(JChessApp.class, args);    	
        JChessLogger.getInstance().log(level.INFO, "Launching Game");
    }
}
