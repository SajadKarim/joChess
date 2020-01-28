package jchess.ruleengine;

import jchess.gui.GUIManager;
import jchess.util.IAppLogger;

/**
 * Pawn promotion functionality calls GUI manager to show piece selection window.
 * To test its functionality it was needed to override it here and make a perdefined selection.
 * 
 * This was extended to achieve test functionality but I need to it in a better and clean way.
 * 
 * @author	Sajad Karim
 * @since	14 Dec 2019
 */

public final class GUIManagerTest extends GUIManager {

	public GUIManagerTest(IAppLogger oLogger) {
		super(null, null, null, oLogger, null);
		// TODO Auto-generated constructor stub
	}

}
