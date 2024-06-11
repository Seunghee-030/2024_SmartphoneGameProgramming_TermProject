package kr.ac.tukorea.ge.spgp2024.sweetdrops.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;

public class LevelSelectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        // Example of setting up level buttons
        Button level1Button = findViewById(R.id.buttonLevel1);
        level1Button.setOnClickListener(v -> startGameWithLevel(1));

        Button level2Button = findViewById(R.id.buttonLevel2);
        level2Button.setOnClickListener(v -> startGameWithLevel(2));

        // Add more buttons for additional levels as needed
    }

    private void startGameWithLevel(int level) {
        Intent intent = new Intent(LevelSelectActivity.this, SweetDropsActivity.class);
        intent.putExtra("LEVEL", level);
        startActivity(intent);
    }
}
