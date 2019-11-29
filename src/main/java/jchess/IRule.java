package jchess;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

public interface IRule {	
	public IRule getNextRule();
	
	public void reset();
	
	public Boolean canCapture();

	public Boolean canMove();
	
	public void isValidMove( IPosition oPosition, AtomicReference<Boolean> bIsValidMode, AtomicReference<Boolean> bCanContinue);
}
