package kr.ac.tukorea.ge.spgp2024.framework.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class StartOverlayView extends View {

    private Paint paint;
    private Paint textPaint;
    private OnStartListener onStartListener;

    public interface OnStartListener {
        void onStart();
    }

    public void setOnStartListener(OnStartListener listener) {
        this.onStartListener = listener;
    }

    public StartOverlayView(Context context) {
        super(context);
        init();
    }

    public StartOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StartOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.argb(150, 100, 170, 50)); // Semi-transparent black

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(100);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        canvas.drawText("Click To Start!!", getWidth() / 2, getHeight() / 2, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (onStartListener != null) {
                onStartListener.onStart();
            }
            return true;
        }
        return super.onTouchEvent(event);
    }
}
