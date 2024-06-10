package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;

public class Wind extends Sprite implements IBoxCollidable, IRecyclable {
    private static final float WIND_WIDTH = 2.3f;
    private static final float WIND_HEIGHT = WIND_WIDTH * 0.4f;
    private static final float SPEED = 5.0f;
    private int power;

    private Wind(float x, float y, int power) {
        super(R.mipmap.wind);
        setPosition(x, y, WIND_WIDTH, WIND_HEIGHT);
        this.power = power;
        dx = SPEED;
    }

    public static Wind get(float x, float y, int power) {
        Wind wind = (Wind) RecycleBin.get(Wind.class);
        if (wind != null) {
            wind.setPosition(x, y, WIND_WIDTH, WIND_HEIGHT);
            wind.power = power;
            return wind;
        }
        return new Wind(x, y, power);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        if (dstRect.bottom < 0 || dstRect.top > Metrics.height) {
            Scene.top().remove(MainScene.Layer.wind, this);
        }
        if (dstRect.right < 0 || dstRect.left > Metrics.width) {
            Scene.top().remove(MainScene.Layer.wind, this);
        }
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void onRecycle() {
    }

    public int getPower() {
        return power;
    }
}
