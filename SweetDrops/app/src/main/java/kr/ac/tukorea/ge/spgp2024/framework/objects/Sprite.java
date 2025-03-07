package kr.ac.tukorea.ge.spgp2024.framework.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main.Vector2;

public class Sprite implements IGameObject {
    private static final String TAG = Sprite.class.getSimpleName();
    protected Bitmap bitmap;
    protected Rect srcRect;
    protected final RectF dstRect = new RectF();
    protected float posX, posY, dx, dy;
    protected Vector2 position;
    protected float width, height, radius;

    public Sprite(int mipmapId) {
        if (mipmapId != 0) {
            bitmap = BitmapPool.get(mipmapId);
        }
        Log.v(TAG, "Created " + this.getClass().getSimpleName() + "@" + System.identityHashCode(this));
    }

    public Sprite(int mipmapId, float x, float y, float width, float height) {
        if (mipmapId != 0) {
            bitmap = BitmapPool.get(mipmapId);
        }
        setPosition(x, y, width, height);
    }
    public void setPosition(float x, float y, float radius) {
        this.posX = x;
        this.posY = y;
        this.width = this.height = 2 * radius;
        dstRect.set(x - radius, y - radius, x + radius, y + radius);
    }
    public void setPosition(float x, float y, float width, float height) {
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = height;
        radius = Math.min(width, height) / 2;
        dstRect.set(x - width / 2, y - height / 2, x + width / 2, y + height / 2);
    }
    @Override
    public void update(float elapsedSeconds) {
        float timedDx = dx * elapsedSeconds;
        float timedDy = dy * elapsedSeconds;
        posX += timedDx;
        posY += timedDy;
        dstRect.offset(timedDx, timedDy);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
