package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import java.util.ArrayList;
import java.util.List;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import kr.ac.tukorea.ge.spgp2024.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class MainScene extends Scene {
    private final Monster monster;
    private final List<Bouncer> bouncers;
    private final List<FireUnit> fireUnits;
    private final List<Cloud> clouds;
    private final List<Spike> spikes;
    private final Score score;
    private final int level;

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
        this.spikes = new ArrayList<>();

        add(Layer.player, monster);

        setupLevel(level);

        this.score = new Score(R.mipmap.number_24x32, Metrics.width - 0.5f, 0.5f, 0.6f);
        score.setScore(100000);

        add(Layer.ui, score);
    }

    private void setupLevel(int level) {
        switch (level) {
            case 1:
                addCloud(Metrics.width / 5, Metrics.height - 5f);
                break;
            case 2:
                addCloud(Metrics.width / 4, Metrics.height - 5f);
                addBouncer(Metrics.width / 4, Metrics.height - 5f);
                break;
            // Add cases for additional levels
            // case 3, case 4, etc.
        }
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
}
