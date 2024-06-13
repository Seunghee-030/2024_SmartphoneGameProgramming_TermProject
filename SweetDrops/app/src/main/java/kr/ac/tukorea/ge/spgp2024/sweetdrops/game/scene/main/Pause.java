package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;

public class Pause extends Sprite {
    private static final float BUTTON_WIDTH = 3f;
    private static final float BUTTON_HEIGHT = 3f;

    private static final int imageSizeX = 300;
    private static final int imageSizeY = 300;

    private static final Rect[] rects = new Rect[] {
            new Rect(0, 0, imageSizeX, imageSizeY),
    };

    public Pause(float x, float y, int e) {
        super(R.mipmap.btn_pause);
        setPosition(2, 2, BUTTON_WIDTH, BUTTON_HEIGHT);

        srcRect = rects[0];
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
                if (dstRect.contains(pts[0],pts[1])) {
                    //setTargetY(clickedY);
                    //setPosition(posX, clickedY, CLOUD_WIDTH, CLOUD_HEIGHT);
                    System.out.println("click pause");

                    return true;
                }
        }
        return false;
    }

    public RectF getCollisionRect() {
        return dstRect;
    }
}
