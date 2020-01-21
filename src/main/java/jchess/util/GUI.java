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

/*
 * Authors:
 * Mateusz Sławomir Lach ( matlak, msl )
 * Damian Marciniak
 */
package jchess.util;

import java.awt.*;
import java.net.*;
import java.io.*;

import java.util.Properties;
import java.io.FileOutputStream;

/** Class representing the game interface which is seen by a player and
 * where are lockated available for player opptions, current games and where
 * can he start a new game (load it or save it)
 */
public class GUI
{
    static final public Properties configFile = GUI.getConfigFile();

    /*Method load image by a given name with extension
     * @name     : string of image to load for ex. "chessboard.jpg"
     * @returns  : image or null if cannot load
     * */

    public static Image loadImage(String name)
    {
        if (configFile == null)
        {
            return null;
        }
        Image img = null;
        URL url = null;
        Toolkit tk = Toolkit.getDefaultToolkit();
        try
        {
            String imageLink = GUI.getJarPath() + "/theme/" + configFile.getProperty("THEME", "default") + "/images/" + name;
            img = tk.getImage(new File(imageLink).toURI().toURL());
        }
        catch (Exception e)
        {
            System.out.println("some error loading image");
            e.printStackTrace();
        }
        return img;
    }/*--endOf-loadImage--*/


    public static boolean themeIsValid(String name)
    {
        return true;
    }

    public static String getJarPath()
    {
        String path = GUI.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        path = path.replaceAll("[a-zA-Z0-9%!@#$%^&*\\(\\)\\[\\]\\{\\}\\.\\,\\s]+\\.jar", "");
        int lastSlash = path.lastIndexOf(File.separator); 
        if(path.length()-1 == lastSlash)
        {
            path = path.substring(0, lastSlash);
        }
        path = path.replace("%20", " ");
        return path;
    }

    public static Properties getConfigFile() {
        Properties configFile= new Properties();
        File fsConfigFile = new File(GUI.getJarPath() + File.separator + "config.txt");

        try {
        	if (!fsConfigFile.exists()) {
            	configFile.store(new FileOutputStream(fsConfigFile), null);
        	}

        	configFile.load(new FileInputStream(fsConfigFile.getPath()));
        }
        catch (java.io.IOException exc) {
        }

        return configFile;
    }
}
