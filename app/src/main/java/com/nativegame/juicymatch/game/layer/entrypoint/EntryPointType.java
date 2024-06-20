package com.nativegame.juicymatch.game.layer.entrypoint;

import com.nativegame.juicymatch.asset.Textures;
import com.nativegame.natyengine.texture.Texture;



public enum EntryPointType {
    ARROW;

    //--------------------------------------------------------
    // Getter and Setter
    //--------------------------------------------------------
    public Texture getTexture() {
        switch (this) {
            case ARROW:
                return Textures.ARROW;
            default:
                throw new IllegalArgumentException("EntryPoint Texture not found!");
        }
    }
    //========================================================

}
