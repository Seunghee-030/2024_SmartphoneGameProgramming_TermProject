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
            new Rect( 0, 0, 147, 152),                    // 0
            new Rect( 0, 152, 130, 152+178),                 // 1
            new Rect( 0, 152+178, 130, 152+178*2),               // 2
            new Rect( 9, 152+178*2, 130, 152+178*3),               // 3
            new Rect( 9, 152+178*2, 129, 152+178*3+139),               // 4
            new Rect( 9, 152+178*2+139, 129, 152+178*3+139*2),               // 5
    };

    private float targetY;
    private RectF targetRect = new RectF();
    private Bitmap targetBmp;
    private float fireCoolTime = FIRE_INTERVAL;

    public FireUnit() {
        super(R.mipmap.obj_pump);
        setPosition(FIREUNIT_X_OFFSET, Metrics.height, PLANE_WIDTH, PLANE_HEIGHT);
        setTargetY(y);
        targetBmp = BitmapPool.get(R.mipmap.fighter_target);
        srcRect = rects[0];
    }

    @Override
    public void update(float elapsedSeconds) {
        x += dx * elapsedSeconds;

        super.update(elapsedSeconds);

        if (targetY < y) {
            dy = -SPEED;
        } else if (y < targetY) {
            dy = SPEED;
        } else {
            dy = 0;
        }
        super.update(elapsedSeconds);
        float adjy = y;
        if ((dy < 0 && y < targetY) || (dy > 0 && y > targetY)) {
            adjy = targetY;
        } else {
            adjy = Math.max(radius, Math.min(y, Metrics.height - radius));
        }
        if (adjy != y) {
            setPosition(x, adjy, PLANE_WIDTH, PLANE_HEIGHT);
            dy = 0;
        }

        fireBullet(elapsedSeconds);
        updateRoll(elapsedSeconds);
    }

    private void updateRoll(float elapsedSeconds) {
        // rollTime을 증가시킴으로써 애니메이션을 진행
        rollTime += elapsedSeconds * 0.3f;

        // rollTime이 MAX_ROLL_TIME을 넘어가면 초기화하여 반복되도록
        if (rollTime > MAX_ROLL_TIME)
            rollTime -= MAX_ROLL_TIME;

        // 0부터 6까지의 값을 순환하도록 rollIndex를 계산
        int rollIndex = (int)(rollTime / MAX_ROLL_TIME * 4); //6개의 프레임

        srcRect = rects[rollIndex];
    }

    private void fireBullet(float elapsedSeconds) {
        MainScene scene = (MainScene) Scene.top();
        if (scene == null) return;
        fireCoolTime -= elapsedSeconds;
        if (fireCoolTime > 0) return;

        fireCoolTime = FIRE_INTERVAL;
        int score = scene.getScore();
        //int power = 10 + score / 1000;
        int power = 10;
        Bullet bullet = Bullet.get(x+ BULLET_OFFSET, y , power);

        scene.add(MainScene.Layer.bullet, bullet);
    }

    @Override
    public void draw(Canvas canvas) {
        if (dx != 0) {
            canvas.drawBitmap(targetBmp, null, targetRect, null);
        }
        // 객체를 90도 회전하여 그리기
        canvas.save();
        canvas.rotate(90, x, y); // 90도 회전
        super.draw(canvas);
        canvas.restore();

    }

    private void setTargetY(float y) {
        targetY = Math.max(radius, Math.min(y, Metrics.height - radius));
        targetRect.set(
                 x- TARGET_RADIUS, targetY - TARGET_RADIUS,
                x+ TARGET_RADIUS, targetY + TARGET_RADIUS
        );
    }

    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float[] pts = Metrics.fromScreen(event.getX(), event.getY());
                setTargetY(pts[1]);
                return true;
        }
        return false;
    }

    public RectF getCollisionRect() {
        return dstRect;
    }
}
