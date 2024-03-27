package kr.ac.tukorea.ge.spgp.seung.cuttherope;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 화면 크기를 얻습니다.
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        // GameView를 생성하고 화면에 표시합니다.
        gameView = new GameView(this, point.x, point.y);
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 게임이 백그라운드로 갈 때 게임을 일시 중지합니다.
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 게임이 포그라운드로 돌아올 때 게임을 재개합니다.
        gameView.resume();
    }
}
