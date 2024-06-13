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
    private static final float SPEED = 3.0f;
    private int power;
    private Direction direction;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Wind(float x, float y, int power, Direction direction) {
        super(R.mipmap.wind);
        setPosition(x, y, WIND_WIDTH, WIND_HEIGHT);
        this.power = power;
        this.direction = direction;
        setDirectionVelocity(direction);
    }

    public static Wind get(float x, float y, int power, Direction direction) {
        Wind wind = (Wind) RecycleBin.get(Wind.class);
        if (wind != null) {
            wind.setPosition(x, y, WIND_WIDTH, WIND_HEIGHT);
            wind.power = power;
            wind.direction = direction;
            wind.setDirectionVelocity(direction);
            return wind;
        }
        return new Wind(x, y, power, direction);
    }

    private void setDirectionVelocity(Direction direction) {
        switch (direction) {
            case UP:
                dx = 0;
                dy = -SPEED;
                break;
            case DOWN:
                dx = 0;
                dy = SPEED;
                break;
            case LEFT:
                dx = -SPEED;
                dy = 0;
                break;
            case RIGHT:
                dx = SPEED;
                dy = 0;
                break;
        }
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        posX += dx * elapsedSeconds/2;
        posY += dy * elapsedSeconds/2;
        dstRect.offset(dx * elapsedSeconds, dy * elapsedSeconds);
        if (dstRect.bottom < 0 || dstRect.top > Metrics.height || dstRect.right < 0 || dstRect.left > Metrics.width) {
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
