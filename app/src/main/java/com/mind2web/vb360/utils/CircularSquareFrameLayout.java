package com.mind2web.vb360.utils;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class CircularSquareFrameLayout extends FrameLayout {

    private Path clipPath;

    public CircularSquareFrameLayout(Context context) {
        super(context);
        init();
    }

    public CircularSquareFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularSquareFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        clipPath = new Path();
        setWillNotDraw(false); // Ensure the view draws its content
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Get the minimum of width and height to maintain a square shape
        int size = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        int squareMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);

        super.onMeasure(squareMeasureSpec, squareMeasureSpec); // Set both dimensions to the same value
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float radius = Math.min(w, h) / 2f; // Radius is half the size of the square
        clipPath.reset();
        clipPath.addCircle(w / 2f, h / 2f, radius, Path.Direction.CW); // Define the circular clipping path
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(clipPath); // Apply the circular clip
        super.dispatchDraw(canvas);
        canvas.restore();
    }
}