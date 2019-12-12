package jchess.cache;

import java.util.*;

import jchess.common.IBoardMapping;

/**
 * This class holds Board mapping. It facilitates Rule logic by making Board layout similar at logic layer.
 * I hope you did not understand above description: I shall add more description later.
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public class BoardMapping implements IBoardMapping {
    Map m_dictMappings = null; 

    public BoardMapping() {
    	m_dictMappings = new Hashtable();
    }
    
    public int getMapping( int nSource) {
    	Object oDestination = m_dictMappings.get((Object)nSource);
    	
    	if( oDestination == null )
    		return nSource;
    	
    	return (int)oDestination;
    }
    
    public void addMapping( int nSource, int nDestination) {
    	m_dictMappings.put(nSource, nDestination);
    }

}
