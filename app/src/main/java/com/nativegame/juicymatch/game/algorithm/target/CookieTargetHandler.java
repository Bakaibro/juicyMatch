package com.nativegame.juicymatch.game.algorithm.target;

import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.game.layer.tile.type.CookieTile;

 

public class CookieTargetHandler implements TargetHandler {

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public boolean checkTarget(Tile tile) {
        if (tile instanceof CookieTile) {
            CookieTile cookie = ((CookieTile) tile);
            return cookie.isObstacle();
        }
        return false;
    }
    //========================================================

}
