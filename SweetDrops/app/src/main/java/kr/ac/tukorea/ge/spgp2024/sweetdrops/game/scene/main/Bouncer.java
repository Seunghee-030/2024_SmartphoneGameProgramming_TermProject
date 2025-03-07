package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class Bouncer extends Sprite {
    private static final String TAG = Bouncer.class.getSimpleName();
    private static final float BOUNCER_WIDTH = 2.5f;
    private static final float BOUNCER_HEIGHT = BOUNCER_WIDTH / 3;

    public Bouncer(float x, float y) {
        super(R.mipmap.obj_bouncer);
        setPosition(x, y, BOUNCER_WIDTH, BOUNCER_HEIGHT);
        srcRect = new Rect(0, 0, 182, 72);
    }

    @Override
    public void setPosition(float x, float y, float width, float height) {
        super.setPosition(x, y, BOUNCER_WIDTH, BOUNCER_HEIGHT);
    }

    @Override
    public void update(float elapsedSeconds) {
        // Bouncer 업데이트 로직이 있다면 추가하세요.
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float[] pts = Metrics.fromScreen(event.getX(), event.getY());

                // Bouncer 사각형 영역 내에서 클릭 여부 확인
                // if (dstRect.contains(pts[0], pts[1])) {
                Log.d(TAG, "Bouncer clicked: (" + pts[0] + ", " + pts[1] + ")");
                setPosition(pts[0], posY, BOUNCER_WIDTH, BOUNCER_HEIGHT);
                //setPosition(pts[0], pts[1], BOUNCER_WIDTH, BOUNCER_HEIGHT);
                return true;
            //}
        }
        return false;
    }

    public RectF getCollisionRect() {
        return dstRect;
    }
}
