package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;

public class Spike extends Sprite {
    private static final String TAG = Spike.class.getSimpleName(); // Corrected from Monster to Spike
    private static final float IMAGE_ASPECT_RATIO = 34 / 14;
    private static final float SPIKE_WIDTH = 3f;
    private static final float SPIKE_HEIGHT = SPIKE_WIDTH / IMAGE_ASPECT_RATIO;

    public Spike(float x, float y) {
        super(R.mipmap.obj_spikes_01);
        setPosition(x, y, SPIKE_WIDTH, SPIKE_HEIGHT);
        srcRect = new Rect(0, 0, 182, 72);
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
