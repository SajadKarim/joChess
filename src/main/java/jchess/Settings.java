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
package jchess;

import java.io.Serializable;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/** Class representings game settings available for the current player
 */
public class Settings implements Serializable {
    private static ResourceBundle loc = null;
 
    public static String lang(String key) {
        if (Settings.loc == null) {
            Settings.loc = PropertyResourceBundle.getBundle("jchess.main");
            Locale.setDefault(Locale.ENGLISH);
        }
        
        String result = "";
        
        try {
            result = Settings.loc.getString(key);
        } catch (java.util.MissingResourceException exc) {
            result = key;
        }

        return result;
    }
}
