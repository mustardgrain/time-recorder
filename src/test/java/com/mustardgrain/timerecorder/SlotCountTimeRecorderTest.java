package com.mustardgrain.timerecorder;

public class SlotCountTimeRecorderTest extends TimeRecorderTest {

    @Override
    protected TimeRecorder newTimeRecorder(int count) {
        return new SlotCountTimeRecorder();
    }

}
