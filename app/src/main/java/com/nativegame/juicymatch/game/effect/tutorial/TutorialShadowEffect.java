package com.nativegame.juicymatch.game.effect.tutorial;

import com.nativegame.natyengine.engine.Engine;
import com.nativegame.natyengine.entity.shape.primitive.Plane;
import com.nativegame.natyengine.input.touch.TouchEventListener;

 

public class TutorialShadowEffect extends Plane implements TouchEventListener {

    //--------------------------------------------------------
    // Constructors
    //--------------------------------------------------------
    public TutorialShadowEffect(Engine engine, int color) {
        super(engine, color);
    }
    //========================================================

    //--------------------------------------------------------
    // Overriding methods
    //--------------------------------------------------------
    @Override
    public void onTouchEvent(int type, float touchX, float touchY) {
        // Remove when player touch screen
        removeFromGame();
    }
    //========================================================

}
