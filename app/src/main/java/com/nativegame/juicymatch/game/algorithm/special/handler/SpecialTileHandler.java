package com.nativegame.juicymatch.game.algorithm.special.handler;

import com.nativegame.juicymatch.game.layer.tile.Tile;


public interface SpecialTileHandler {

    void handleSpecialTile(Tile[][] tiles, Tile tile, int row, int col);

}
