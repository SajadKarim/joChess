package jchess.common;

/**
 * 
 * @author 	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IBoardMapping {
    public int getMapping(int nSource);    
    public void addMapping(int nSource, int nDestination);
}
