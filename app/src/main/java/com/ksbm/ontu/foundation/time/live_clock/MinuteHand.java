
package com.ksbm.ontu.foundation.time.live_clock;

import android.graphics.Paint;

public class MinuteHand extends Hand {
    public MinuteHand(Paint paint, float widthPercent, float lengthPercent) {
        super(paint, widthPercent, lengthPercent);
    }

    @Override
    protected double getRadians() {
        return (2 * Math.PI) * (minute / 60.0);
    }

    @Override
    public void move(float x, float y) {
        if (x == centerX && y == centerY) {
            // Hitting the center has undefined direction
            return;
        }

        // Compute the new radians value
        float dx = x - centerX;
        float dy = y - centerY;
        double radians = Math.atan2(dx, -dy);

        // Update the time based on the new radians value
        int newMinute = (int) Math.floor((radians / (2 * Math.PI)) * 60.0);

        // Minute is now -30 to 29, we want it to be 0-59
        while (newMinute < 0) {
            newMinute += 60;
        }
        setMinute(newMinute);
    }

    private void setMinute(int newMinute) {
        // Handle change when moving the minute between 0 and 59
        if (minute > 45 && newMinute < 15) {
            hour++;
        } else if (minute < 15 && newMinute > 45) {
            hour--;
        }
        minute = newMinute;

        while (hour < 0) {
            hour += 24;
        }
        while (hour >= 24) {
            hour -= 24;
        }
    }
}
