package com.nativegame.juicymatch.game.algorithm.special.finder;

import com.nativegame.juicymatch.game.layer.tile.Tile;

 

public interface SpecialTileFinder {

    void findSpecialTile(Tile[][] tiles, int row, int col);

}
