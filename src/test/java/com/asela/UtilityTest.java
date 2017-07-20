package com.asela;

import org.junit.Test;

public class UtilityTest {

    @Test
    public void test() {
       Utility.decoratedPrint("Test", "Test");
       Utility.decoratedPrint("Test11111111111111111111111111111", "Test");
       Utility.decoratedPrint("Test", "Test11111111111111111111111111111");
       Utility.decoratedPrint("Test1111111111111111111111111111", "Test");
       Utility.decoratedPrint("Test", "Test1111111111111111111111111111");
    }

}
