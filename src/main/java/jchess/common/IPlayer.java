package jchess.common;

import jchess.common.PlayerData.colors;
import jchess.common.PlayerData.playerTypes;

public interface IPlayer {
	public String getName();
	public colors getColor();
	public playerTypes getplayertype();
}
