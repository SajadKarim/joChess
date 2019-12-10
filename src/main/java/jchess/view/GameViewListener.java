package jchess.view;

import jchess.common.IPositionAgent;

public interface GameViewListener {
    public void onPositionClicked(IPositionAgent oPosition);
}
