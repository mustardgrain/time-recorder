package com.mustardgrain.timerecorder;

/**
 * TimeRecorder is a lightweight data structure to record the number of
 * unique time slots and their respective countRecords.
 * <p>
 * This is useful for determining latency percentile, etc.
 * <p>
 * Not thread safe
 */

public interface TimeRecorder {

    public void record(short time);

    public long total();

    public short percentile(double percentile);

}
