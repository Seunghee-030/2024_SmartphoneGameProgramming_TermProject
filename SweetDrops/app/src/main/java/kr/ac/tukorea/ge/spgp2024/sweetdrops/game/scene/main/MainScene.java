package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import kr.ac.tukorea.ge.spgp2024.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Monster monster;
    private Score score; // package private

    public int getScore() {
        return score.getScore();
    }

    // 추가된 메소드: 몬스터 인스턴스를 반환하는 메소드
    public Monster getMonster() {
        return monster;
    }

    public enum Layer {
        bg, enemy, bullet, player, ui, controller, item, COUNT
    }

    public MainScene() {
        //Metrics.setGameSize(16, 16);
        initLayers(Layer.COUNT);

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker(this));

        add(Layer.bg, new VertScrollBackground(R.mipmap.background, 0.001f));
        //add(Layer.bg, new VertScrollBackground(R.mipmap.clouds, 0.4f));
        add(MainScene.Layer.item, Item.get(0, 0));
        this.monster = new Monster();
        add(Layer.player, monster);

        this.score = new Score(R.mipmap.numbers_320x110, Metrics.width - 0.5f, 0.5f, 0.6f);
        score.setScore(0);
        add(Layer.ui, score);
    }

    public void addScore(int amount) {
        score.add(amount);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return monster.onTouch(event);
    }
}
