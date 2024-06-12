package kr.ac.tukorea.ge.spgp2024.sweetdrops.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;

public class LevelSelectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        // Setup level buttons
        setupLevelButton(R.id.buttonLevel1, 1);
        setupLevelButton(R.id.buttonLevel2, 2);
        setupLevelButton(R.id.buttonLevel3, 3);
        setupLevelButton(R.id.buttonLevel4, 4);
        setupLevelButton(R.id.buttonLevel5, 5);
        setupLevelButton(R.id.buttonLevel6, 6);
    }

    private void setupLevelButton(int buttonId, int level) {
        ImageButton button = findViewById(buttonId);
        button.setOnClickListener(v -> startGameWithLevel(level));
    }

    private void startGameWithLevel(int level) {
        Intent intent = new Intent(LevelSelectActivity.this, SweetDropsActivity.class);
        intent.putExtra("LEVEL", level);
        startActivity(intent);
    }
}
