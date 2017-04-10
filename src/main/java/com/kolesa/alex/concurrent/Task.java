package com.kolesa.alex.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.kolesa.alex.concurrent.buffer.Buffer;
import com.kolesa.alex.concurrent.buffer.interactors.Mover;
import com.kolesa.alex.concurrent.buffer.interactors.Producer;

public class Task {

    public static final int EXTRACT_LIMIT = 100;
    public static final int PRODUCER_NUMBER = 5;
    public static final int MOVER_NUMBER = 2;

    private Buffer buffer1;
    private Buffer buffer2;

    private ExecutorService pool ;

    public Task(Buffer buffer1, Buffer buffer2) {
        this.buffer1 = buffer1;
        this.buffer2 = buffer2;
        this.pool = Executors
                .newFixedThreadPool(PRODUCER_NUMBER + MOVER_NUMBER, new DaemonThreadFactory());
    }

    public void execute(){

        populateProducers(buffer1, PRODUCER_NUMBER);

        populateMovers(buffer1, buffer2, MOVER_NUMBER);

        printResult(buffer2);

    }

    private void populateProducers(Buffer buffer, int producerNumber) {
        for(int i = 0; i < producerNumber; i++){
            pool.submit(new Producer(buffer));
        }
    }

    private void populateMovers(Buffer fromBuffer, Buffer toBuffer,  int moverNumber) {
        for(int i = 0; i < moverNumber; i++){
            pool.submit(new Mover(fromBuffer, toBuffer));
        }
    }

    private void printResult(Buffer buffer) {
        int extracted = 0;
        while (extracted < EXTRACT_LIMIT)
            try {
                String result = buffer.get();
                System.out.println( extracted + 1 + ") " + result);
                extracted++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    }

    private static class DaemonThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setDaemon(true);
            return t;
        }
    }
}
