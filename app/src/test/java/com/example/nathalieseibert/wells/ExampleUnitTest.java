package com.example.nathalieseibert.wells;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
   /* @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);*/


    @Test
    public void addition_isCorrect() {
        int valueA;
        int valueB;
        int valueC;
        valueA = 2;
        valueB = 2;
        valueC = 5;
        assertEquals("failure - A <> B + C", valueA, valueB + valueC);
    }
}
