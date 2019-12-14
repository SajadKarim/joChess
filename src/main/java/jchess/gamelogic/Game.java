package jchess.gamelogic;

import org.javatuples.Pair;

import com.google.inject.Inject;

import jchess.common.IBoardAgent;
import jchess.common.IPlayerAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;
import jchess.common.enumerator.RuleEngineType;
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
	private IBoardAgent m_oBoard;
	private IGameState m_oGameState;
	private IRuleEngine m_oRuleProcessor;
	private final ArrayList<IGameListener> m_lstListener;

	@Inject
	public Game(ITimer oTimer){
		m_oTimer = oTimer;
		m_lstListener = new ArrayList<IGameListener>(); 
	}
	
	public void init(IBoardAgent oBoard) {
		m_oBoard = oBoard;
		m_oGameState = new GameState(m_oBoard.getAllPlayerAgents());

		m_oRuleProcessor = RuleEngineFactory.getRuleEngine(RuleEngineType.convertStringToEnum(oBoard.getRuleEngineName()));

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
			if( m_oGameState.getActivePlayer().getName().equals(oPosition.getPiece().getPlayer().getName())) {
				trySelectPossibleMoveCandidates(oPosition);
				return;
			}
		} else {	
			if( m_oGameState.getActivePosition() == oPosition) {
				deselectedActivePosition();
				return;
			}

			if( oPosition.getPiece() != null && m_oGameState.getActivePlayer().getName().equals(oPosition.getPiece().getPlayer().getName())) {
				trySelectPossibleMoveCandidates(oPosition);
				return;
			}
		
			Pair<IPositionAgent, IRuleAgent> oData =m_oGameState.doesPositionExistsInMoveCandidates(oPosition); 
			if( oData != null) {
				m_oRuleProcessor.tryMakeMove(m_oGameState.getActivePosition(), oData);
				deselectedActivePosition();
				m_oGameState.switchPlayTurn();
				notifyListenersOnCurrentPlayerChanged(m_oGameState.getActivePlayer());
				return;
			}
		}		
	}
		
	public void deselectedActivePosition() {
		if( m_oGameState.getPossibleMovesForActivePosition() != null) {

			for (Map.Entry<String,Pair<IPositionAgent, IRuleAgent>> entry : m_oGameState.getPossibleMovesForActivePosition().entrySet()) {
				entry.getValue().getValue0().setMoveCandidacy(false);
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
		
		Map<String,Pair<IPositionAgent, IRuleAgent>> lstPosition = m_oRuleProcessor.tryFindPossibleCandidateMovePositions(m_oBoard, oPosition);
		
		for (Map.Entry<String,Pair<IPositionAgent, IRuleAgent>> entry : lstPosition.entrySet()) {
			entry.getValue().getValue0().setMoveCandidacy(true);
    	}

    	oPosition.setSelectState(true);
    	
    	m_oGameState.setActivePosition(oPosition);
    	m_oGameState.setPossibleMovesForActivePosition( lstPosition);
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
}
