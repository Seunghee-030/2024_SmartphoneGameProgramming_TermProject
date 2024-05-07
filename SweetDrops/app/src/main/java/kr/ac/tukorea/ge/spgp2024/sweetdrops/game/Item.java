import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.game.MainScene;

public class Item extends Sprite implements IBoxCollidable, IRecyclable {
    private static final float ITEM_WIDTH = 0.68f;
    private static final float ITEM_HEIGHT = ITEM_WIDTH * 40 / 28;
    private static final float SPEED = 20.0f;
    private int power;

    private Item(float x, float y, int power) {
        super(R.mipmap.laser_1);
        setPosition(x, y, ITEM_WIDTH, ITEM_HEIGHT);
        this.power = power;
        dy = -SPEED;
    }
    
    private static class Item {
        public Item(float x, float y, int power) {
        }
    }

    public static kr.ac.tukorea.ge.spgp2024.sweetdrops.game.Item get(float x, float y, int power) {
        kr.ac.tukorea.ge.spgp2024.sweetdrops.game.Item item = (kr.ac.tukorea.ge.spgp2024.sweetdrops.game.Item) RecycleBin.get(kr.ac.tukorea.ge.spgp2024.sweetdrops.game.Item.class);
        if (item != null) {
            item.setPosition(x, y, ITEM_WIDTH, ITEM_HEIGHT);
            item.power = power;
            return item;
        }
        return new Item(x, y, power);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        if (dstRect.bottom < 0) {
            Scene.top().remove(MainScene.Layer.item, this);
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