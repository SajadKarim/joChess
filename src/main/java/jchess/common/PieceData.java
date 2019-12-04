package jchess.common;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

public class PieceData implements IPieceData {
	String m_stName;
	String m_stImagePath;
	Map<String, IRuleData> m_mpRules;
	
	public PieceData() {
		m_mpRules = new HashMap<String, IRuleData>();
	}
	
	public PieceData(String stName, String stImagePath) {
		m_stName = stName;
		m_stImagePath = stImagePath;
		
		m_mpRules = new HashMap<String, IRuleData>();
//		m_mpRules.put("1", new RuleData("1", RuleType.MOVE_AND_CAPTURE, Direction.VERTEX, Manoeuvre.BLINKER, 1000, Family.SAME, File.IGNORE, Rank.IGNORE, true, null));
//		m_mpRules.put("2",  new RuleData("2", RuleType.MOVE_AND_CAPTURE, Direction.EDGE, Manoeuvre.BLINKER, 3000, Family.IGNORE, File.IGNORE, Rank.IGNORE, true, null));

	}
	
	public PieceData(PieceData oPiece) {
		this.m_stName = oPiece.m_stName;
		this.m_stImagePath = oPiece.m_stImagePath;
		
		this.m_mpRules = new HashMap<String, IRuleData>(oPiece.m_mpRules);
	}
	
	public String getName() {
		return m_stName;
	}
	
	public void setName(String stName) {
		m_stName = stName;
	}
	
	public String getImagePath() {
		return m_stImagePath;
	}
	
	public void setImagePath(String stImagePath) {
		m_stImagePath = stImagePath;
	}
	
	public List<IRuleData> getAllRules(){
		return new ArrayList<IRuleData>(m_mpRules.values());
	}
	
	public void addRule(IRuleData oRule) {
		m_mpRules.put(oRule.getName(), oRule);
	}
	
	public PieceData getPieceData() {
		return this;
	}
}
