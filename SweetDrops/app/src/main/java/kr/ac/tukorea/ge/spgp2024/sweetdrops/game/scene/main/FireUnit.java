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

public class FireUnit extends Sprite {
    private static final String TAG = Monster.class.getSimpleName();
    private static final float PLANE_WIDTH = 1.75f;
    private static final float PLANE_HEIGHT = PLANE_WIDTH * 80 / 72;
    private static final float FIREUNIT_X_OFFSET = 1.2f;
    private static final float TARGET_RADIUS = 0.5f;
    private static final float SPEED = 5.0f;
    private static final float FIRE_INTERVAL = 1.3f;
    private static final float BULLET_OFFSET = 0.8f;

    protected RectF collisionRect = new RectF();
    private static final float MAX_ROLL_TIME = 0.4f;
    private float rollTime;
    private static final Rect[] rects = new Rect[] {
            new Rect(0, 0, 147, 152),                    // 0
            new Rect(0, 152, 130, 152 + 178),                 // 1
            new Rect(0, 152 + 178, 130, 152 + 178 * 2),               // 2
            new Rect(9, 152 + 178 * 2, 130, 152 + 178 * 3),               // 3
            new Rect(9, 152 + 178 * 2, 129, 152 + 178 * 3 + 139),               // 4
            new Rect(9, 152 + 178 * 2 + 139, 129, 152 + 178 * 3 + 139 * 2),               // 5
    };

    private float targetX;
    private float targetY;
    private RectF targetRect = new RectF();
    private Bitmap targetBmp;
    private float fireCoolTime = FIRE_INTERVAL;

    public FireUnit() {
        super(R.mipmap.obj_pump);
        setPosition(FIREUNIT_X_OFFSET, Metrics.height, PLANE_WIDTH, PLANE_HEIGHT);
        setTargetY(posY);
        targetBmp = BitmapPool.get(R.mipmap.fighter_target);
        srcRect = rects[0];
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        float adjy = posY;
        super.update(elapsedSeconds);

        fireBullet(elapsedSeconds);
        updateRoll(elapsedSeconds);
    }

    private void updateRoll(float elapsedSeconds) {
        rollTime += elapsedSeconds * 0.3f;
        if (rollTime > MAX_ROLL_TIME)
            rollTime -= MAX_ROLL_TIME;
        int rollIndex = (int)(rollTime / MAX_ROLL_TIME * 4); // 6개의 프레임
        srcRect = rects[rollIndex];
    }

    private void fireBullet(float elapsedSeconds) {
        MainScene scene = (MainScene) Scene.top();
        if (scene == null) return;
        fireCoolTime -= elapsedSeconds;
        if (fireCoolTime > 0) return;

        fireCoolTime = FIRE_INTERVAL;
        int score = scene.getScore();
        int power = 10;
        Bullet bullet = Bullet.get(posX + BULLET_OFFSET, posY, power);
        scene.add(MainScene.Layer.bullet, bullet);
    }

    @Override
    public void draw(Canvas canvas) {
        if (dx != 0) {
            canvas.drawBitmap(targetBmp, null, targetRect, null);
        }
        canvas.save();
        canvas.rotate(90, posX, posY); // 90도 회전
        super.draw(canvas);
        canvas.restore();
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

                // FireUnit 사각형 영역 내에서 클릭 여부 확인
                if (dstRect.contains(pts[0], clickedY)) {
                    setTargetY(clickedY);
                    setPosition(posX, clickedY, PLANE_WIDTH, PLANE_HEIGHT);
                    return true;
                }
        }
        return false;
    }

    public RectF getCollisionRect() {
        return dstRect;
    }
}
