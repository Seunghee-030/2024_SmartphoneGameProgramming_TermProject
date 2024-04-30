package kr.ac.tukorea.ge.spgp.seung.cuttherope;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Item {
    int x, y;
    Bitmap candyBitmap;
    Bitmap jellyBitmap;

    public Item(Context context, int screenX, int screenY) {
        candyBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.candy);
        x = screenX / 2;
        y = screenY - candyBitmap.getHeight();
    }

}
