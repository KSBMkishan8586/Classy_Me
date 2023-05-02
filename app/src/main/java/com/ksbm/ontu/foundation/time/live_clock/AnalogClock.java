package com.ksbm.ontu.foundation.time.live_clock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class AnalogClock extends View {
    private static final float CLOCK_RADIUS_PERCENT = 40;
    private static final float BORDER_WIDTH_PERCENT = 3;
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final float MINUTE_TICK_RADIUS_PERCENT = 36;
    private static final float MINUTE_TICK_LENGTH_PERCENT = 4;
    private static final float MINUTE_TICK_WIDTH_PERCENT = .5f;
    private final Paint minuteTickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final float HOUR_TICK_RADIUS_PERCENT = 36;
    private static final float HOUR_TICK_LENGTH_PERCENT = 4;
    private static final float HOUR_TICK_WIDTH_PERCENT = 1.5f;
    private final Paint hourTickPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final float HOUR_NUMBER_RADIUS_PERCENT = 29;
    private static final float HOUR_FONT_SIZE_PERCENT = 7;
    private final Paint hourNumberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final float HOUR_HAND_LENGTH_PERCENT = 20;
    private static final float HOUR_HAND_WIDTH_PERCENT = 4;
    private final Hand hourHand;

    private static final float MINUTE_HAND_LENGTH_PERCENT = 26;
    private static final float MINUTE_HAND_WIDTH_PERCENT = 3;
    private final Hand minuteHand;

    private float cx;
    private float cy;
    private int diameter;

    private static final int SLOP_PERCENT = 20;
    private int slopPixels;

    private int hour;
    private int minute;

    public interface OnTimeChanged {
        void onTimeChanged(int newHour, int newMinute);
    }
    @Nullable
    private OnTimeChanged onTimeChanged;

    /**
     * Which hand are we currently moving?
     */
    //@Nullable
    private Hand movingHand;

    public AnalogClock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // If the time is wrong when debugging this, check the time of your simulator, we should be
        // showing the same thing.
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

       // hour = 12;
       // minute = 05;

       // Log.e("hr_mint", ""+ hour + " "+ minute);

        Paint hourHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hourHandPaint.setColor(Color.BLACK);
        hourHandPaint.setStrokeCap(Paint.Cap.ROUND);
        hourHand = new HourHand(hourHandPaint, HOUR_HAND_WIDTH_PERCENT, HOUR_HAND_LENGTH_PERCENT);
        hourHand.setTime(hour, minute);

        Paint minuteHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        minuteHandPaint.setColor(Color.BLACK);
        minuteHandPaint.setStrokeCap(Paint.Cap.ROUND);
        minuteHand = new MinuteHand(minuteHandPaint, MINUTE_HAND_WIDTH_PERCENT, MINUTE_HAND_LENGTH_PERCENT);
        minuteHand.setTime(hour, minute);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        diameter = Math.min(w, h);
        cx = w / 2f;
        cy = h / 2f;

        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(diameter * BORDER_WIDTH_PERCENT / 100f);

        minuteTickPaint.setColor(Color.BLACK);
        minuteTickPaint.setStyle(Paint.Style.STROKE);
        minuteTickPaint.setStrokeWidth(diameter * MINUTE_TICK_WIDTH_PERCENT / 100f);

        hourTickPaint.setColor(Color.BLACK);
        hourTickPaint.setStyle(Paint.Style.STROKE);
        hourTickPaint.setStrokeWidth(diameter * HOUR_TICK_WIDTH_PERCENT / 100f);

        hourNumberPaint.setColor(Color.BLACK);
        hourNumberPaint.setStyle(Paint.Style.STROKE);
        hourNumberPaint.setTextSize(diameter * HOUR_FONT_SIZE_PERCENT / 100f);

        slopPixels = (diameter * SLOP_PERCENT) / 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (canvas.getWidth() == 0 || canvas.getHeight() == 0) {
            // This can happen while Android Studio tries to preview us
            return;
        }

        drawBorder(canvas);
        drawMinuteTicks(canvas);
        drawHourTicks(canvas);
        drawHourNumbers(canvas);

        minuteHand.draw(canvas, getWidth(), getHeight());
        hourHand.draw(canvas, getWidth(), getHeight());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return onHandMoveStart(event) || super.onTouchEvent(event);
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            return onHandMove(event) || super.onTouchEvent(event);
        }

        return super.onTouchEvent(event);
    }

    private boolean onHandMoveStart(MotionEvent event) {

        double dMinute = minuteHand.getDistanceTo(event.getX(), event.getY());
        double dHour = hourHand.getDistanceTo(event.getX(), event.getY());
        Hand hand=null;
        double distance=0;

        if (dMinute < dHour) {
            hand = minuteHand;
            distance = dMinute;
        } else {
            hand = hourHand;
            distance = dHour;
        }

        if (distance < slopPixels) {
            movingHand = hand;
            return true;
        }

        return false;
    }

    private boolean onHandMove(MotionEvent event) {
        if (movingHand == null) {
            return false;
        }

        movingHand.move(event.getX(), event.getY());
        hour = movingHand.getHour();
        minute = movingHand.getMinute();
        hourHand.setTime(hour, minute);
        minuteHand.setTime(hour, minute);

        invalidate();

        if (onTimeChanged != null) {
            onTimeChanged.onTimeChanged(hour, minute);
        }

        return true;
    }

    public void setOnTimeChanged(@Nullable OnTimeChanged onTimeChanged) {
        this.onTimeChanged = onTimeChanged;
    }

    private void drawHourNumbers(Canvas canvas) {
        for (int i = 1; i <= 12; i++) {
            double radians = (2 * Math.PI) * (i / 12.0);

            float radius = diameter * HOUR_NUMBER_RADIUS_PERCENT / 100f;
            float x0 = cx + (float)(radius * Math.sin(radians));
            float y0 = cy - (float)(radius * Math.cos(radians));

            String hourString = Integer.toString(i);
            Rect textBounds = new Rect();
            hourNumberPaint.getTextBounds(hourString, 0, hourString.length(), textBounds);
            canvas.drawText(
                    hourString,
                    x0 - textBounds.exactCenterX(),
                    y0 - textBounds.exactCenterY(),
                    hourNumberPaint);
        }
    }

    private void drawHourTicks(Canvas canvas) {
        for (int i = 1; i <= 12; i++) {
            double radians = (2 * Math.PI) * (i / 12.0);

            float inner_radius = diameter * (HOUR_TICK_RADIUS_PERCENT
                    - HOUR_TICK_LENGTH_PERCENT / 2f) / 100f;
            float x0 = cx + (float)(inner_radius * Math.sin(radians));
            float y0 = cy - (float)(inner_radius * Math.cos(radians));

            float outer_radius = diameter * (HOUR_TICK_RADIUS_PERCENT
                    + HOUR_TICK_LENGTH_PERCENT / 2f) / 100f;
            float x1 = cx + (float)(outer_radius * Math.sin(radians));
            float y1 = cy - (float)(outer_radius * Math.cos(radians));

            canvas.drawLine(x0, y0, x1, y1, hourTickPaint);
        }
    }

    private void drawMinuteTicks(Canvas canvas) {
        for (int i = 0; i <= 59; i++) {
            double radians = (2 * Math.PI) * (i / 60.0);

            float inner_radius = diameter * (MINUTE_TICK_RADIUS_PERCENT
                    - MINUTE_TICK_LENGTH_PERCENT / 2f) / 100f;
            float x0 = cx + (float)(inner_radius * Math.sin(radians));
            float y0 = cy - (float)(inner_radius * Math.cos(radians));

            float outer_radius = diameter * (MINUTE_TICK_RADIUS_PERCENT
                    + MINUTE_TICK_LENGTH_PERCENT / 2f) / 100f;
            float x1 = cx + (float)(outer_radius * Math.sin(radians));
            float y1 = cy - (float)(outer_radius * Math.cos(radians));

            canvas.drawLine(x0, y0, x1, y1, minuteTickPaint);
        }
    }

    private void drawBorder(Canvas canvas) {
        canvas.drawCircle(
                cx, cy,
                diameter * CLOCK_RADIUS_PERCENT / 100f,
                borderPaint);
    }

    /**
     * @return 0-23
     */
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
