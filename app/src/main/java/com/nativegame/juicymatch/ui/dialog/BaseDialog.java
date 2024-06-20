package com.nativegame.juicymatch.ui.dialog;

import com.nativegame.juicymatch.asset.Sounds;
import com.nativegame.natyengine.ui.GameActivity;
import com.nativegame.natyengine.ui.GameDialog;



public abstract class BaseDialog extends GameDialog {


    protected BaseDialog(GameActivity activity) {
        super(activity);
    }

    @Override
    protected void onShow() {
        Sounds.DIALOG_SLIDE.play();
    }

    @Override
    protected void onDismiss() {
        Sounds.DIALOG_SLIDE.play();
    }

}
