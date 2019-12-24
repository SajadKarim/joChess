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
import jchess.util.ITimer;
import jchess.util.ITimerListener;

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
	
	@Inject
	public Game(IGameState oGameState, IGameModel oGameModel, ITimer oTimer, IGUIHandle oGUIHandle){
		m_oTimer = oTimer;
		m_oGUIHandle = oGUIHandle;
		m_lstListener = new ArrayList<IGameListener>(); 
		
		m_oGameState = oGameState;
		m_oGameModel = oGameModel;

		m_oRuleProcessor = RuleEngineFactory.getRuleEngine(RuleEngineType.convertStringToEnum(m_oGameModel.getRuleEngineName()));
		m_oRuleProcessor.setGUIHandle(m_oGUIHandle);
	}
	
	public void init() {		
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
		deselectedActivePosition();
		m_oGameState.switchPlayTurn();
		notifyListenersOnTimerUpdate_TimerElapsed(m_oGameState.getActivePlayer());		
		//m_oTimer.start();
	}
	
	public void onBoardActivity(IPositionAgent oPosition) {
		if( m_oGameState.getActivePlayer() == null)
			return;

		
		if( m_oGameState.getActivePosition() == null) {
			if( m_oGameState.isThisActivePlayer(oPosition.getPiece().getPlayer().getName())) {
				trySelectPossibleMoveCandidates(oPosition);
				return;
			}
		} else {	
			if( m_oGameState.isThisActivePosition(oPosition.getName())) {
				deselectedActivePosition();
				return;
			}

			if( oPosition.getPiece() != null && m_oGameState.isThisActivePlayer(oPosition.getPiece().getPlayer().getName())) {
				trySelectPossibleMoveCandidates(oPosition);
				return;
			}
		
			IMoveCandidacy oMoveCandidate =m_oGameState.doesPositionExistsInMoveCandidates(oPosition); 
			if( oMoveCandidate != null) {
				oMoveCandidate.setSourcePosition(m_oGameState.getActivePosition());
				tryExecuteRule(oMoveCandidate);
				return;
			} 
			
			deselectedActivePosition();
		}		
	}
		
	public void tryExecuteRule(IMoveCandidacy oMoveCandidate) {
		IMove oMove = m_oRuleProcessor.tryExecuteRule(m_oGameModel.getBoard(), oMoveCandidate);
		deselectedActivePosition();
		m_oGameState.switchPlayTurn();
		notifyListenersOnMoveMadeByPlayer(m_oGameState.getActivePlayer(), oMove);
		notifyListenersOnCurrentPlayerChanged(m_oGameState.getActivePlayer());
	}
	
	public void deselectedActivePosition() {
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

	public void trySelectPossibleMoveCandidates(IPositionAgent oPosition){
		if( oPosition.getPiece() == null)
			return;
				
		if (!oPosition.getPiece().getPlayer().getName().equals(m_oGameState.getActivePlayer().getName()))
			return;
		
		deselectedActivePosition();
		
		Map<String, IMoveCandidacy> mpCandidateMovePosition = m_oRuleProcessor.tryEvaluateAllRules(m_oGameModel.getBoard(), oPosition);
		
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
        for (final IGameListener oListener : m_lstListener) {
            oListener.onTimerUpdate_SecondsElapsed(nRemainingSeconds);
        }	
	}
	
	public void notifyListenersOnTimerUpdate_TimerElapsed(IPlayerAgent oPlayer) {
        for (final IGameListener oListener : m_lstListener) {
            oListener.onTimerUpdate_TimerElapsed(oPlayer);
        }	
	}
	
	public void notifyListenersOnCurrentPlayerChanged(IPlayerAgent oPlayer) {		
        for (final IGameListener oListener : m_lstListener) {
            oListener.onCurrentPlayerChanged(oPlayer);
        }	
	}

	public void notifyListenersOnMoveMadeByPlayer(IPlayerAgent oPlayer, IMove oMove) {		
        for (final IGameListener oListener : m_lstListener) {
            oListener.onMoveMadeByPlayer(oPlayer, oMove);
        }	
	}

	public void tryUndoMove(IPlayerAgent oPlayer) {
		deselectedActivePosition();
		while( !m_oGameState.getActivePlayer().equals(oPlayer)) {
			m_oGameState.switchPlayTurn();
		}			
		notifyListenersOnCurrentPlayerChanged(m_oGameState.getActivePlayer());
	}

	public void tryRedoMove(IPlayerAgent oPlayer) {
		deselectedActivePosition();
		while( !m_oGameState.getActivePlayer().equals(oPlayer)) {
			m_oGameState.switchPlayTurn();
		}
			
		m_oGameState.switchPlayTurn();

		notifyListenersOnCurrentPlayerChanged(m_oGameState.getActivePlayer());
	}
}
