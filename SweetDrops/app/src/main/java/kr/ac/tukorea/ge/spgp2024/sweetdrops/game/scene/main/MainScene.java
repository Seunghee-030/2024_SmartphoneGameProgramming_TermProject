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
    private final FireUnit fireUnit;
    private Score score; // package private

    public int getScore() {
        return score.getScore();
    }

    // 추가된 메소드: 몬스터 인스턴스를 반환하는 메소드
    public Monster getMonster() {
        return monster;
    }

    public enum Layer {
        bg, obstacle, enemy, bullet, player, ui, controller, item, COUNT
    }

    public MainScene() {
        //Metrics.setGameSize(16, 16);
        initLayers(Layer.COUNT);

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker(this));

        add(Layer.bg, new VertScrollBackground(R.mipmap.background, 0.001f));

        add(MainScene.Layer.item, Item.get(0, 0));
        this.monster = new Monster();
        this.fireUnit = new FireUnit();
        add(Layer.player, monster);
        add(Layer.obstacle, fireUnit);
        this.score = new Score(R.mipmap.number_24x32, Metrics.width - 0.5f, 0.5f, 0.6f);
        score.setScore(100000);

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
        return fireUnit.onTouch(event);
    }
}
