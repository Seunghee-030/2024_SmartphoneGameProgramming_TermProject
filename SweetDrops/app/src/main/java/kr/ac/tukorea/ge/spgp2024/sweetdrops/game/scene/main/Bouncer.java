package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;

public class Bouncer extends Sprite implements IBoxCollidable, IRecyclable {
    private static final float BOUNCER_WIDTH = 3.0f;
    private static final float BOUNCER_HEIGHT = BOUNCER_WIDTH * 30 / 40;
    private static final float SPEED = 2.0f;
    private int power;

    private Bouncer(float x, float y, int power) {
        super(R.mipmap.obj_bouncer_01);
        setPosition(x, y, BOUNCER_WIDTH, BOUNCER_HEIGHT);
        this.power = power;
        dy = -SPEED;
    }
    public static Bouncer get(float x, float y, int power) {
        Bouncer bouncer = (Bouncer) RecycleBin.get(Bouncer.class);
        if (bouncer != null) {
            bouncer.setPosition(x, y, BOUNCER_WIDTH, BOUNCER_HEIGHT);
            bouncer.power = power;
            return bouncer;
        }
        return new Bouncer(x, y, power);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);


        if (dstRect.bottom < 0) {
            Scene.top().remove(MainScene.Layer.obstacle, this);
        }
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void onRecycle() {

    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.save();

        float width = dstRect.width() * 0.7f;
        canvas.translate(x - width / 2, dstRect.bottom);
        canvas.scale(width, width);
        canvas.restore();
    }
    public int getPower() {
        return power;
    }
}
