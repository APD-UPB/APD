package org.apd.storage;

import java.util.Arrays;
import java.util.Random;

/* DO NOT MODIFY */
public final class Entry {
    private final long readDuration;
    private final long writeDuration;
    private final byte[] block;
    private int size;
    private int sequence;

    public Entry(int blockSize, long readDuration, long writeDuration) {
        this.readDuration = readDuration;
        this.writeDuration = writeDuration;
        this.block = new byte[blockSize];
        this.size = 0;
    }

    public void setContent(byte[] data) {
        size = data.length;
        ++sequence;

        for (int i = 0; i < data.length; i++) {
            block[i] = data[i];
        }

        TryRandomSleep(readDuration);

        if (size != data.length) {
            throw new RuntimeException("Size mismatch");
        }

        for (int i = 0; i < data.length; i++) {
            if (block[i] != data[i]) {
                throw new RuntimeException("Block mismatch");
            }
        }
    }

    public byte[] getContent()
    {
        var result = Arrays.copyOf(block, size);

        TryRandomSleep(writeDuration);

        return result;
    }

    private static void TryRandomSleep(long duration)
    {
        try {
            Thread.sleep(duration + new Random().nextLong(- duration / 10, duration / 10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getSequence() {
        return sequence;
    }
}
