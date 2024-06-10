package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import static kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main.MainScene.Layer.item;

import android.graphics.Canvas;
import android.graphics.RectF;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2024.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2024.framework.util.Gauge;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import java.util.List;

public class Item extends AnimSprite implements IBoxCollidable {
    private static final float GRAVITY = 9.8f; // 중력 가속도
    private float SIDE_BOUNCE_FACTOR = 0.0f;
    private static final float BOUNCE_FACTOR = 0.8f; // 튕김 계수 (에너지 손실을 고려한 반발력)
    private float RADIUS = 0.6f;
    private static final int[] resIds = {
            R.mipmap.candy, R.mipmap.candy_broken_anim
    };

    public static final int MAX_LEVEL = resIds.length - 2; // 마지막 리소스는 충돌 애니메이션이므로 제외
    public static final float ANIM_FPS = 7.0f;
    protected RectF collisionRect = new RectF();
    private int level;
    private int life, maxLife;
    private Vector2 position;
    private Vector2 vel;
    private boolean isBroken;
    float brokenPosY;

    protected static Gauge gauge = new Gauge(0.1f, R.color.enemy_gauge_fg, R.color.enemy_gauge_bg);

    private Item(int level, int index) {
        super(0, 0);
        isBroken = false;
        init(level, index);
    }

    private void init(int level, int index) {
        this.level = level;
        this.life = this.maxLife = (level + 1) * 10;
        setAnimationResource(resIds[level], ANIM_FPS);
        position = new Vector2(Metrics.width / 2, RADIUS); // 화면 가운데 맨 위에서 생성
        vel = new Vector2(0.0f, 0.0f); // 초기 속도
        isBroken = false;
    }

    public static Item get(int level, int index) {
        Item item = (Item) RecycleBin.get(Item.class);
        if (item != null) {
            item.init(level, index);
            return item;
        }
        return new Item(level, index);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);

        vel = vel.add(new Vector2(SIDE_BOUNCE_FACTOR, GRAVITY).multiply(elapsedSeconds));
        position = position.add(vel.multiply(elapsedSeconds));

        // 아이템과 몬스터의 충돌 감지
        MainScene scene = (MainScene) Scene.top();
        if (scene != null && !isBroken) {

            // 화면 경계와 충돌
            BounceBall(0, Metrics.height, Metrics.width, 0);

            Monster monster = scene.getMonster(); // 몬스터 인스턴스 가져오기 (이를 위해 MainScene 클래스에 getMonster() 메소드가 필요)
            if (monster != null && isCollidingWith(monster)) {
                //System.out.println("충돌! - 아이템, 몬스터");
                scene.remove(item, this);
                scene.addScore(100000);
            }
            List<Bouncer> bouncers = scene.getBouncers();
            for (Bouncer bouncer : bouncers) {
            if (bouncer != null && isCollidingWith(bouncer)) {
                //System.out.println("충돌! - Item, Bouncer");
                float bouncerLeft = bouncer.getCollisionRect().left;
                float bouncerRight = bouncer.getCollisionRect().right;
                float bouncerTop = bouncer.getCollisionRect().top;
                float bouncerBottom = bouncer.getCollisionRect().bottom;

                // 바운서 위쪽에 닿았을 때 튕김 처리
                if (position.y > bouncerTop + 0.2f - RADIUS) {
                    position.y = bouncerTop + 0.2f - RADIUS;
                    vel.y = -vel.y * BOUNCE_FACTOR;
                    vel.x = -vel.x * BOUNCE_FACTOR;
                }}
            }

            // Check collision with all spikes
            List<Spike> spikes = scene.getSpikes();
            for (Spike spike : spikes) {
                if (isCollidingWith(spike)) {
                    // 스파이크와 충돌 시 애니메이션 변경
                    brokenPosY = position.y;
                    setAnimationResource(resIds[1], ANIM_FPS/2, 10); // 충돌 애니메이션 리소스로 변경
                    setScale(1.4f);
                    isBroken = true;
                    vel.y = -vel.y * BOUNCE_FACTOR/3;
                    break;
                }
            }
        }

        if(isBroken){
            if(position.y > Metrics.height + RADIUS) {
                scene.remove(item, this);
                scene.addScore(-100000);
            }
            // 시간을 두고 삭제하지 않고 충돌후 일정 아래로 떨어지면 사라지도록
            if(position.y > brokenPosY+3.f){
                scene.remove(item,this);
                scene.addScore(-100000);
            }
        }

        updateDstRect(); // 위치 업데이트 후 dstRect 갱신
    }

    private void setScale(float v) {
        RADIUS = RADIUS*v;
    }

    private void BounceBall(float targetUpper, float targetBottom, float targetLeft, float targetRight) {
        // 윗면, 아랫면에 닿았을 때 튕김 처리
        if (position.y > targetBottom - RADIUS) {
            position.y = targetBottom - RADIUS;
            vel.y = -vel.y * BOUNCE_FACTOR;
        }
        if (position.y < targetUpper + RADIUS) {
            position.y = targetUpper + RADIUS;
            vel.y = -vel.y * BOUNCE_FACTOR;
        }

        // 우면, 좌면에 닿았을 때 튕김 처리
        if (position.x > targetLeft - RADIUS) {
            position.x = targetLeft - RADIUS;
            vel.x = -vel.x * BOUNCE_FACTOR;
        }
        if (position.x < targetRight + RADIUS) {
            position.x = targetRight + RADIUS;
            vel.x = -vel.x * BOUNCE_FACTOR;
        }
        updateDstRect();
    }

    private void updateDstRect() {
        dstRect.set(position.x - RADIUS, position.y - RADIUS,
                position.x + RADIUS, position.y + RADIUS);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.save();
        float width = dstRect.width() * 0.7f;
        canvas.translate(position.x - width / 2, dstRect.bottom);
        canvas.scale(width, width);
        gauge.draw(canvas, (float) life / maxLife);
        canvas.restore();
    }

    public boolean isCollidingWith(Monster monster) {
        RectF itemRect = getCollisionRect();
        RectF monsterRect = monster.getCollisionRect();
        return RectF.intersects(itemRect, monsterRect);
    }

    public boolean isCollidingWith(Bouncer bouncer) {
        RectF itemRect = getCollisionRect();
        RectF bouncerRect = bouncer.getCollisionRect();
        return RectF.intersects(itemRect, bouncerRect);
    }

    public boolean isCollidingWith(Spike spike) {
        RectF itemRect = getCollisionRect();
        RectF spikeRect = spike.getCollisionRect();
        return RectF.intersects(itemRect, spikeRect);
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    public int getScore() {
        return (level + 1) * 100;
    }

    public boolean decreaseLife(int power) {
        life -= power;
        return life <= 0;
    }
}
