package kr.ac.tukorea.ge.spgp2024.sweetdrops.app;

import android.os.Bundle;

import kr.ac.tukorea.ge.spgp2024.sweetdrops.game.MainScene;
import kr.ac.tukorea.ge.spgp2024.framework.activity.GameActivity;

public class SweetDropsActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MainScene().push();
    }
}