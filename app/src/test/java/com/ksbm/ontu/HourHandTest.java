
package com.ksbm.ontu;

import com.ksbm.ontu.foundation.time.live_clock.HourHand;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class HourHandTest {
    private void testSetHour(int startHour, int setHour, int correctResult) {
        //noinspection ConstantConditions
        HourHand testMe = new HourHand(null, 0, 0);
        testMe.setTime(startHour, 0);
        testMe.setHour(setHour);
        Assert.assertThat(testMe.getHour(), is(correctResult));
    }

    @Test
    public void testSetHourEasy() {
        testSetHour(6, 5, 5);
    }

    @Test
    public void testSetHourAfternoon() {
        testSetHour(15, 4, 16);
    }

    @Test
    public void testSetHourMidnightForward() {
        testSetHour(23, 1, 1);
    }

    @Test
    public void testSetHourMidnightBackward() {
        testSetHour(1, 11, 23);
    }

    @Test
    public void testSetHourNoonForward() {
        testSetHour(11, 1, 13);
    }

    @Test
    public void testSetHourNoonBackward() {
        testSetHour(13, 11, 11);
    }
}
