package jchess.cache;

import java.util.*;

import jchess.common.IBoardMapping;

public class BoardMapping implements IBoardMapping {
    Dictionary m_dictMappings = null; 

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
