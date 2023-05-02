package com.ksbm.ontu.foundation;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.ksbm.ontu.foundation.model.PointModel;

import java.util.ArrayList;

public class ArrowLayout extends RelativeLayout {

    public static final String PROPERTY_X = "PROPERTY_X";
    public static final String PROPERTY_Y = "PROPERTY_Y";

    private final static double ARROW_ANGLE = Math.PI / 6;
    private final static double ARROW_SIZE = 50;

    private Paint mPaint= new Paint();;

    private boolean mDrawArrow = false;
    private Point mPointFrom = new Point();   // current (during animation) arrow start point
    private Point mPointTo = new Point();     // current (during animation)  arrow end point

    ArrayList<PointModel> all_points= new ArrayList<>();

    public ArrowLayout(Context context) {
        super(context);
        init();
    }

    public ArrowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArrowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ArrowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setWillNotDraw(false);
      //  mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);

    }

    Canvas canvas=new Canvas();
    @Override
    public void dispatchDraw(Canvas canvs) {
        super.dispatchDraw(canvs);
        this.canvas=canvs;
        canvas.save();
        if (mDrawArrow) {
            all_points.add(new PointModel(mPointFrom, mPointTo));

            for (int i=0; i<all_points.size(); i++){
                drawArrowLines(all_points.get(i).getmPointFrom(), all_points.get(i).getmPointTo(), canvas);
            }

        }
        canvas.restore();
    }

    private Point calcPointFrom(Rect fromViewBounds, Rect toViewBounds) {
        Point pointFrom = new Point();

        pointFrom.x = fromViewBounds.right;
        pointFrom.y = fromViewBounds.top + (fromViewBounds.bottom - fromViewBounds.top) / 2;

        return pointFrom;
    }


    private Point calcPointTo(Rect fromViewBounds, Rect toViewBounds) {
        Point pointTo = new Point();

        pointTo.x = toViewBounds.left;
        pointTo.y = toViewBounds.top + (toViewBounds.bottom - toViewBounds.top) / 2;

        return pointTo;
    }


    private void drawArrowLines(Point pointFrom, Point pointTo, Canvas canvas) {
        canvas.drawLine(pointFrom.x, pointFrom.y, pointTo.x, pointTo.y, mPaint);

        double angle = Math.atan2(pointTo.y - pointFrom.y, pointTo.x - pointFrom.x);

        int arrowX, arrowY;

        arrowX = (int) (pointTo.x - ARROW_SIZE * Math.cos(angle + ARROW_ANGLE));
        arrowY = (int) (pointTo.y - ARROW_SIZE * Math.sin(angle + ARROW_ANGLE));
        canvas.drawLine(pointTo.x, pointTo.y, arrowX, arrowY, mPaint);

        arrowX = (int) (pointTo.x - ARROW_SIZE * Math.cos(angle - ARROW_ANGLE));
        arrowY = (int) (pointTo.y - ARROW_SIZE * Math.sin(angle - ARROW_ANGLE));
        canvas.drawLine(pointTo.x, pointTo.y, arrowX, arrowY, mPaint);

    }

    public void animateArrows(int duration, int from_view, int to_view) {
        mDrawArrow = true;

        View fromView = getChildAt(from_view);
        View toView = getChildAt(to_view);

        // find from and to views bounds
        Rect fromViewBounds = new Rect();
        fromView.getDrawingRect(fromViewBounds);
        offsetDescendantRectToMyCoords(fromView, fromViewBounds);

        Rect toViewBounds = new Rect();
        toView.getDrawingRect(toViewBounds);
        offsetDescendantRectToMyCoords(toView, toViewBounds);

        // calculate arrow sbegin and end points
        Point pointFrom = calcPointFrom(fromViewBounds, toViewBounds);
        Point pointTo = calcPointTo(fromViewBounds, toViewBounds);

        ValueAnimator arrowAnimator = createArrowAnimator(pointFrom, pointTo, duration);
        arrowAnimator.start();
    }

    private ValueAnimator createArrowAnimator(Point pointFrom, Point pointTo, int duration) {

        final double angle = Math.atan2(pointTo.y - pointFrom.y, pointTo.x - pointFrom.x);

        mPointFrom.x = pointFrom.x;
        mPointFrom.y = pointFrom.y;

        int firstX = (int) (pointFrom.x + ARROW_SIZE * Math.cos(angle));
        int firstY = (int) (pointFrom.y + ARROW_SIZE * Math.sin(angle));

        PropertyValuesHolder propertyX = PropertyValuesHolder.ofInt(PROPERTY_X, firstX, pointTo.x);
        PropertyValuesHolder propertyY = PropertyValuesHolder.ofInt(PROPERTY_Y, firstY, pointTo.y);

        ValueAnimator animator = new ValueAnimator();
        animator.setValues(propertyX, propertyY);
        animator.setDuration(duration);
        // set other interpolator (if needed) here:
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mPointTo.x = (int) valueAnimator.getAnimatedValue(PROPERTY_X);
                mPointTo.y = (int) valueAnimator.getAnimatedValue(PROPERTY_Y);

                invalidate();
            }
        });

        return animator;
    }

}
