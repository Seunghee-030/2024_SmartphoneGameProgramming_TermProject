package kr.ac.tukorea.ge.spgp.seung.cuttherope;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Player {
    int x, y;
    Bitmap playerBitmap;

    public Player(Context context, int screenX, int screenY) {
        playerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        x = screenX / 2;
        y = screenY - playerBitmap.getHeight();
    }
}