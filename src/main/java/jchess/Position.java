package jchess;

import java.util.*;

enum Type{
	SAME,
	DIFFERENT
}

public class Position implements IPosition{
	List<IPosition> m_lstEdge;
	List<IPosition> m_lstVertex;
	IShape m_oShape;
	int m_nFile;
	int m_nRank;
	Object m_oPiece;
	
	public Position( String stName, IShape oShape) {
		m_nFile = (int)stName.charAt(0);
		m_nRank = (int)stName.charAt(1);

		m_oShape = oShape;
		
		m_lstEdge = new ArrayList<IPosition>();
		m_lstVertex = new ArrayList<IPosition>();
	}
	
	public void AddEdge(IPosition oPosition) {
		m_lstEdge.add(oPosition);
	}

	public void AddVertex(IPosition oPosition) {
		m_lstVertex.add(oPosition);
	}
	
	public String getName() {
		return "" + (char)m_nFile + "" + (char)m_nRank;
	}

	public IShape getShape() {
		return m_oShape;
	}

	public List<IPosition> getEdges(){
		return m_lstEdge;
	}

	public List<IPosition> getVertexes(){
		return m_lstVertex;
	}
	
	public int getFile() {
		return m_nFile;
	}
	
	public int getRank() {
		return m_nRank;
	}

	public void setPiece(Object object) {
		this.m_oPiece = object;
	}

	public Object getPiece() {
		return m_oPiece;
	}

}
