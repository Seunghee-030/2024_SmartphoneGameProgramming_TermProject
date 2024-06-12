package kr.ac.tukorea.ge.spgp2024.sweetdrops.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main.MainScene;
import kr.ac.tukorea.ge.spgp2024.framework.activity.GameActivity;

public class SweetDropsActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int level = getIntent().getIntExtra("LEVEL", 1); // Default to level 1 if not specified
        new MainScene(level).push();
    }
}
