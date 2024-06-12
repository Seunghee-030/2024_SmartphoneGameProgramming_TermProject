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

public class Cloud extends Sprite {
    private static final float CLOUD_WIDTH = 2f;
    private static final float CLOUD_HEIGHT = CLOUD_WIDTH * 0.7f;
    private static final float TARGET_RADIUS = 0.5f;
    private static final float FIRE_INTERVAL = 1.3f;
    private static final float WIND_OFFSET = 0.8f;

    protected RectF collisionRect = new RectF();
    private static final float MAX_ROLL_TIME = 0.4f;
    private float rollTime;
    private static final int imageSizeX = 141;
    private static final int imageSizeY = 110;

    private static final Rect[] rects = new Rect[] {
            new Rect(1, 0, imageSizeX, imageSizeY),
            new Rect(1+imageSizeX, 0, imageSizeX*2, imageSizeY),
            new Rect(1+imageSizeX*2, 0, imageSizeX*3, imageSizeY),
            new Rect(1+ imageSizeX*3, 0, imageSizeX*4, imageSizeY),
            new Rect(1+imageSizeX*4, 0, imageSizeX*5, imageSizeY),
            new Rect(1+imageSizeX*5, 0, imageSizeX*6, imageSizeY),
            new Rect(1+imageSizeX*6, 0, imageSizeX*7, imageSizeY),
    };

    private float targetX;
    private float targetY;
    private RectF targetRect = new RectF();
    private Bitmap targetBmp;
    private float fireCoolTime = FIRE_INTERVAL;

    public Cloud(float x, float y) {
        super(R.mipmap.cloud_anim);
        setPosition(x,y, CLOUD_WIDTH, CLOUD_HEIGHT);
        setTargetY(posY);
        targetBmp = BitmapPool.get(R.mipmap.fighter_target);
        srcRect = rects[0];
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        fireWind(elapsedSeconds * 0.3f);
        updateRoll(elapsedSeconds);
    }

    private void updateRoll(float elapsedSeconds) {
        rollTime += elapsedSeconds * 0.5f;
        if (rollTime > MAX_ROLL_TIME)
            rollTime -= MAX_ROLL_TIME;
        int rollIndex = (int)(rollTime / MAX_ROLL_TIME * 7);
        srcRect = rects[rollIndex];
    }

    private void fireWind(float elapsedSeconds) {
        MainScene scene = (MainScene) Scene.top();
        if (scene == null) return;
        fireCoolTime -= elapsedSeconds;
        if (fireCoolTime > 0) return;

        fireCoolTime = FIRE_INTERVAL;
        int power = 3;
        Wind wind = Wind.get(posX + WIND_OFFSET, posY, power);
        scene.add(MainScene.Layer.wind, wind);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    private void setTargetY(float y) {
        targetY = Math.max(radius, Math.min(y, Metrics.height - radius));
        targetRect.set(
                posX - TARGET_RADIUS, targetY - TARGET_RADIUS,
                posX + TARGET_RADIUS, targetY + TARGET_RADIUS
        );
    }

    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float[] pts = Metrics.fromScreen(event.getX(), event.getY());
                float clickedY = pts[1];

                if (dstRect.contains(pts[0], clickedY)) {
                    setTargetY(clickedY);
                    setPosition(posX, clickedY, CLOUD_WIDTH, CLOUD_HEIGHT);
                    return true;
                }
        }
        return false;
    }

    public RectF getCollisionRect() {
        return dstRect;
    }
}
