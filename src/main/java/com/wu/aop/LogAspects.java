package com.wu.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class LogAspects {

    @Pointcut("execution(public int com.wu.aop.MathCalculator.*(..))")
    public void pointCut(){};


    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.println("出发开始,参数是："+ Arrays.asList(args));
    }
    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println("出发结束");
    }

    @AfterReturning(value = "pointCut()" ,returning = "result")
    public void logReturn(JoinPoint joinPoint,Object result){
        System.out.println("返回正常，返回结果："+result);
    }

    @AfterThrowing(value = "pointCut()" ,throwing = "e")
    public void logException(JoinPoint joinPoint,Exception e){
        System.out.println("返回一场,一场信息="+e.getMessage());
    }

}
