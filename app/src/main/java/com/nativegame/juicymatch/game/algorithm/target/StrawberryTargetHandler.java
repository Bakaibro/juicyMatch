package com.nativegame.juicymatch.game.algorithm.target;

import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.juicymatch.game.layer.tile.Tile;

 

public class StrawberryTargetHandler implements TargetHandler {

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public boolean checkTarget(Tile tile) {
        return tile.getTileType() == FruitType.STRAWBERRY;
    }
    //========================================================

}
