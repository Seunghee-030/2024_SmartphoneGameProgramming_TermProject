package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.List;

import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import kr.ac.tukorea.ge.spgp2024.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Monster monster;
    private final List<Bouncer> bouncers;
    private final FireUnit fireUnit;
    private final Cloud cloud;
    private final List<Spike> spikes; // Use a list to store multiple spikes
    private Score score; // package private

    public int getScore() {
        return score.getScore();
    }

    public Monster getMonster() {
        return monster;
    }

    public Bouncer getBouncer(int index) {
        return bouncers.get(index);
    }

    public List<Bouncer> getBouncers() {
        return bouncers;
    }

    public Spike getSpike(int index) {
        return spikes.get(index);
    }

    public List<Spike> getSpikes() {
        return spikes;
    }

    public enum Layer {
        bg, obstacle, enemy, bullet, player, ui, controller, item, wind, COUNT
    }

    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker(this));

        add(Layer.bg, new VertScrollBackground(R.mipmap.background, 0.001f));

        add(Layer.item, Item.get(0, 0));
        this.monster = new Monster();
        this.bouncers = new ArrayList<>();
        this.fireUnit = new FireUnit();
        this.cloud = new Cloud();
        this.spikes = new ArrayList<>(); // Initialize the list

        add(Layer.player, monster);
        add(Layer.obstacle, fireUnit);
        add(Layer.obstacle, cloud);

        // Add bouncers at desired positions
        addBouncer(Metrics.width / 4, Metrics.height - 5f);
        addBouncer(Metrics.width / 2, Metrics.height - 7f);
        addBouncer(Metrics.width * 3 / 4, Metrics.height - 6f);

        // Add spikes at desired positions
        addSpike(Metrics.width / 4, Metrics.height / 2);
        addSpike(Metrics.width / 2, Metrics.height / 4);
        addSpike(Metrics.width * 3 / 4, Metrics.height / 2);

        this.score = new Score(R.mipmap.number_24x32, Metrics.width - 0.5f, 0.5f, 0.6f);
        score.setScore(100000);

        add(Layer.ui, score);
    }

    public void addBouncer(float x, float y) {
        Bouncer bouncer = new Bouncer(x, y);
        bouncers.add(bouncer);
        add(Layer.obstacle, bouncer);
    }

    public void addSpike(float x, float y) {
        Spike spike = new Spike(x, y);
        spikes.add(spike);
        add(Layer.obstacle, spike);
    }

    public void addScore(int amount) {
        score.add(amount);
    }

    public Cloud getCloud() {
        return cloud;
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
       /* for (Bouncer bouncer : bouncers) {
            if (bouncer.onTouch(event)) {
                return true;
            }
        }*/
        return fireUnit.onTouch(event);
    }
}
