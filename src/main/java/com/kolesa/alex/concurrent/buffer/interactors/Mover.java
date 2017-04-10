package com.kolesa.alex.concurrent.buffer.interactors;

import com.kolesa.alex.concurrent.buffer.Buffer;

public class Mover implements Runnable {

    private final Buffer buffer1;
    private final Buffer buffer2;

    public Mover(Buffer buffer1, Buffer buffer2) {
        this.buffer1 = buffer1;
        this.buffer2 = buffer2;
    }

    @Override
    public void run() {
        while(!Thread.interrupted()){
            try {
                buffer2.put(getMessagePrefix() + buffer1.get());
                Thread.sleep(1);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    private String getMessagePrefix() {
        return "Thread #" + Thread.currentThread().getId() + " moved message: ";
    }
}
