package kr.ac.tukorea.ge.spgp.seung.cuttherope;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean IsPlaying;
    private int screenX, screenY;
    private Background background1, background2;

    private Player player;
    private Item item;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());

        background2.y = -screenY;

        player = new Player(context, screenX, screenY);
    }

    @Override
    public void run() {
        while (IsPlaying) {
            update();
            draw();
            sleep();
        }
    }

    private void update() {
        //background1.y += 10; 버퍼 두개 생성
        //background2.y += 10;

        if (background1.y > screenY) {
            background1.y = -screenY;
        }

        if (background2.y > screenY) {
            background2.y = -screenY;
        }
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, null);

            if (background1.y > 0) {
                canvas.drawBitmap(background2.background, background2.x, background2.y, null);
            }
            canvas.drawBitmap(player.playerBitmap, player.x, player.y, null);
            //canvas.drawBitmap(item.itemBitmap, item.x, player.y, null);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        IsPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            IsPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                player.x = (int) event.getX() - player.playerBitmap.getWidth() / 2;
                player.y = (int) event.getY() - player.playerBitmap.getHeight() / 2;
                break;
        }
        return true;
    }
}
