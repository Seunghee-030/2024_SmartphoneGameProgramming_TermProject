package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class Monster extends Sprite {
    private static final String TAG = Monster.class.getSimpleName();
    private static final float PLANE_WIDTH = 1.75f;
    private static final float PLANE_HEIGHT = PLANE_WIDTH * 80 / 72;
    private static final float MONSTER_Y_OFFSET = 1.2f;
    private static final float TARGET_RADIUS = 0.5f;
    private static final float SPEED = 5.0f;    //
    protected RectF collisionRect = new RectF();
    private static final float MAX_ROLL_TIME = 0.4f;
    private float rollTime;
    private static final Rect[] rects = new Rect[] {
            new Rect( 0, 0, 56, 58),                    // 0
            new Rect( 56, 0, 56*2, 63),                 // 1
            new Rect( 56*2, 0, 56*3, 63),               // 2
            new Rect( 56*3, 0, 56*4, 63),               // 3
            new Rect( 56*4, 0, 56*5, 63),               // 4
            new Rect( 56*5, 0, 56*6, 63),               // 5
            new Rect( 56*6, 0, 56*6+62, 59),                            // 6
            new Rect( 56*6+62, 0, 56*6+62+59, 59),                      // 7
            new Rect( 56*6+62+59, 0, 56*6+62+59+57, 59),                // 8
            new Rect( 56*6+62+59+57, 0, 56*6+62+59+57+55, 59),          // 9
            new Rect( 56*6+62+59+57+55, 0, 56*6+62+59+57+55+55, 62),    // 10
    };

    private float targetX;
    private RectF targetRect = new RectF();
    private Bitmap targetBmp;

    public Monster() {
        super(R.mipmap.character);
        setPosition(Metrics.width / 2, Metrics.height - MONSTER_Y_OFFSET, PLANE_WIDTH, PLANE_HEIGHT);
        setTargetX(x);
        targetBmp = BitmapPool.get(R.mipmap.fighter_target);
        srcRect = rects[0];
    }

    @Override
    public void update(float elapsedSeconds) {
        if (targetX < x) {
            dx = -SPEED;
        } else if (x < targetX) {
            dx = SPEED;
        } else {
            dx = 0;
        }
        super.update(elapsedSeconds);
        float adjx = x;
        if ((dx < 0 && x < targetX) || (dx > 0 && x > targetX)) {
            adjx = targetX;
        } else {
            adjx = Math.max(radius, Math.min(x, Metrics.width - radius));
        }
        if (adjx != x) {
            setPosition(adjx, y, PLANE_WIDTH, PLANE_HEIGHT);
            dx = 0;
        }

        updateRoll(elapsedSeconds);
    }

    private void updateRoll(float elapsedSeconds) {
        // rollTime을 증가시킴으로써 애니메이션을 진행
        rollTime += elapsedSeconds * 0.3f;

        // rollTime이 MAX_ROLL_TIME을 넘어가면 초기화하여 반복되도록
        if (rollTime > MAX_ROLL_TIME)
            rollTime -= MAX_ROLL_TIME;

        // 0부터 12까지의 값을 순환하도록 rollIndex를 계산
        int rollIndex = (int)(rollTime / MAX_ROLL_TIME * 10); //10개의 프레임

        srcRect = rects[rollIndex];
    }


    @Override
    public void draw(Canvas canvas) {
        if (dx != 0) {
            canvas.drawBitmap(targetBmp, null, targetRect, null);
        }
        super.draw(canvas);
    }

    private void setTargetX(float x) {
        targetX = Math.max(radius, Math.min(x, Metrics.width - radius));
        targetRect.set(
                targetX - TARGET_RADIUS, y - TARGET_RADIUS,
                targetX + TARGET_RADIUS, y + TARGET_RADIUS
        );
    }


    public RectF getCollisionRect() {
        return dstRect;
    }
}
