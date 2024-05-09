package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import android.graphics.Canvas;
import android.graphics.RectF;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2024.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.util.Gauge;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
public class Item extends AnimSprite implements IBoxCollidable, IRecyclable {
    private static final float GRAVITY = 0.2f; // 중력 가속도
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

    private Item(int level, int index) {
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
        // 중력에 따른 속도 변경
        dy += GRAVITY * elapsedSeconds;
        // 공의 위치 변경
        y += dy * elapsedSeconds;

        if (dx > 0) {
            if (dstRect.right > Metrics.width) {
                dx = -dx;
            }
        } else {
            if (dstRect.left < 0) {
                dx = -dx;
            }
        }
        if (dy > 0) {
            if (dstRect.bottom > Metrics.height) {
                dy = -dy;
            }
        } else {
            if (dstRect.top < 0) {
                dy = -dy;
            }
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