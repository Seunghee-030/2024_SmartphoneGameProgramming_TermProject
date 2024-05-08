package kr.ac.tukorea.ge.spgp2024.sweetdrops.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2024.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.util.Gauge;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class Enemy extends AnimSprite implements IBoxCollidable, IRecyclable {
    private static final float GRAVITY = 9.8f; // 중력 가속도
    private static final float SPEED = 2.0f;
    private static final float RADIUS = 0.9f;
    private static final int[] resIds = {
            R.mipmap.candy };
    public static final int MAX_LEVEL = resIds.length - 1;
    public static final float ANIM_FPS = 10.0f;
    protected RectF collisionRect = new RectF();
    private int level;
    private int life, maxLife;
    protected static Gauge gauge = new Gauge(0.1f, R.color.enemy_gauge_fg, R.color.enemy_gauge_bg);

    private Enemy(int level, int index) {
        super(0, 0);
        init(level, index);
        dy = SPEED;
    }

    private void init(int level, int index) {
        this.level = level;
        this.life = this.maxLife = (level + 1) * 10;
        setAnimationResource(resIds[level], ANIM_FPS);
        setPosition(Metrics.width / 2, -RADIUS, RADIUS); // 화면 가운데 맨 위에서 생성

        dy = SPEED; // 초기 속도 설정
    }

    public static Enemy get(int level, int index) {
        Enemy enemy = (Enemy) RecycleBin.get(Enemy.class);
        if (enemy != null) {
            enemy.init(level, index);
            return enemy;
        }
        return new Enemy(level, index);
    }
    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);

        // 중력 적용
        dy += GRAVITY * elapsedSeconds;

        // 캐릭터 이동
        y += dy * elapsedSeconds;
        x += dx * elapsedSeconds;

        // 화면 테두리에 닿으면 반대 방향으로 튕김
        if (y <= RADIUS) {
            y = RADIUS; // 테두리 안으로 위치 조정
            dy = -dy; // 방향 반전
        }
        else if (y >= Metrics.height - RADIUS) {
            y = Metrics.height - RADIUS; // 테두리 안으로 위치 조정
            dy = -dy; // 방향 반전
        }

        if (x <= RADIUS) {
            x = RADIUS; // 테두리 안으로 위치 조정
            dx = -dx; // 방향 반전
        }
        else if (x >= Metrics.width - RADIUS) {
            x = Metrics.width - RADIUS; // 테두리 안으로 위치 조정
            dx = -dx; // 방향 반전
        }

        collisionRect.set(dstRect);
        collisionRect.inset(0.11f, 0.11f);

        if (dstRect.top > Metrics.height) {
            Scene.top().remove(MainScene.Layer.enemy, this);
        }
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.save();

        float width = dstRect.width() * 0.7f;
        canvas.translate(x - width / 2, dstRect.bottom);
        canvas.scale(width, width);
        gauge.draw(canvas, (float)life / maxLife);
        canvas.restore();
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    public void onRecycle() {

    }

    public int getScore() {
        return (level + 1) * 100;
    }

    public boolean decreaseLife(int power) {
        life -= power;
        return life <= 0;
    }
}
