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

public class Bouncer extends Sprite {
    private static final float BOUNCER_WIDTH = 2f;
    private static final float BOUNCER_HEIGHT = BOUNCER_WIDTH * 0.7f;
    private static final float TARGET_RADIUS = 0.5f;

    protected RectF collisionRect = new RectF();

    private float targetX;
    private float targetY;
    private RectF targetRect = new RectF();
    private Bitmap targetBmp;

    public Bouncer(float x, float y) {
        super(R.mipmap.obj_bouncer); // replace with actual resource
        setPosition(x, y, BOUNCER_WIDTH, BOUNCER_HEIGHT);
        setTargetX(posX);
        targetBmp = BitmapPool.get(R.mipmap.fighter_target);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    private void setTargetX(float x) {
        targetX = Math.max(radius, Math.min(x, Metrics.width - radius));
        targetRect.set(
                targetX - TARGET_RADIUS, posY - TARGET_RADIUS,
                targetX + TARGET_RADIUS, posY + TARGET_RADIUS
        );
    }

    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float[] pts = Metrics.fromScreen(event.getX(), event.getY());
                float clickedX = pts[0];

                if (dstRect.contains(clickedX, pts[1])) {
                    setTargetX(clickedX);
                    setPosition(clickedX, posY, BOUNCER_WIDTH, BOUNCER_HEIGHT);
                    return true;
                }
        }
        return false;
    }

    public RectF getCollisionRect() {
        return dstRect;
    }
}
