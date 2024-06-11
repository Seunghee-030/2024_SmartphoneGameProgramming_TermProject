package kr.ac.tukorea.ge.spgp2024.sweetdrops.app;

import android.os.Bundle;
import android.widget.FrameLayout;

import kr.ac.tukorea.ge.spgp2024.framework.view.StartOverlayView;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main.MainScene;
import kr.ac.tukorea.ge.spgp2024.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2024.framework.view.GameView;

public class SweetDropsActivity extends GameActivity {

    private FrameLayout rootLayout;
    private StartOverlayView startOverlayView;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the selected level from the intent
        int level = getIntent().getIntExtra("LEVEL", 1);

        // Initialize the main scene with the selected level
        MainScene mainScene = new MainScene(level);
        mainScene.push();
    }

    private void startGame() {
        gameView.post(() -> new MainScene(1).push());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (startOverlayView.getParent() == null) {
            gameView.resumeGame();
        }
    }

    @Override
    protected void onPause() {
        gameView.pauseGame();
        super.onPause();
    }
}
