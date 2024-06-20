package com.nativegame.juicymatch.game.algorithm.layer;

import com.nativegame.juicymatch.algorithm.TileState;
import com.nativegame.juicymatch.game.algorithm.target.TargetHandlerManager;
import com.nativegame.juicymatch.game.layer.lock.Lock;
import com.nativegame.juicymatch.game.layer.lock.LockSystem;
import com.nativegame.juicymatch.game.layer.tile.Tile;
import com.nativegame.juicymatch.level.TargetType;

 

public class LockLayerHandler extends BaseLayerHandler {

    private final LockSystem mLockSystem;


    public LockLayerHandler(LockSystem lockSystem) {
        mLockSystem = lockSystem;
    }

    @Override
    protected void onInitLayer(Tile tile) {
        Lock lock = mLockSystem.getChildAt(tile.getRow(), tile.getColumn());
        if (lock != null && lock.isRunning()) {
            tile.setPoppable(false);
            tile.setSwappable(false);
            tile.setShufflable(false);
        }
    }

    @Override
    protected void onUpdateLayer(Tile tile, TargetHandlerManager targetHandlerManager, Tile[][] tiles, int row, int col) {
        if (tile.getTileState() != TileState.MATCH) {
            return;
        }
        updateLock(targetHandlerManager, tile);
    }

    @Override
    protected void onRemoveLayer(Tile tile) {
    }

    private void updateLock(TargetHandlerManager targetHandlerManager, Tile tile) {
        Lock lock = mLockSystem.getChildAt(tile.getRow(), tile.getColumn());
        if (lock != null && lock.isRunning()) {
            lock.playLockEffect();
            tile.setPoppable(true);
            tile.setSwappable(true);
            tile.setShufflable(true);
            tile.setTileState(TileState.IDLE);
            targetHandlerManager.updateTarget(TargetType.LOCK);
        }
    }

}
