package com.wu.aop;

public class MathCalculator {

    public int div(int i , int j ){
        int result = i / j ;
        System.out.println("返回值="+result);
        return result;
    }
}
