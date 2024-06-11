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
    private final List<FireUnit> fireUnits;
    private final List<Cloud> clouds;
    private final List<Spike> spikes; // Use a list to store multiple spikes
    private Score score; // package private
    private boolean isPaused = false;

    public int getScore() {
        return score.getScore();
    }

    public Monster getMonster() {
        return monster;
    }

    public List<Bouncer> getBouncers() {
        return bouncers;
    }

    public List<Spike> getSpikes() {
        return spikes;
    }

    public enum Layer {
        bg, obstacle, enemy, bullet, player, ui, controller, item, wind, COUNT
    }

    private int level;

    public MainScene(int level) {
        this.level = level;
        initLayers(Layer.COUNT);

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker(this));

        add(Layer.bg, new VertScrollBackground(R.mipmap.background, 0.001f));

        add(Layer.item, Item.get(0, 0));
        this.monster = new Monster();
        this.bouncers = new ArrayList<>();
        this.fireUnits = new ArrayList<>();
        this.clouds = new ArrayList<>();
        this.spikes = new ArrayList<>(); // Initialize the list

        add(Layer.player, monster);

        // Add FireUnits
        addFireUnit(Metrics.width / 4, Metrics.height - 5f);
        addFireUnit(Metrics.width / 3, Metrics.height - 5f);

        // Add Clouds
        addCloud(Metrics.width / 2, Metrics.height - 5f);
        addCloud(Metrics.width / 1, Metrics.height - 5f);

        // Add bouncers
        addBouncer(Metrics.width / 4, Metrics.height - 5f);
        addBouncer(Metrics.width / 2, Metrics.height - 7f);
        addBouncer(Metrics.width * 3 / 4, Metrics.height - 6f);

        // Add spikes
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

    public void addCloud(float x, float y) {
        Cloud cloud = new Cloud(x, y);
        clouds.add(cloud);
        add(Layer.obstacle, cloud);
    }
    public void addFireUnit(float x, float y) {
        FireUnit fireUnit = new FireUnit(x, y);
        fireUnits.add(fireUnit);
        add(Layer.obstacle, fireUnit);
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
       /* for (Bouncer bouncer : bouncers) {
            if (bouncer.onTouch(event)) {
                return true;
            }
        }*/
        return fireUnits.get(0).onTouch(event);
    }
}
