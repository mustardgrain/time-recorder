package com.mustardgrain.timerecorder;

public class ArrayBasedTimeRecorderTest extends TimeRecorderTest {

    @Override
    protected TimeRecorder newTimeRecorder(int count) {
        return new ArrayBasedTimeRecorder(count);
    }

}
