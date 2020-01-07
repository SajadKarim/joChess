package jchess.gamelogic;

import org.javatuples.Pair;

import com.google.inject.Inject;

import jchess.common.IBoardAgent;
import jchess.common.IMove;
import jchess.common.IMoveCandidacy;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;
import jchess.common.enumerator.RuleEngineType;
import jchess.gui.IGUIHandle;
import jchess.gui.IGUIManager;
import jchess.gui.model.gamewindow.IGameModel;
import jchess.ruleengine.IRuleEngine;
import jchess.ruleengine.RuleEngineFactory;
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
	private IRuleEngine m_oRuleProcessor;
	private ArrayList<IGameListener> m_lstListener;
	private IGUIHandle m_oGUIHandle;
	private IAppLogger m_oLogger;
	
	@Inject
	public Game(IGameModel oGameModel, ITimer oTimer, IGUIHandle oGUIHandle, IAppLogger oLogger, IGameState oGameState, IRuleEngine oRuleEngine){
		m_oLogger = oLogger;
		
		oLogger.writeLog(LogLevel.DETAILED, "Instantiating Game.", "Game", "Game");
		
		m_oTimer = oTimer;
		m_oGUIHandle = oGUIHandle;
		m_lstListener = new ArrayList<IGameListener>(); 
		
		m_oGameState = oGameState;
		m_oGameModel = oGameModel;

		m_oRuleProcessor  = oRuleEngine;
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
	/**
	 * Keep track on the time. Notify when all the time has been elapsed
	 */
	@Override
	public void onTimerElapsed() {
		m_oLogger.writeLog(LogLevel.DETAILED, "All the time has been elasped.", "onTimerElasped", "Game");

		deselectedActivePosition();
		m_oGameState.switchPlayTurn();
		notifyListenersOnTimerUpdate_TimerElapsed(m_oGameState.getActivePlayer());		
	}
	/**
	 * Manage the activity on board
	 * @param oPosition position the mouse clicked
	 */
	public void onBoardActivity(IPositionAgent oPosition) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Activity on board has been observed.", "onBoardActivity", "Game");

		if( m_oGameState.getActivePlayer() == null) {
			m_oLogger.writeLog(LogLevel.DETAILED, "There is not Active player.", "onBoardActivity", "Game");
			return;
		}
		
		if( m_oGameState.getActivePosition() == null) {
			if( oPosition.getPiece() != null && m_oGameState.isThisActivePlayer(oPosition.getPiece().getPlayer().getName())) {
				m_oLogger.writeLog(LogLevel.DETAILED, "Player selected a new Position.", "onBoardActivity", "Game");
				tryFinalAllPossibleMoveCandidates(oPosition);
				return;
			}
		} else {	
			if( m_oGameState.isThisActivePosition(oPosition.getName())) {
				m_oLogger.writeLog(LogLevel.DETAILED, "Player clicked on the selected Position.", "onBoardActivity", "Game");
				deselectedActivePosition();
				return;
			}

			if( oPosition.getPiece() != null && m_oGameState.isThisActivePlayer(oPosition.getPiece().getPlayer().getName())) {
				m_oLogger.writeLog(LogLevel.DETAILED, "Player clicked on some other Piece.", "onBoardActivity", "Game");
				tryFinalAllPossibleMoveCandidates(oPosition);
				return;
			}
		
			IMoveCandidacy oMoveCandidate =m_oGameState.getMoveCandidate(oPosition); 
			if( oMoveCandidate != null) {
				m_oLogger.writeLog(LogLevel.DETAILED, "Player clicked on one of the Move candidancies.", "onBoardActivity", "Game");
				oMoveCandidate.setSourcePosition(m_oGameState.getActivePosition());
				tryExecuteRule(oMoveCandidate);
				return;
			} 
			
			m_oLogger.writeLog(LogLevel.DETAILED, "Player clicked on some inactive Position.", "onBoardActivity", "Game");
			deselectedActivePosition();
		}		
	}
	/**
	 * Try executing the move 
	 * @param oMoveCandidate the Move candinate
	 */
	public void tryExecuteRule(IMoveCandidacy oMoveCandidate) {
		m_oLogger.writeLog(LogLevel.INFO, "Trying to make move." + oMoveCandidate.toLog(), "tryExecuteRule", "Game");

		deselectedActivePosition();
		IMove oMove = m_oRuleProcessor.tryExecuteRule(m_oGameModel.getBoard(), oMoveCandidate);

		m_oGameState.switchPlayTurn();
		notifyListenersOnMoveMadeByPlayer(m_oGameState.getActivePlayer(), oMove);
		notifyListenersOnCurrentPlayerChanged(m_oGameState.getActivePlayer());
	}
	/**
	 * Deselect active position, deselect all the move candidate
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
	 * Try to find all the possible move candidate of the piece
	 * @param oPosition the selected piece position
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
		
		Map<String, IMoveCandidacy> mpCandidateMovePosition = m_oRuleProcessor.tryEvaluateAllRules(m_oGameModel.getBoard(), oPosition);
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
	/**
	 * Notify listener the second elapse from the clock
	 * @param nRemainingSeconds the remaining second of the clock
	 */
	public void notifyListenersOnTimerUpdate_SecondsElapsed(int nRemainingSeconds) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Notifying about second elapse.", "notifyListenersOnTimerUpdate_SecondsElapsed", "Game");

        for (final IGameListener oListener : m_lstListener) {
            oListener.onTimerUpdate_SecondsElapsed(nRemainingSeconds);
        }	
	}
	/**
	 * Notify listener the time has elapse
	 * @param oPlayer player's time elapse
	 */
	public void notifyListenersOnTimerUpdate_TimerElapsed(IPlayerAgent oPlayer) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Notifying about timer elapse.", "notifyListenersOnTimerUpdate_TimerElapsed", "Game");

		for (final IGameListener oListener : m_lstListener) {
            oListener.onTimerUpdate_TimerElapsed(oPlayer);
        }	
	}
	/**
	 * Notify listener change current player to next player
	 * @param oPlayer next player
	 */
	public void notifyListenersOnCurrentPlayerChanged(IPlayerAgent oPlayer) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Notifying about player change.", "notifyListenersOnCurrentPlayerChanged", "Game");

        for (final IGameListener oListener : m_lstListener) {
            oListener.onCurrentPlayerChanged(oPlayer);
        }	
	}
	/**
	 * Notify listener the recent move of the current player
	 * @param oPlayer current player
	 * @param oMove recent move
	 */
	public void notifyListenersOnMoveMadeByPlayer(IPlayerAgent oPlayer, IMove oMove) {		
		m_oLogger.writeLog(LogLevel.DETAILED, "Notifying about move made by player.", "notifyListenersOnMoveMadeByPlayer", "Game");

		for (final IGameListener oListener : m_lstListener) {
            oListener.onMoveMadeByPlayer(oPlayer, oMove);
        }	
	}
	/**
	 * Try undo the move
	 * @param oPlayer current player
	 */
	public void tryUndoMove(IPlayerAgent oPlayer) {
		m_oLogger.writeLog(LogLevel.DETAILED, "Undoing move.", "tryUndoMove", "Game");

		deselectedActivePosition();
		while( !m_oGameState.getActivePlayer().equals(oPlayer)) {
			m_oGameState.switchPlayTurn();
		}			
		notifyListenersOnCurrentPlayerChanged(m_oGameState.getActivePlayer());
	}
	/**
	 * Try to redo (undo your undo)
	 * @param oPlayer current player
	 */
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
