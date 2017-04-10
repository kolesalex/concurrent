package com.kolesa.alex.concurrent.buffer;

public interface Buffer {

    void put(String element) throws InterruptedException;

    String get() throws InterruptedException;
}
