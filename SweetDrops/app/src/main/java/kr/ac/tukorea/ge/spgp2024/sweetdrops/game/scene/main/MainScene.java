package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import android.view.MotionEvent;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2024.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.app.LevelSelectActivity;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.app.MainActivity;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.app.SweetDropsActivity;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.paused.PausedScene;

import java.util.ArrayList;
import java.util.List;

public class MainScene extends Scene {
    private final Monster monster;
    private final List<Bouncer> bouncers;
    private final List<FireUnit> fireUnits;
    private final List<Cloud> clouds;
    private final List<Button> buttons;
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
        bg, obstacle, enemy, bullet, player, ui, touch, controller, item, wind, COUNT
    }

    public MainScene(int level) {
        this.level = level;
        initLayers(Layer.COUNT);

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker(this));
        switch (level) {
            case 1:
                add(Layer.bg, new VertScrollBackground(R.mipmap.bgr_1, 0f));
                break;
            case 2:
                add(Layer.bg, new VertScrollBackground(R.mipmap.bgr_2, 0f));
                break;
            case 3:
                add(Layer.bg, new VertScrollBackground(R.mipmap.bgr_3, 0.3f));
                break;
            case 4:
                add(Layer.bg, new VertScrollBackground(R.mipmap.bgr_4, 0.5f));
                break;
            case 5:
                add(Layer.bg, new VertScrollBackground(R.mipmap.bgr_5, 0f));
                break;
            case 6:
                add(Layer.bg, new VertScrollBackground(R.mipmap.bgr_6, 0f));
                break;
        }
        add(Layer.item, Item.get(0, 0));
        this.monster = new Monster();
        this.bouncers = new ArrayList<>();
        this.fireUnits = new ArrayList<>();
        this.clouds = new ArrayList<>();
        this.buttons = new ArrayList<>();
        this.spikes = new ArrayList<>();

        add(Layer.touch, new Button(R.mipmap.btn_pause, 1f,1f,1f,1f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if (action == Button.Action.pressed) {
                    // 버튼 눌림 동작 처리
                    System.out.println("click");
                    return true;
                } else if (action == Button.Action.released) {
                    // 버튼 놓임 동작 처리
                    // 일시정지 씬을 띄우는 버튼 논리 구현
                    new PausedScene().push();
                    return true;
                }
                return false;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.btn_restart,2f,1f,1f,1f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if (action == Button.Action.pressed) {
                    // 버튼 눌림 동작 처리
                    return true;
                } else if (action == Button.Action.released) {
                    // 버튼 놓임 동작 처리

                    return true;
                }
                return false;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.btn_menu, 3f,1f,1f,1f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if (action == Button.Action.pressed) {
                    // 버튼 눌림 동작 처리
                    return true;
                } else if (action == Button.Action.released) {
                    // 버튼 놓임 동작 처리
                    MainScene.pop();
                    return true;
                }
                return false;
            }
        }));

        add(Layer.player, monster);

        setupLevel(level);

        this.score = new Score(R.mipmap.number_24x32, Metrics.width - 0.5f, 0.5f, 0.6f);
        score.setScore(0);

        add(Layer.ui, score);
    }

    private void setupLevel(int level) {
        switch (level) {
            case 1:
                addSpike(Metrics.width/6,Metrics.height- 0.2f);
                addSpike(Metrics.width*5/6+ 1f,Metrics.height- 0.2f);
                addSpike(Metrics.width/6,Metrics.height/2);
                addSpike(Metrics.width*5/6+ 1f,Metrics.height/2);
                addSpike(Metrics.width/6,1f);
                addSpike(Metrics.width*5/6+ 1f,1f);



                addCloud(Metrics.width/6, 3f, 1);
                addFireUnit(Metrics.width/6, Metrics.width - 3f);

                addBouncer(Metrics.width / 2, Metrics.height - 5f);
                addBouncer(Metrics.width / 2, 5f);

                break;
            case 2:
                addCloud(Metrics.width -2f, Metrics.height - 5f,2);
                addCloud(Metrics.width / 4, 5f,1);
                addBouncer(Metrics.width / 7, 5f);
                addBouncer(Metrics.width / 2, Metrics.height - 5f);
                addBouncer(Metrics.width -1f, 5f);
                addSpike(Metrics.width/6,Metrics.height- 0.2f);
                addSpike(Metrics.width*5/6+ 1f,Metrics.height- 0.2f);
                break;
            case 3:
                addCloud(Metrics.width -2f, Metrics.height - 5f,2);
                addCloud(Metrics.width / 4, 5f,3);
                addBouncer(Metrics.width / 7, 5f);
                addBouncer(Metrics.width / 2, Metrics.height - 5f);
                addBouncer(Metrics.width -1f, 5f);
                addSpike(Metrics.width/6,Metrics.height- 0.2f);
                addSpike(Metrics.width*5/6+ 1f,Metrics.height- 0.2f);
                break;
            case 4:
                addCloud(Metrics.width -2f, Metrics.height - 5f,1);
                addCloud(Metrics.width / 4, 5f,4);
                addBouncer(Metrics.width / 7, 5f);
                addBouncer(Metrics.width / 2, Metrics.height - 5f);
                addBouncer(Metrics.width -1f, 5f);
                addSpike(Metrics.width/6,Metrics.height- 0.2f);
                addSpike(Metrics.width*5/6+ 1f,Metrics.height- 0.2f);
                break;
            case 5:
                addCloud(Metrics.width -2f, Metrics.height - 5f,3);
                addCloud(Metrics.width / 4, 5f,4);
                addBouncer(Metrics.width / 7, 5f);
                addBouncer(Metrics.width / 2, Metrics.height - 5f);
                addBouncer(Metrics.width -1f, 5f);
                addSpike(Metrics.width/6,Metrics.height- 0.2f);
                addSpike(Metrics.width*5/6+ 1f,Metrics.height- 0.2f);
                break;
            case 6:
                addCloud(Metrics.width -2f, Metrics.height - 5f,2);
                addCloud(Metrics.width / 4, 5f,4);
                addBouncer(Metrics.width / 7, 5f);
                addBouncer(Metrics.width / 2, Metrics.height - 5f);
                addBouncer(Metrics.width -1f, 5f);
                addSpike(Metrics.width/6,Metrics.height- 0.2f);
                addSpike(Metrics.width*5/6+ 1f,Metrics.height- 0.2f);
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

    public void addCloud(float x, float y, int e) {
        Cloud cloud = new Cloud(x, y, e);
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

    /*@Override
    public boolean onTouch(MotionEvent event) {
        float[] pts = Metrics.fromScreen(event.getX(), event.getY());

        for (Bouncer bouncer : bouncers) {
            if (bouncer.getCollisionRect().contains(pts[0], pts[1])) {
                if (bouncer.onTouch(event)) {
                    System.out.println("Bouncer Click!");
                    return true;
                }
            }
        }

        for (FireUnit fireUnit : fireUnits) {
            if (fireUnit.getCollisionRect().contains(pts[0], pts[1])) {
                if (fireUnit.onTouch(event)) {
                    System.out.println("FireUnit Click!");
                    return true;
                }
            }
        }

        for (Cloud cloud : clouds) {
            if (cloud.getCollisionRect().contains(pts[0], pts[1])) {
                if (cloud.onTouch(event)) {
                    System.out.println("Cloud Click!");
                    return true;
                }
            }
        }

        return false;
    }*/

    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    @Override
    protected void onPause() {
        //Sound.pauseMusic();
    }

    @Override
    protected void onResume() {
        //Sound.resumeMusic();
    }
}

