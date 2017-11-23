package com.server.util;

import java.util.concurrent.atomic.AtomicInteger;

public class Sequence {

    private static final AtomicInteger counter = new AtomicInteger();
    private static final AtomicInteger counter2 = new AtomicInteger();

    public static int nextJoinId() {
        return counter.getAndIncrement();
    }

    public static int nextRoomRef() {
        return counter2.getAndIncrement();
    }
}
