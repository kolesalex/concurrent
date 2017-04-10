package com.kolesa.alex.concurrent.buffer.interactors;

import com.kolesa.alex.concurrent.buffer.Buffer;

public class Producer implements Runnable {

    private static final int DELAY = 10; // added in order to make even distribution of producing

    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int msgId = 0;
        while(!Thread.interrupted()){
            try {
                buffer.put(getMessagePrefix() + msgId++);
                Thread.sleep(DELAY);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    private String getMessagePrefix() {
        return "Thread #" + Thread.currentThread().getId() + " generated message: ";
    }
}
