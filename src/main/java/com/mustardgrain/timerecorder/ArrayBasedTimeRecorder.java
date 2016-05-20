package com.mustardgrain.timerecorder;

import java.util.Arrays;

/**
 * ArrayBasedTimeRecorder uses a simple array to record timings.
 * <p>
 * Not thread safe
 */

public class ArrayBasedTimeRecorder extends AbstractTimeRecorder {

    private short[] records;

    private int pos;

    private boolean hasSorted;

    public ArrayBasedTimeRecorder(int iterations) {
        this.records = new short[iterations];
        this.pos = 0;
    }

    @Override
    public void record(short time) {
        records[pos++] = time;
        hasSorted = false;
    }

    @Override
    public long total() {
        return records.length;
    }

    @Override
    public short percentile(double percentile) {
        int goal = checkPercentile(percentile);

        if (!hasSorted) {
            Arrays.sort(records);
            hasSorted = true;
        }

        return records[goal];
    }

    public void merge(ArrayBasedTimeRecorder other) {
        System.arraycopy(other.records, 0, this.records, pos, other.records.length);
        pos += other.records.length;
        hasSorted = false;
    }


}
