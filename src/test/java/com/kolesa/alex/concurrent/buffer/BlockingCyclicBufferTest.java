package com.kolesa.alex.concurrent.buffer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.kolesa.alex.concurrent.buffer.impl.BlockingCyclicBuffer;


public class BlockingCyclicBufferTest {

    @Test
    public void get() throws Exception {
        Buffer buffer = new BlockingCyclicBuffer(3);
        buffer.put("a");
        buffer.put("b");
        buffer.get();
        buffer.get();
        buffer.put("c");
        buffer.put("d");
        String result1 = buffer.get();
        assertThat("should extract c: ", result1, is("c"));
        String result2 = buffer.get();
        assertThat("should extract d: ", result2, is("d"));
    }

}