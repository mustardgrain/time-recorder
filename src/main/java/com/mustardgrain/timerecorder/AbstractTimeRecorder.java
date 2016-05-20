package com.mustardgrain.timerecorder;

/**
 * TimeRecorder is a lightweight data structure to record the number of
 * unique time slots and their respective countRecords.
 * <p>
 * This is useful for determining latency percentile, etc.
 * <p>
 * Not thread safe
 */

public abstract class AbstractTimeRecorder implements TimeRecorder {

    protected int checkPercentile(double percentile) {
        if (percentile < 0.0 || percentile > 1.0)
            throw new IllegalArgumentException(String.valueOf(percentile));

        long total = total();
        long goal = (long) ((double) total * percentile);

        if (percentile == 1.0 && total > 0)
            return (int) (goal - 1);

        if (goal < 0 || goal >= total)
            throw new IllegalArgumentException(String.valueOf(percentile));

        return (int) goal;
    }

}
