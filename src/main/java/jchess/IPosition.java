package jchess;

public interface IPosition {
	public int getFile();
	public int getRank();
	
	public String getName();
	public void AddEdge(IPosition oPosition);
	public void AddVertex(IPosition oPosition);
	
	public IShape getShape();

	public void setPiece(Object object);
	public Object getPiece();
}
