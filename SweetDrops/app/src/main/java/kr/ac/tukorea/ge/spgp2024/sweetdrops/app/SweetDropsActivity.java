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
        // Initialize the GameView
        gameView = new GameView(this);
        setContentView(gameView);

        // Create a FrameLayout to overlay the StartOverlayView
        rootLayout = new FrameLayout(this);
        addContentView(rootLayout, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        // Initialize and add the StartOverlayView
        startOverlayView = new StartOverlayView(this);
        startOverlayView.setOnStartListener(() -> {
            rootLayout.removeView(startOverlayView);
            startGame();
        });

        rootLayout.addView(startOverlayView);
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
