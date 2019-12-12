package jchess.presenter;

import jchess.view.*;
import jchess.model.*;
/**
 * Responsible to responding to user interaction and updating the view
 */
public class presenterTemp implements viewTempListener {
    private final viewTemp _view;
    private final modelTemp _model;

    public presenterTemp(final viewTemp _view, final modelTemp _model) {
        this._view = _view;
        _view.addListener(this);
        this._model = _model;
    }

    @Override
    public void onButtonClicked() {
        // Update the model (ie. the state of the application)
        _model.addOneToCount();
        // Update the view
        _view.setLabelText(String.valueOf(_model.getCount()));
    }
}