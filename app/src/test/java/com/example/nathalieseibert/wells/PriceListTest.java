package com.example.nathalieseibert.wells;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by hspru on 16.05.2018.
 */

public class PriceListTest {
    @Test
    public void testCalculateTotal(){
        PriceList price = new PriceList();
        int result = price.CalculateTotal(199, 250);
        assertEquals(449,result);
    }

}
