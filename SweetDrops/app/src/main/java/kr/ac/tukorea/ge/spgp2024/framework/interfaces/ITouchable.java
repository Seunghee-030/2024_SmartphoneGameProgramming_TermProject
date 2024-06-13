package kr.ac.tukorea.ge.spgp2024.framework.interfaces;

import android.graphics.RectF;
import android.view.MotionEvent;

public interface ITouchable {
    RectF getCollisionRect();
    public boolean onTouch(MotionEvent event);
}
