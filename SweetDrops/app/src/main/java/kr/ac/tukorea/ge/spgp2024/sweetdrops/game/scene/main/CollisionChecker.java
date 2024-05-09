package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private final MainScene scene;

    public CollisionChecker(MainScene scene) {
        this.scene = scene;
    }

    @Override
    public void update(float elapsedSeconds) {
        ArrayList<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);
        ArrayList<IGameObject> fighter = scene.objectsAt(MainScene.Layer.player); // Fighter 객체 가져오기

        for (int e = enemies.size() - 1; e >= 0; e--) {
            Enemy enemy = (Enemy) enemies.get(e);

            // Fighter와 Enemy 사이의 충돌 체크
            if (CollisionHelper.collides(enemy, (IBoxCollidable) fighter)) {
                // 충돌 시 처리할 작업 수행
                // 여기서는 간단히 Fighter를 제거하도록 함
                System.out.println("충돌");
                // 게임 종료 또는 체력 감소 등의 처리를 추가할 수 있음
                break;
            }
        }
        for (int e = enemies.size() - 1; e >= 0; e--) {
            Enemy enemy = (Enemy)enemies.get(e);
            ArrayList<IGameObject> bullets = scene.objectsAt(MainScene.Layer.bullet);
            for (int b = bullets.size() - 1; b >= 0; b--) {
                Bullet bullet = (Bullet)bullets.get(b);
                if (CollisionHelper.collides(enemy, bullet)) {
                    //Log.d(TAG, "Collision !!");
                    scene.remove(MainScene.Layer.bullet, bullet);
                    boolean dead = enemy.decreaseLife(bullet.getPower());
                    if (dead) {
                        scene.remove(MainScene.Layer.enemy, enemy);
                        scene.addScore(enemy.getScore());
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
