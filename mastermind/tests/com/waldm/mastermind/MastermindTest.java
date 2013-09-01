package com.waldm.mastermind;

import junit.framework.Assert;

public class MastermindTest {
    //Mastermind.calculateResult("guess", "code");

    @org.junit.Test
    public void testCorrectResultWhenGuessIsEqualToCode() throws Exception {
        Result result = Result.calculateResult("1234", "1234");
        Assert.assertEquals(4, result.locationCorrect);
        Assert.assertEquals(0, result.numberCorrect);
    }

    @org.junit.Test
    public void testCorrectResultWhenTwoCorrectTwoIncorrect() throws Exception {
        Result result = Result.calculateResult("1324", "1234");
        Assert.assertEquals(2, result.locationCorrect);
        Assert.assertEquals(2, result.numberCorrect);
    }

    @org.junit.Test
    public void testCorrectResultWhenAllInWrongLocation() throws Exception {
        Result result = Result.calculateResult("4321", "1234");
        Assert.assertEquals(0, result.locationCorrect);
        Assert.assertEquals(4, result.numberCorrect);
    }

    @org.junit.Test
    public void testCorrectResultWhenTwoNumbersAreTheSame() throws Exception {
        Result result = Result.calculateResult("2222", "1224");
        Assert.assertEquals(2, result.locationCorrect);
        Assert.assertEquals(0, result.numberCorrect);
    }

    @org.junit.Test
    public void testCorrectResultWhenGuessIsAllSameNumber() throws Exception {
        Result result = Result.calculateResult("1111", "1234");
        Assert.assertEquals(1, result.locationCorrect);
        Assert.assertEquals(0, result.numberCorrect);

        result = Result.calculateResult("2222", "1234");
        Assert.assertEquals(1, result.locationCorrect);
        Assert.assertEquals(0, result.numberCorrect);
    }

    @org.junit.Test
    public void testRealWord1() throws Exception {
        Result result = Result.calculateResult("1122", "5413");
        Assert.assertEquals(0, result.locationCorrect);
        Assert.assertEquals(1, result.numberCorrect);
    }

    @org.junit.Test
    public void testRealWord2() throws Exception {
        Result result = Result.calculateResult("3344", "5413");
        Assert.assertEquals(0, result.locationCorrect);
        Assert.assertEquals(2, result.numberCorrect);
    }

    @org.junit.Test
    public void testRealWord3() throws Exception {
        Result result = Result.calculateResult("5566", "5413");
        Assert.assertEquals(1, result.locationCorrect);
        Assert.assertEquals(0, result.numberCorrect);
    }

    @org.junit.Test
    public void testRealWord4() throws Exception {
        Result result = Result.calculateResult("7788", "5413");
        Assert.assertEquals(0, result.locationCorrect);
        Assert.assertEquals(0, result.numberCorrect);
    }

    @org.junit.Test
    public void testRealWord5() throws Exception {
        Result result = Result.calculateResult("5555", "5413");
        Assert.assertEquals(1, result.locationCorrect);
        Assert.assertEquals(0, result.numberCorrect);
    }

    @org.junit.Test
    public void testRealWord6() throws Exception {
        Result result = Result.calculateResult("1235", "5413");
        Assert.assertEquals(0, result.locationCorrect);
        Assert.assertEquals(3, result.numberCorrect);
    }

    @org.junit.Test
    public void testRealWord7() throws Exception {
        Result result = Result.calculateResult("1135", "5413");
        Assert.assertEquals(0, result.locationCorrect);
        Assert.assertEquals(3, result.numberCorrect);
    }

    @org.junit.Test
    public void testRealWord8() throws Exception {
        Result result = Result.calculateResult("5311", "5413");
        Assert.assertEquals(2, result.locationCorrect);
        Assert.assertEquals(1, result.numberCorrect);
    }

    @org.junit.Test
    public void testRealWord9() throws Exception {
        Result result = Result.calculateResult("3155", "5413");
        Assert.assertEquals(0, result.locationCorrect);
        Assert.assertEquals(3, result.numberCorrect);
    }
}
