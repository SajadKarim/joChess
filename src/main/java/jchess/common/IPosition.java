package jchess.common;

import java.util.List;

public interface IPosition {
	public int getFile();
	public int getRank();
	public String getName();
	public String getCategory();
	public IShape getShape();
	public IPath getPathByName(String stName);
	public List<IPath> getPaths();
	public IPiece getPiece();
	public List<IPosition> tryGetOppositePath( IPosition oPosition);
	public void setPiece(Piece oPiece);
	
	public Boolean getSelectState();
	public void setSelectState(Boolean oSelect);
	public Boolean getMoveCandidacy();
	public void setMoveCandidacy(Boolean bMoveCandidacy);
}
