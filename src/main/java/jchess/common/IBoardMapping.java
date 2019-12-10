package jchess.common;

public interface IBoardMapping {
    public int getMapping( int nSource);    
    public void addMapping( int nSource, int nDestination);
}
