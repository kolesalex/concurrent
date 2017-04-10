package com.kolesa.alex.concurrent;

import com.kolesa.alex.concurrent.buffer.impl.BlockingCyclicBuffer;
import com.kolesa.alex.concurrent.buffer.Buffer;

public class Main {

    public static final int CAPACITY = 10;

    public static void main(String[] args) {
        Buffer buffer1 = new BlockingCyclicBuffer(CAPACITY);
        Buffer buffer2 = new BlockingCyclicBuffer(CAPACITY);

        Task task = new Task(buffer1, buffer2);
        
        task.execute();
    }
}
