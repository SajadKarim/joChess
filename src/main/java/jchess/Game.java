package jchess;

import jchess.util.Timer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javatuples.Pair;

import jchess.common.IPiece;
import jchess.common.IPlayer;
import jchess.common.IPosition;
import jchess.common.IPositionData;
import jchess.common.IRule;
import jchess.common.Player;
import jchess.common.Position;
import jchess.common.enumerator.RuleType;
import jchess.presenter.IGamePresenter;
import jchess.util.ITimerListener;

public class Game implements ITimerListener{
	private IGamePresenter m_oGamePresenter;
	private Timer m_oTimer;
	private GameState m_oGameState;
	
	public Game(IGamePresenter oGamePresenter, List<IPlayer> lstPlayers){
		m_oGamePresenter = oGamePresenter;
		m_oGameState = new GameState(lstPlayers);
		//m_oGameState.populatePlayers(lstPlayers);
		m_oTimer = new Timer(60*1, 100000, 0, true, true);
		m_oTimer.addListener(this);
		
		m_oTimer.start();
	}
	
	@Override
	public void onSecondElapsed(int nRemainingSeconds) {
		m_oGamePresenter.timerUpdateForRemainingSeconds(nRemainingSeconds);
	}

	@Override
	public void onTimerEnds() {
		m_oGamePresenter.timerElapsed();
		
	}
	
	public void onBoardActivity(Position oPosition) {
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
		
			Pair<IPosition, IRule> oData =m_oGameState.doesPositionExistsInMoveCandidates(oPosition); 
			if( oData != null) {
				makeMove( m_oGameState.getActivePosition(), oData);
				return;
			}
		}		
	}
	
	public void makeMove(IPosition oSourcePosition, Pair<IPosition, IRule> oDestinationPositionAndRule) {
		switch( oDestinationPositionAndRule.getValue1().getRuleType()) {
			case MOVE:{
				if( oDestinationPositionAndRule.getValue0().getPiece() == null) {
					jchess.common.IPiece oPiece = oSourcePosition.getPiece();
					oDestinationPositionAndRule.getValue0().setPiece((jchess.common.Piece)oPiece);
					oSourcePosition.setPiece(null);
				}
			}
				break;
			case MOVE_AND_CAPTURE:{
				jchess.common.IPiece oPiece = oSourcePosition.getPiece();
				oDestinationPositionAndRule.getValue0().setPiece((jchess.common.Piece)oPiece);
				oSourcePosition.setPiece(null);
			}
				break;
			case MOVE_IFF_CAPTURE_POSSIBLE:{
				
			}
				break;
			case MOVE_TRANSIENT:{
				
			}
				break;
			//case CUSTOM:	-- NEED TO ADD THIS IN XML 	
			default:
				break;
		}
	}
	
	public void deselectedActivePosition() {
		if( m_oGameState.getPossibleMovesForActivePosition() != null) {

			for (Map.Entry<String,Pair<IPosition, IRule>> entry : m_oGameState.getPossibleMovesForActivePosition().entrySet()) {
				entry.getValue().getValue0().setMoveCandidacy(false);
			//Iterator<IPosition> itt = m_oGameState.getPossibleMovesForActivePosition().iterator();
	    	//while( itt.hasNext()) {
	    	//	itt.next().setMoveCandidacy(false);
	    	}		
		}

		if( m_oGameState.getActivePosition() != null) {			
			m_oGameState.getActivePosition().setSelectState(false);
		}
		
		m_oGameState.setPossibleMovesForActivePosition(null);
    	m_oGameState.setActivePosition(null);
	}

	public void trySelectPossibleMoveCandidates(IPosition oPosition){
		if( oPosition.getPiece() == null)
			return;
				
		if (oPosition.getPiece().getPlayer() != m_oGameState.getActivePlayer())
			return;
		
		deselectedActivePosition();
		
		Map<String,Pair<IPosition, IRule>> lstPosition = RuleEngine.tryFindPossibleMove(oPosition);
		
		for (Map.Entry<String,Pair<IPosition, IRule>> entry : lstPosition.entrySet()) {
			entry.getValue().getValue0().setMoveCandidacy(true);
		//Iterator<IPosition> itt = lstPosition.iterator();
    	//while( itt.hasNext()) {
    	//	itt.next().setMoveCandidacy(true);
    	}

    	oPosition.setSelectState(true);
    	
    	m_oGameState.setActivePosition(oPosition);
    	m_oGameState.setPossibleMovesForActivePosition( lstPosition);
	}
}
