package level3.lesson7;

import level3.lesson7.annotation.*;

public class ClassForTesting {

    public ClassForTesting() {
    }


    @test(priority = 5)
    public void test5() {
        System.out.println("Этот метод с приоритетом 5");
    }

    @test
    public void testDefault() {
        System.out.println("Этот метод с дефолтным приоритетом");
    }

    @test
    public void testDefault1() {
        System.out.println("Этот метод с дефолтным приоритетом1");
    }

    //    @beforeSuite
    public void testDefault2() {
        System.out.println("Метод никтода не вызовется, так как нет аннотации");
    }


    @test(priority = 7)
    public void test7() {
        System.out.println("Этот метод с приоритетом 7");
    }

    @afterSuite
    public void afterSuite() {
        System.out.println("Этот метод с аннотацией afterSuite");
    }

    @beforeSuite
    public void beforeSuit() {
        System.out.println("Этот метод с аннотацией beforeSuite");
    }

}
