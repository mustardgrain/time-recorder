package com.mustardgrain.timerecorder;

import org.junit.Assert;
import org.junit.Test;

public abstract class TimeRecorderTest {

    protected abstract TimeRecorder newTimeRecorder(int count);

    @Test
    public void smokeTest_1() {
        TimeRecorder recorder = newTimeRecorder(1);
        recorder.record((short) 4);

        Assert.assertEquals(1, recorder.total());
        Assert.assertEquals(4, (int) recorder.percentile(0));
        Assert.assertEquals(4, (int) recorder.percentile(0.5));
        Assert.assertEquals(4, (int) recorder.percentile(1));
    }

    @Test
    public void smokeTest_2() {
        TimeRecorder recorder = newTimeRecorder(5);
        recorder.record((short) 0);
        recorder.record((short) 1);
        recorder.record((short) 2);
        recorder.record((short) 3);
        recorder.record((short) 4);

        Assert.assertEquals(5, recorder.total());
        Assert.assertEquals((short) 2, recorder.percentile(0.5));
    }

    @Test
    public void smokeTest_3() {
        TimeRecorder recorder = newTimeRecorder(4);
        recorder.record((short) 0);
        recorder.record((short) 0);
        recorder.record((short) 1);
        recorder.record((short) 1);

        Assert.assertEquals(4, recorder.total());
        Assert.assertEquals(0, (long) recorder.percentile(0));
        Assert.assertEquals(1, (long) recorder.percentile(0.5));
        Assert.assertEquals(1, (long) recorder.percentile(1));
    }

    @Test
    public void smokeTest_4() {
        TimeRecorder recorder = newTimeRecorder(100);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++)
                recorder.record((short) i);
        }

        Assert.assertEquals(100, recorder.total());
        Assert.assertEquals(0, (long) recorder.percentile(0));
        Assert.assertEquals(5, (long) recorder.percentile(0.5));
        Assert.assertEquals(9, (long) recorder.percentile(0.9));
        Assert.assertEquals(9, (long) recorder.percentile(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoRecords() {
        TimeRecorder recorder = newTimeRecorder(0);

        Assert.assertEquals(0, recorder.total());
        Assert.assertEquals(-1, recorder.percentile(0.5));
    }

    @Test
    public void allowZero() {
        TimeRecorder recorder = newTimeRecorder(1);
        recorder.record((short) 1);
        recorder.percentile(0.0);
        recorder.percentile(-0.0);
    }

    @Test
    public void allowOne() {
        TimeRecorder recorder = newTimeRecorder(1);
        recorder.record((short) 1);
        recorder.percentile(1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectNegative() {
        TimeRecorder recorder = newTimeRecorder(1);
        recorder.record((short) 1);
        recorder.percentile(-0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectGreaterThanOne() {
        TimeRecorder recorder = newTimeRecorder(1);
        recorder.record((short) 1);
        recorder.percentile(1.01);
    }

}
