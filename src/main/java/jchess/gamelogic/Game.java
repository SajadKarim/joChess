package jchess.gamelogic;

import org.javatuples.Pair;

import jchess.common.IBoardAgent;
import jchess.common.IPositionAgent;
import jchess.common.IRuleAgent;
import jchess.common.enumerator.RuleEngineType;
import jchess.presenter.gamewindow.IGamePresenter_Callback;
import jchess.ruleengine.IRuleEngine;
import jchess.ruleengine.RuleEngineFactory;
import jchess.util.ITimerListener;
import jchess.util.Timer;
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

public class Game implements ITimerListener{
	private IBoardAgent m_oBoard;
	private IGamePresenter_Callback m_oGamePresenter;
	private Timer m_oTimer;
	private GameState m_oGameState;
	private IRuleEngine m_oRuleProcessor;
	
	public Game(IGamePresenter_Callback oGamePresenter, IBoardAgent oBoard){
		m_oGameState = new GameState(oBoard.getAllPlayerAgents());
		
		m_oBoard = oBoard;
		
		m_oGamePresenter = oGamePresenter;
		m_oGamePresenter.updateCurrentPlayer(m_oGameState.getActivePlayer());
		
		m_oRuleProcessor = RuleEngineFactory.getRuleEngine(RuleEngineType.RULEENGINE_3PLAYER);
		
		m_oTimer = new Timer(60*60, 10000, 0, true, true);
		m_oTimer.addListener(this);		
		m_oTimer.start();
	}
	
	@Override
	public void onSecondElapsed(int nRemainingSeconds) {
		m_oGamePresenter.onTimerUpdate_SecondsElapsed(nRemainingSeconds);
	}

	@Override
	public void onTimerElapsed() {
		deselectedActivePosition();
		m_oGameState.switchPlayTurn();
		m_oGamePresenter.onTimerUpdate_TimerElapsed(m_oGameState.getActivePlayer());		
		//m_oTimer.start();
	}
	
	public void onBoardActivity(IPositionAgent oPosition) {
		if( m_oGameState.getActivePlayer() == null)
			return;

		
		if( m_oGameState.getActivePosition() == null) {
			if( m_oGameState.getActivePlayer() == oPosition.getPiece().getPlayer()) {
				trySelectPossibleMoveCandidates(oPosition);
				return;
			}
		} else {	
			if( m_oGameState.getActivePosition() == oPosition) {
				deselectedActivePosition();
				return;
			}

			if( oPosition.getPiece() != null && m_oGameState.getActivePlayer() == oPosition.getPiece().getPlayer()) {
				trySelectPossibleMoveCandidates(oPosition);
				return;
			}
		
			Pair<IPositionAgent, IRuleAgent> oData =m_oGameState.doesPositionExistsInMoveCandidates(oPosition); 
			if( oData != null) {
				m_oRuleProcessor.tryMakeMove(m_oGameState.getActivePosition(), oData);
				deselectedActivePosition();
				m_oGameState.switchPlayTurn();
				m_oGamePresenter.updateCurrentPlayer(m_oGameState.getActivePlayer());
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
				
		if (oPosition.getPiece().getPlayer() != m_oGameState.getActivePlayer())
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
}
