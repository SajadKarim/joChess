package jchess.dimodule;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import jchess.cache.CacheManager;
import jchess.cache.ICacheManager;
import jchess.util.AppLogger;
import jchess.util.IAppLogger;

/**
 * This class binds all the dependencies that are share across the project,
 * and requires single instantiation (Singleton).
 * 
 * @author  Sajad Karim
 * @since	7 Dec 2019
 */

public class GlobalModule extends AbstractModule {

	@Override 
	protected void configure() {
	}
	
	@Provides @Singleton
	ICacheManager provideCacheManager() {
		ICacheManager oCacheManager = new CacheManager();
		return oCacheManager;
	}

	@Provides @Singleton
	IAppLogger provideAppLogger() {
		IAppLogger oLogger = new AppLogger();
		return oLogger;
	}
}