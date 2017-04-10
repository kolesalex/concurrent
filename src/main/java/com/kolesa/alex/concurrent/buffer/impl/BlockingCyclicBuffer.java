package com.kolesa.alex.concurrent.buffer.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.kolesa.alex.concurrent.buffer.Buffer;

public class BlockingCyclicBuffer implements Buffer {

    private final int capacity;
    private int mainIndex;
    private int endIndex;

    private List<String> data;

    private final Lock lock = new ReentrantLock(true);
    private final Condition isNotEmpty = lock.newCondition();
    private final Condition isNotFull = lock.newCondition();

    public BlockingCyclicBuffer(int capacity) {
        this.capacity = capacity;
        data = new LinkedList<>(Arrays.asList(new String[capacity]));
        mainIndex = 0;
        endIndex = 0;
    }

    @Override
    public void put(String element) throws InterruptedException {
        lock.lock();
        try {
            while(isFull()){
                isNotFull.await();
            }
            data.set(endIndex, element);
            endIndex = ++endIndex % capacity;
            isNotEmpty.signal();
        } finally {
            lock.unlock();
        }

    }

    private boolean isFull() {
        return (endIndex + 1) % capacity == mainIndex % capacity;
    }

    @Override
    public String get() throws InterruptedException {
        lock.lock();
        while(isEmpty()){
            isNotEmpty.await();
        }
        String result = data.get(mainIndex);
        data.set(mainIndex, null);
        mainIndex = ++mainIndex % capacity;
        isNotFull.signal();
        return result;
    }

    private boolean isEmpty() {
        return mainIndex == endIndex;
    }


}
