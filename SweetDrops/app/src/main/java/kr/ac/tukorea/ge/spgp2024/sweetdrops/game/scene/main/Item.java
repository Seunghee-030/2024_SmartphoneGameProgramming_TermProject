package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import static kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main.MainScene.Layer.item;

import android.graphics.Canvas;
import android.graphics.RectF;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2024.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.util.Gauge;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
public class Item extends AnimSprite implements IBoxCollidable, IRecyclable {
    private static final float GRAVITY = 2.0f; // 중력 가속도
    private static final float SPEED = 2.0f;
    private static final float RADIUS = 0.6f;
    private static final int[] resIds = {
            R.mipmap.candy };

    public static final int MAX_LEVEL = resIds.length - 1;
    public static final float ANIM_FPS = 10.0f;
    protected RectF collisionRect = new RectF();
    private int level;
    private int life, maxLife;

    protected static Gauge gauge = new Gauge(0.1f, R.color.enemy_gauge_fg, R.color.enemy_gauge_bg);

    private Item(int level, int index) {
        super(0, 0);
        init(level, index);
        dy = SPEED;
    }

    private void init(int level, int index) {
        this.level = level;
        this.life = this.maxLife = (level + 1) * 10;
        setAnimationResource(resIds[level], ANIM_FPS);
        setPosition(Metrics.width / 2, -RADIUS, RADIUS); // 화면 가운데 맨 위에서 생성

        dy = SPEED; // 초기 속도 설정
    }

    public static Item get(int level, int index) {
        Item item = (Item) RecycleBin.get(Item.class);
        if (item != null) {
            item.init(level, index);
            return item;
        }
        return new Item(level, index);
    }
    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
        // 중력에 따른 속도 변경
        dy += GRAVITY * elapsedSeconds;
        // 공의 위치 변경
        y += dy * elapsedSeconds;

        // X축 경계에 닿을 때 속도 반전
        if (dx > 0 && dstRect.right > Metrics.width) {
            // 오른쪽 벽에 닿았을 때
            x = Metrics.width - dstRect.width(); // 화면 안으로 이동
            dx = -dx; // X축 속도 반전
        } else if (dx < 0 && dstRect.left < 0) {
            // 왼쪽 벽에 닿았을 때
            x = 0; // 화면 안으로 이동
            dx = -dx; // X축 속도 반전
        }

        // Y축 경계에 닿을 때 속도 반전
        if (dy > 0 && dstRect.bottom > Metrics.height) {
            // 아래쪽 벽에 닿았을 때
            y = Metrics.height - dstRect.height(); // 화면 안으로 이동
            dy = -dy; // Y축 속도 반전
        } else if (dy < 0 && dstRect.top < 0) {
            // 위쪽 벽에 닿았을 때
            y = 0; // 화면 안으로 이동
            dy = -dy; // Y축 속도 반전
        }


        // 아이템과 몬스터의 충돌 감지
        MainScene scene = (MainScene) Scene.top();
        if (scene != null) {
            Monster monster = scene.getMonster(); // 몬스터 인스턴스 가져오기 (이를 위해 MainScene 클래스에 getMonster() 메소드가 필요합니다.)
            if (monster != null && isCollidingWith(monster)) {
                System.out.println("충돌 - 아이템, 몬스터");
                scene.remove(item, this);
                scene.addScore(100000);
            }
        }
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.save();

        float width = dstRect.width() * 0.7f;
        canvas.translate(x - width / 2, dstRect.bottom);
        canvas.scale(width, width);
        //gauge.draw(canvas, (float)life / maxLife);
        canvas.restore();
    }

    public boolean isCollidingWith(Monster monster) {
        RectF itemRect = getCollisionRect();
        RectF monsterRect = monster.getCollisionRect();
        return RectF.intersects(itemRect, monsterRect);
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void onRecycle() {

    }

    public int getScore() {
        return (level + 1) * 100;
    }

    public boolean decreaseLife(int power) {
        life -= power;
        return life <= 0;
    }
}