package kr.ac.tukorea.ge.spgp2024.sweetdrops.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.game.MainScene;

public class Item extends Sprite implements IBoxCollidable, IRecyclable {
    //아이템 크기
    private static final float ITEM_RADIUS = 0.6f;
    private static final float gravity = 5.0f;           // 낙하 속도
    private int type;                                   // 아이템 종류

    Item(int type, float x, float y) {
        super(R.mipmap.candy);              // 이미지 리소스
        setPosition(x, y, ITEM_RADIUS, ITEM_RADIUS);
        this.type = type;
        dy = -gravity;
    }

    public static Item get(float x, float y, int type) {
        Item item = (Item) RecycleBin.get(Item.class);
        if (item != null) {
            item.setPosition(x, y, ITEM_RADIUS, ITEM_RADIUS);
            item.type = type;
            return item;
        }
        return new Item(type,x, y);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);

        // 중력 적용
        dy += gravity * elapsedSeconds;

        // 캐릭터 이동
        y += dy * elapsedSeconds;
        //x += dx * elapsedSeconds;

        if (dstRect.bottom < 0) {
            Scene.top().remove(MainScene.Layer.item, this);
        }
    }
    public void draw(Canvas canvas) {
        super.draw(canvas);

    }
    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void onRecycle() {

    }

    public int getType() {
        return type;
    }
}
