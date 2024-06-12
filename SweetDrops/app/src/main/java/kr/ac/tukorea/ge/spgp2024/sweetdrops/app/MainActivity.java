package kr.ac.tukorea.ge.spgp2024.sweetdrops.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ImageButton 클릭 리스너 설정
        ImageButton startGameButton = binding.getRoot().findViewById(R.id.imageButtonGameStart);
        startGameButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LevelSelectActivity.class));
        });
    }
}
