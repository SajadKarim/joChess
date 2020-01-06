package jchess.dimodule;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import jchess.cache.ICacheManager;
import jchess.common.IBoard;
import jchess.gamelogic.Game;
import jchess.gamelogic.GameState;
import jchess.gamelogic.IGame;
import jchess.gamelogic.IGameState;
import jchess.gui.GUIManager;
import jchess.gui.IGUIHandle;
import jchess.gui.IGUIManager;
import jchess.gui.model.gamewindow.GameModel;
import jchess.gui.model.gamewindow.IBoardModel;
import jchess.gui.model.gamewindow.IClockModel;
import jchess.gui.model.gamewindow.IGameModel;
import jchess.gui.model.gamewindow.IPlayerModel;
import jchess.util.IAppLogger;
import jchess.util.ITimer;
import jchess.util.LogLevel;
import jchess.gui.view.gamewindow.BoardView;
import jchess.gui.view.gamewindow.ClockView;
import jchess.gui.view.gamewindow.GameView;
import jchess.gui.view.gamewindow.IBoardView;
import jchess.gui.view.gamewindow.IClockView;
import jchess.gui.view.gamewindow.IGameView;
import jchess.gui.view.gamewindow.IMoveHistoryView;
import jchess.gui.view.gamewindow.IPlayerView;
import jchess.gui.view.gamewindow.MoveHistoryView;
import jchess.gui.view.gamewindow.PlayerView;
import jchess.ruleengine.IRuleEngine;
import jchess.ruleengine.IRuleProcessor;

/**
 * This class binds all the dependencies that are required for Game window.
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class GameWndModule extends AbstractModule {
	private Injector m_oGlobalInjector = null;
	private String m_stBoardFileName;
	private String m_stBoardName;
	private String m_stGameId;
	
	public GameWndModule(Injector oGlobalInjector, String stGameId, String stBoardName, String stBoardFileName) {
		m_oGlobalInjector = oGlobalInjector;
		m_stBoardFileName = stBoardFileName;
		m_stBoardName = stBoardName;
		m_stGameId = stGameId;
	}
	
	@Override 
	protected void configure() {
		bind(IGameView.class).to(GameView.class);
		bind(IGameModel.class).to(GameModel.class);

		bind(IBoardView.class).to(BoardView.class);
		bind(IBoardModel.class).to(GameModel.class);

		bind(IClockView.class).to(ClockView.class);
		bind(IClockModel.class).to(GameModel.class);

		bind(IPlayerView.class).to(PlayerView.class);
		bind(IPlayerModel.class).to(GameModel.class);

		bind(IMoveHistoryView.class).to(MoveHistoryView.class);

		bind(IGame.class).to(Game.class);
		bind(IGameState.class).to(GameState.class);

		bind(ITimer.class).to(jchess.util.Timer.class);

		bind(IGUIHandle.class).to(GUIManager.class).in(Singleton.class);
	}
	
	@Provides @Singleton
	GameModel provideGameModel() {
		ICacheManager oCacheManager = m_oGlobalInjector.getInstance(ICacheManager.class);
		oCacheManager.loadBoardFromFile(m_stGameId, m_stBoardFileName);
		
		GameModel oGameModel = new GameModel(null);
		oGameModel.setBoard( oCacheManager.getBoard(m_stGameId));
		return oGameModel;
	}

	@Provides @Singleton
	IRuleEngine provideRuleEngine() {
		IRuleEngine oRuleEngine = null;
		IRuleProcessor oRuleProcessor  = null;

		IAppLogger oAppLogger = m_oGlobalInjector.getInstance(IAppLogger.class);
		IGUIManager oGUIManager = m_oGlobalInjector.getInstance(IGUIManager.class);

		ICacheManager oCacheManager = m_oGlobalInjector.getInstance(ICacheManager.class);
		IBoard oBoard = oCacheManager.getBoard(m_stGameId);

		try {			
			oRuleProcessor = (IRuleProcessor)Class.forName("jchess.ruleengine." + oBoard.getRuleProcessorName()).getConstructor(IAppLogger.class).newInstance(oAppLogger);
			oRuleEngine= (IRuleEngine)Class.forName("jchess.ruleengine." + oBoard.getRuleEngineName()).getConstructor(IRuleProcessor.class, IGUIHandle.class, IAppLogger.class).newInstance(new Object[] {oRuleProcessor, oGUIManager.getGUIHandle() , oAppLogger});
		} catch(java.lang.Exception e) {
			oAppLogger.writeLog(LogLevel.ERROR, "An unhandled exception has occured. Exception="+e.toString(), "provideRuleEngine", "GameWndModule");
		}
		return oRuleEngine;
	}
}