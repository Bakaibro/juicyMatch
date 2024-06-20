package com.nativegame.juicymatch.game.layer.entrypoint;



public class EntryPointInitializer {

    //--------------------------------------------------------
    // Static methods
    //--------------------------------------------------------
    public static EntryPointType getType(char c) {
        switch (c) {
            case 'A':
                return EntryPointType.ARROW;
            default:
                throw new IllegalArgumentException("EntryPointType not found!");
        }
    }
    //========================================================

}
