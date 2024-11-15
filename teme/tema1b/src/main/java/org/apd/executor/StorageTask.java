package org.apd.executor;

/* DO NOT MODIFY */
public record StorageTask(int index, String data) {
    public boolean isWrite() {
        return data != null;
    }
}
