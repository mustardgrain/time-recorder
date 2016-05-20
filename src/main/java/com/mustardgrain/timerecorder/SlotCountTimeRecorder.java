package com.mustardgrain.timerecorder;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * SlotCountTimeRecorder is a lightweight data structure to record the number of
 * unique time slots and their respective counts.
 * <p>
 * Not thread safe
 */

public class SlotCountTimeRecorder extends AbstractTimeRecorder {

    private SortedMap<Short, MutableLong> records;

    public SlotCountTimeRecorder() {
        records = new TreeMap<>();
    }

    @Override
    public void record(short time) {
        MutableLong count = records.get(time);

        if (count == null)
            records.put(time, new MutableLong(1));
        else
            count.value++;
    }

    @Override
    public long total() {
        long total = 0;

        for (MutableLong count : records.values())
            total += count.value;

        return total;
    }

    @Override
    public short percentile(double percentile) {
        long goal = checkPercentile(percentile);
        long curr = 0;
        Short currTimeSlot = null;

        for (Map.Entry<Short, MutableLong> entry : records.entrySet()) {
            currTimeSlot = entry.getKey();
            MutableLong count = entry.getValue();
            curr += count.value;

            if (curr > goal)
                break;
        }

        return currTimeSlot != null ? currTimeSlot : (short) -1;
    }

    public void merge(SlotCountTimeRecorder other) {
        for (Map.Entry<Short, MutableLong> entry : other.records.entrySet()) {
            Short timeSlot = entry.getKey();
            MutableLong otherCount = entry.getValue();
            MutableLong count = records.get(timeSlot);

            if (count == null)
                records.put(timeSlot, new MutableLong(otherCount.value));
            else
                count.value += otherCount.value;
        }
    }

    public Set<Short> timeSlots() {
        return records.keySet();
    }

}
