package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class Monster extends Sprite {
    private static final String TAG = Monster.class.getSimpleName();
    private static final float PLANE_WIDTH = 1.75f;
    private static final float PLANE_HEIGHT = PLANE_WIDTH * 80 / 72;
    private static final float MONSTER_Y_OFFSET = 1.2f;
    private static final float TARGET_RADIUS = 0.5f;
    private static final float SPEED = 5.0f;
    protected RectF collisionRect = new RectF();
    private static final float MAX_ROLL_TIME = 0.4f;
    private float rollTime;
    private static final Rect[] rects = new Rect[] {
            new Rect( 0, 0, 56, 58),
            new Rect( 56, 0, 56*2, 63),
            new Rect( 56*2, 0, 56*3, 63),
            new Rect( 56*3, 0, 56*4, 63),
            new Rect( 56*4, 0, 56*5, 63),
            new Rect( 56*5, 0, 56*6, 63),
            new Rect( 56*6, 0, 56*6+62, 59),
            new Rect( 56*6+62, 0, 56*6+62+59, 59),
            new Rect( 56*6+62+59, 0, 56*6+62+59+57, 59),
            new Rect( 56*6+62+59+57, 0, 56*6+62+59+57+55, 59),
            new Rect( 56*6+62+59+57+55, 0, 56*6+62+59+57+55+55, 62),
    };

    private static final int[] resIds = {
            R.mipmap.character,
            R.mipmap.character_anim
    };

    private float targetX;
    private RectF targetRect = new RectF();
    private Bitmap targetBmp;
    private boolean isColliding = false;
    private float collisionAnimTime = 0;
    private static final float COLLISION_ANIM_DURATION = 0.5f; // duration of collision animation

    public Monster() {
        super(resIds[0]);
        setPosition(Metrics.width / 2, Metrics.height - MONSTER_Y_OFFSET, PLANE_WIDTH, PLANE_HEIGHT);
        setTargetX(posX);
        targetBmp = BitmapPool.get(R.mipmap.fighter_target);
        srcRect = rects[0];
    }

    @Override
    public void update(float elapsedSeconds) {
        if (isColliding) {
            collisionAnimTime += elapsedSeconds;
            if (collisionAnimTime > COLLISION_ANIM_DURATION) {
                isColliding = false;
                collisionAnimTime = 0;
                bitmap = BitmapPool.get(resIds[0]);
                srcRect = rects[0];
            }
        } else {
            if (targetX < posX) {
                dx = -SPEED;
            } else if (posX < targetX) {
                dx = SPEED;
            } else {
                dx = 0;
            }
            super.update(elapsedSeconds);
            float adjx = posX;
            if ((dx < 0 && posX < targetX) || (dx > 0 && posX > targetX)) {
                adjx = targetX;
            } else {
                adjx = Math.max(radius, Math.min(posX, Metrics.width - radius));
            }
            if (adjx != posX) {
                setPosition(adjx, posY, PLANE_WIDTH, PLANE_HEIGHT);
                dx = 0;
            }

            updateRoll(elapsedSeconds);
        }
    }

    private void updateRoll(float elapsedSeconds) {
        rollTime += elapsedSeconds * 0.3f;
        if (rollTime > MAX_ROLL_TIME)
            rollTime -= MAX_ROLL_TIME;
        int rollIndex = (int)(rollTime / MAX_ROLL_TIME * 10);
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
                targetX - TARGET_RADIUS, posY - TARGET_RADIUS,
                targetX + TARGET_RADIUS, posY + TARGET_RADIUS
        );
    }

    public void triggerCollision() {
        isColliding = true;
        collisionAnimTime = 0;
        bitmap = BitmapPool.get(resIds[1]);
        srcRect = rects[0];
    }

    public RectF getCollisionRect() {
        return dstRect;
    }
}
