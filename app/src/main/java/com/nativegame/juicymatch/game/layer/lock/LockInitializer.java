package com.nativegame.juicymatch.game.layer.lock;



public class LockInitializer {

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static LockType getType(char c) {
        switch (c) {
            case 'X':
                return LockType.CENTER;
            default:
                throw new IllegalArgumentException("LockType not found!");
        }
    }
    //========================================================

}
