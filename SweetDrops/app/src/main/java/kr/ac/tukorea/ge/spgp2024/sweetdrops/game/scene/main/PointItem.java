package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;

public class PointItem extends Sprite {
    private static final String TAG = PointItem.class.getSimpleName(); // Corrected from Monster to Spike
    private static final float SPIKE_WIDTH = 1.5f;
    private static final float SPIKE_HEIGHT = SPIKE_WIDTH;

    public PointItem(float x, float y) {
        super(R.mipmap.item_star);
        setPosition(x, y, SPIKE_WIDTH, SPIKE_HEIGHT);
        srcRect = new Rect(0, 0, 300, 300);
    }

    @Override
    public void setPosition(float x, float y, float width, float height) {
        super.setPosition(x, y, SPIKE_WIDTH, SPIKE_HEIGHT);
    }

    @Override
    public void update(float elapsedSeconds) {
        // Implement spike-specific update logic if needed
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public RectF getCollisionRect() {
        return dstRect;
    }
}
