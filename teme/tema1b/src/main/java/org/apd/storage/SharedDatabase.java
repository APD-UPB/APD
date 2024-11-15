package org.apd.storage;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/* DO NOT MODIFY */
public final class SharedDatabase {
    private final ArrayList<Entry> buffer;

    public SharedDatabase(int size, int blockSize, long readDuration, long writeDuration) {
        buffer = IntStream.range(0, size).mapToObj(e -> new Entry(blockSize, readDuration, writeDuration)).collect(Collectors.toCollection(ArrayList::new));
    }

    private void setData(int index, byte[] data) {
        buffer.get(index).setContent(data);
    }

    public EntryResult addData(int index, String data) {
        setData(index, data.getBytes(StandardCharsets.UTF_8));

        return getData(index);
    }

    private byte[] getBytes(int index) {
        return buffer.get(index).getContent();
    }

    public EntryResult getData(int index) {
        return new EntryResult(index, new String(getBytes(index), StandardCharsets.UTF_8), buffer.get(index).getSequence());
    }

    public int getSize() {
        return buffer.size();
    }
}
