package kr.ac.tukorea.ge.spgp.seung.cuttherope;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    int x = 0, y = 0;
    Bitmap background;

    public Background(int screenX, int screenY, Resources res) {

        // 배경 이미지를 로드합니다.
        background = BitmapFactory.decodeResource(res, R.drawable.background);

        // 배경 이미지를 화면 크기에 맞게 스케일링합니다.
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);
    }
}
