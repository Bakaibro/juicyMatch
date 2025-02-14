package com.nativegame.juicymatch.game.algorithm.layer;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.game.algorithm.target.TargetHandlerManager;
import com.nativegame.juicymatch.game.layer.ice.Ice;
import com.nativegame.juicymatch.game.layer.ice.IceSystem;
import com.nativegame.juicymatch.game.layer.tile.FruitType;
import com.nativegame.juicymatch.game.layer.tile.SpecialType;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.level.TargetType;

 

public class IceLayerHandler extends BaseLayerHandler {

    private final IceSystem mIceSystem;


    public IceLayerHandler(IceSystem iceSystem) {
        mIceSystem = iceSystem;
    }

    @Override
    protected void onInitLayer(Tile tile) {
    }

    @Override
    protected void onUpdateLayer(Tile tile, TargetHandlerManager targetHandlerManager, Tile[][] tiles, int row, int col) {
        if (tile.getTileState() != TileState.MATCH) {
            return;
        }
        updateIce(targetHandlerManager, tile);
    }

    @Override
    protected void onRemoveLayer(Tile tile) {
    }

    private void updateIce(TargetHandlerManager targetHandlerManager, Tile tile) {
        Ice ice = mIceSystem.getChildAt(tile.getRow(), tile.getColumn());
        if (ice != null && ice.isRunning()) {
            // Remove ice if tile is fruit or special tile
            if (tile.getTileType() != FruitType.NONE || tile.getSpecialType() != SpecialType.NONE) {
                ice.playIceEffect();
                // Update target if all the ice layers being removed
                if (ice.getIceLayer() == 0) {
                    targetHandlerManager.updateTarget(TargetType.ICE);
                }
            }
        }
    }
}
