package com.mind2web.vb360.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RippleView extends View {

    private Paint paint;
    private List<Ripple> ripples = new ArrayList<>();
    private int centerX, centerY, maxRadius;
    private Handler rippleHandler;
    private static final int RIPPLE_INTERVAL = 500; // Time between ripples

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#6200EE")); // Ripple color
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        rippleHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        maxRadius = Math.min(w, h) / 2;
        startRippleAnimation();
    }

    private void startRippleAnimation() {
        rippleHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ripples.add(new Ripple(0, 255)); // Add new ripple
                invalidate(); // Refresh view to show updates
                rippleHandler.postDelayed(this, RIPPLE_INTERVAL); // Repeat continuously
            }
        }, RIPPLE_INTERVAL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Iterator<Ripple> iterator = ripples.iterator();

        while (iterator.hasNext()) {
            Ripple ripple = iterator.next();
            paint.setAlpha(ripple.alpha);
            canvas.drawCircle(centerX, centerY, ripple.radius, paint);
            ripple.update();

            if (ripple.alpha <= 0) {
                iterator.remove(); // Remove faded-out ripples
            }
        }

        invalidate(); // Continuously redraw
    }

    private class Ripple {
        float radius;
        int alpha;

        Ripple(float radius, int alpha) {
            this.radius = radius;
            this.alpha = alpha;
        }

        void update() {
            radius += 5; // Increase size
            alpha -= 5; // Fade out effect
        }
    }
}
