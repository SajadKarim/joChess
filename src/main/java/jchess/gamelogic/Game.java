package jchess.gamelogic;

import com.google.inject.Inject;

import jchess.common.IMove;
import jchess.common.IMoveCandidacy;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.gui.IGUIHandle;
import jchess.gui.model.gamewindow.IGameModel;
import jchess.ruleengine.IRuleEngine;
import jchess.util.IAppLogger;
import jchess.util.ITimer;
import jchess.util.ITimerListener;
import jchess.util.LogLevel;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class is responsible to perform operations against any activity on Board.
 * It is basically a manager class which with the help of other classes manages
 * state of the board and make other necessary operations related to Board.
 * E.g 
 * It is responsible to find possible moves and make movements.
 * It is also keeps track of the timer.
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public class Game implements IGame, ITimerListener{
	private ITimer m_oTimer;
	private IGameModel m_oGameModel;
	private IGameState m_oGameState;
	private IRuleEngine m_oRuleEngine;
	private ArrayList<IGameListener> m_lstListener;
	private IGUIHandle m_oGUIHandle;
	private IAppLogger m_oLogger;
	
	/**
	 * Constructor for Game.
	 * 
	 * @param oGameModel
	 * @param oTimer
	 * @param oGUIHandle
	 * @param oLogger
	 * @param oGameState
	 * @param oRuleEngine
	 */
	@Inject
	public Game(IGameModel oGameModel, ITimer oTimer, IGUIHandle oGUIHandle, IAppLogger oLogger, IGameState oGameState, IRuleEngine oRuleEngine){
		m_oLogger = oLogger;
		
		oLogger.writeLog(LogLevel.DETAILED, "Instantiating Game.", "Game", "Game");
		
		m_oTimer = oTimer;
		m_oGUIHandle = oGUIHandle;
		m_lstListener = new ArrayList<IGameListener>(); 
		
		m_oGameState = oGameState;
		m_oGameModel = oGameModel;

		m_oRuleEngine  = oRuleEngine;
	}
	
	public void init() {
		m_oLogger.writeLog(LogLevel.DETAILED, "Instializing Game.", "init", "Game");

		notifyListenersOnCurrentPlayerChanged(m_oGameState.getActivePlayer());		
		
		m_oTimer.addListener(this);		
		m_oTimer.start(60*60, 10000, 0, true, true);
	}
	
	@Override
	public void onSecondElapsed(int nRemainingSeconds) {
		notifyListenersOnTimerUpdate_SecondsElapsed(nRemainingSeconds);
	}

	@Override
	public void onTimerElapsed() {
		m_oLogger.writeLog(LogLevel.DETAILED, "All the time has been elasped.", "onTimerElasped", "Game");

		deselectedActivePosition();
		m_oGameState.switchPlayTurn();
		notifyListenersOnTimerUpdate_TimerElapsed(m_oGameState.getActivePlayer());		
	}
	
	/**
	 * This method is called when user makes a click on the Board.
	 */
	public void onBoardActivity(IPositionAgent oPosition) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Activity on board has been observed.", "onBoardActivity", "Game");

		// This checks if there is any Player who is assigned turn to make a move.
		if( m_oGameState.getActivePlayer() == null) {
			m_oLogger.writeLog(LogLevel.ERROR, "There is not Active player.", "onBoardActivity", "Game");
			return;
		}
		
		if( m_oGameState.getActivePosition() == null) {
			// Following code finds and marks all the possible candidate positions that the selected Piece can take.			
			if( oPosition.getPiece() != null && m_oGameState.isThisActivePlayer(oPosition.getPiece().getPlayer().getName())) {
				m_oLogger.writeLog(LogLevel.DETAILED, "Player selected a new Position.", "onBoardActivity", "Game");
				tryFinalAllPossibleMoveCandidates(oPosition);
				return;
			}
		} else {	
			// Following code deselected the currently marked candidate moves as user clicked on a different location than the marked ones.
			if( m_oGameState.isThisActivePosition(oPosition.getName())) {
				m_oLogger.writeLog(LogLevel.DETAILED, "Player clicked on the selected Position.", "onBoardActivity", "Game");
				deselectedActivePosition();
				return;
			}

			// User selected a different piece than the one it made last time and following code finds and marks all the possible 
			// candidate positions that the selected Piece can take.			
			if( oPosition.getPiece() != null && m_oGameState.isThisActivePlayer(oPosition.getPiece().getPlayer().getName())) {
				m_oLogger.writeLog(LogLevel.DETAILED, "Player clicked on some other Piece.", "onBoardActivity", "Game");
				tryFinalAllPossibleMoveCandidates(oPosition);
				return;
			}
		
			// User selected one of the marked candidate positions and hence triggered Rule execution logic.
			IMoveCandidacy oMoveCandidate =m_oGameState.getMoveCandidate(oPosition); 
			if( oMoveCandidate != null) {
				m_oLogger.writeLog(LogLevel.DETAILED, "Player clicked on one of the Move candidancies.", "onBoardActivity", "Game");
				oMoveCandidate.setSourcePosition(m_oGameState.getActivePosition());
				tryExecuteRule(oMoveCandidate);
				return;
			} 
			
			// User click on some inactive location.
			m_oLogger.writeLog(LogLevel.DETAILED, "Player clicked on some inactive Position.", "onBoardActivity", "Game");
			deselectedActivePosition();
		}		
	}
		
	/**
	 * Following method triggers Rule execution code.
	 * 
	 * @param oMoveCandidate - Details about the candidate position user selected to proceed with.
	 */
	public void tryExecuteRule(IMoveCandidacy oMoveCandidate) {
		m_oLogger.writeLog(LogLevel.INFO, "Trying to make move." + oMoveCandidate.toLog(), "tryExecuteRule", "Game");

		deselectedActivePosition();
		IMove oMove = m_oRuleEngine.tryExecuteRule(m_oGameModel.getBoard(), oMoveCandidate);

		m_oGameState.switchPlayTurn();
		notifyListenersOnMoveMadeByPlayer(m_oGameState.getActivePlayer(), oMove);
		notifyListenersOnCurrentPlayerChanged(m_oGameState.getActivePlayer());
	}
	
	/**
	 * Deselects all the currently marked positions.
	 */
	public void deselectedActivePosition() {
		m_oLogger.writeLog(LogLevel.INFO, "Deslecting all the move candidancies.", "deselectedActivePosition", "Game");

		if( m_oGameState.getPossibleMovesForActivePosition() != null) {

			for (Map.Entry<String, IMoveCandidacy> entry : m_oGameState.getPossibleMovesForActivePosition().entrySet()) {
				entry.getValue().getCandidatePosition().setMoveCandidacy(false);
	    	}		
		}

		if( m_oGameState.getActivePosition() != null) {			
			m_oGameState.getActivePosition().setSelectState(false);
		}
		
		m_oGameState.setPossibleMovesForActivePosition(null);
    	m_oGameState.setActivePosition(null);
	}

	/**
	 * Finds all the possible positions that the Piece can make.
	 * 
	 * @param oPosition - It holds Piece and Rule information that is required to find out possible positions.
	 */
	public void tryFinalAllPossibleMoveCandidates(IPositionAgent oPosition){
		m_oLogger.writeLog(LogLevel.INFO, String.format("Finding all the possible moves for Position=[%s]", oPosition.toLog()), "tryFinalAllPossibleMoveCandidates", "Game");

		if( oPosition.getPiece() == null) {
			m_oLogger.writeLog(LogLevel.INFO, "No piece attached.", "tryFinalAllPossibleMoveCandidates", "Game");
			return;
		}
		
		if (!oPosition.getPiece().getPlayer().getName().equals(m_oGameState.getActivePlayer().getName())) {
			m_oLogger.writeLog(LogLevel.INFO, "Piece belongs to some other player.", "tryFinalAllPossibleMoveCandidates", "Game");
			return;
		}
		
		deselectedActivePosition();
		
		Map<String, IMoveCandidacy> mpCandidateMovePosition = m_oRuleEngine.tryEvaluateAllRules(m_oGameModel.getBoard(), oPosition);
		// TODO: log mpCandidateMovePosition 
		
		for (Map.Entry<String, IMoveCandidacy> itCandidateMovePosition : mpCandidateMovePosition.entrySet()) {
			itCandidateMovePosition.getValue().getCandidatePosition().setMoveCandidacy(true);
    	}

    	oPosition.setSelectState(true);
    	
    	m_oGameState.setActivePosition(oPosition);
    	m_oGameState.setPossibleMovesForActivePosition( mpCandidateMovePosition);
	}
	
	public void addListener(final IGameListener oListener) {
        m_lstListener.add(oListener);
    }

	public void notifyListenersOnTimerUpdate_SecondsElapsed(int nRemainingSeconds) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Notifying about second elapse.", "notifyListenersOnTimerUpdate_SecondsElapsed", "Game");

        for (final IGameListener oListener : m_lstListener) {
            oListener.onTimerUpdate_SecondsElapsed(nRemainingSeconds);
        }	
	}
	
	public void notifyListenersOnTimerUpdate_TimerElapsed(IPlayerAgent oPlayer) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Notifying about timer elapse.", "notifyListenersOnTimerUpdate_TimerElapsed", "Game");

		for (final IGameListener oListener : m_lstListener) {
            oListener.onTimerUpdate_TimerElapsed(oPlayer);
        }	
	}
	
	public void notifyListenersOnCurrentPlayerChanged(IPlayerAgent oPlayer) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Notifying about player change.", "notifyListenersOnCurrentPlayerChanged", "Game");

        for (final IGameListener oListener : m_lstListener) {
            oListener.onCurrentPlayerChanged(oPlayer);
        }	
	}

	public void notifyListenersOnMoveMadeByPlayer(IPlayerAgent oPlayer, IMove oMove) {		
		m_oLogger.writeLog(LogLevel.DETAILED, "Notifying about move made by player.", "notifyListenersOnMoveMadeByPlayer", "Game");

		for (final IGameListener oListener : m_lstListener) {
            oListener.onMoveMadeByPlayer(oPlayer, oMove);
        }	
	}

	public void tryUndoMove(IPlayerAgent oPlayer) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Undoing move.", "tryUndoMove", "Game");

		deselectedActivePosition();
		while( !m_oGameState.getActivePlayer().equals(oPlayer)) {
			m_oGameState.switchPlayTurn();
		}			
		notifyListenersOnCurrentPlayerChanged(m_oGameState.getActivePlayer());
	}

	public void tryRedoMove(IPlayerAgent oPlayer) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Redoing move.", "tryRedoMove", "Game");

		deselectedActivePosition();
		while( !m_oGameState.getActivePlayer().equals(oPlayer)) {
			m_oGameState.switchPlayTurn();
		}
			
		m_oGameState.switchPlayTurn();

		notifyListenersOnCurrentPlayerChanged(m_oGameState.getActivePlayer());
	}
}
