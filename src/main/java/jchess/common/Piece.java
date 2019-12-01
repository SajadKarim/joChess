package jchess.common;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import jchess.common.enumerator.Direction;
import jchess.common.enumerator.Family;
import jchess.common.enumerator.File;
import jchess.common.enumerator.Manoeuvre;
import jchess.common.enumerator.Rank;
import jchess.common.enumerator.RuleType;

public class Piece implements IPiece {
	String m_stName;
	String m_stImagePath;
	List<Rule> m_lstRules;
	
	public Piece(String stName, String stImagePath) {
		m_stName = stName;
		m_stImagePath = stImagePath;
		
		m_lstRules = new ArrayList<Rule>();
        m_lstRules.add( new Rule(RuleType.MOVE_AND_CAPTURE, Direction.VERTEX, Manoeuvre.BLINKER, 1000, Family.SAME, File.IGNORE, Rank.IGNORE, true, null));
        m_lstRules.add( new Rule(RuleType.MOVE_AND_CAPTURE, Direction.EDGE, Manoeuvre.BLINKER, 3000, Family.IGNORE, File.IGNORE, Rank.IGNORE, true, null));

	}
	
	public Piece(Piece oPiece) {
		this.m_stName = oPiece.m_stName;
		this.m_stImagePath = oPiece.m_stImagePath;
		
		this.m_lstRules = new ArrayList<Rule>(oPiece.m_lstRules);
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
	
	public List<Rule> getAllRules(){
		return m_lstRules;
	}
	
	public void addRule(Rule oRule) {
		m_lstRules.add(oRule);
	}
}
