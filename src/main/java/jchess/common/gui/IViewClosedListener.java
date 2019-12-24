package jchess.common.gui;

/**
 * IViewClosedListener - Callback to inform subscribers about the closure of View.
 * 
 * 
 * @author	Sajad Karim
 * @since	7 Dec 2019
 */

public interface IViewClosedListener {
    public void onViewClosed(DialogResult oFormAction, Object oData);
}
