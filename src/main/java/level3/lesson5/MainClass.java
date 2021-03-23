package level3.lesson5;

import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

public class MainClass {
    public static final int CARS_COUNT = 4;
    private static volatile boolean hasWinner = false;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Tunnel());
//        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }

        CountDownLatch cdl = new CountDownLatch(CARS_COUNT);
        for (int i = 0; i < cars.length; i++) {
            final int c = i;
            new Thread(() -> {
                cars[c].prepaid();
                cdl.countDown();
            }).start();
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");


        CountDownLatch cdl1 = new CountDownLatch(CARS_COUNT);

        for (int i = 0; i < cars.length; i++) {
            final int index = i;
            new Thread(()->{
//                Lock lock = new ReentrantLock();
                for (int j = 0; j < race.getStages().size(); j++) {
                    cars[index].run(race.getStages().get(j));

                }
                if (Thread.currentThread().isAlive() && !checkHasWinner()) {
                    System.out.println("Победил " + cars[index].getName());
                    hasWinner = true;
//                    lock.lock();   //не блокируется или я не правильно понимаю как заблокировать

//                    lock.unlock();
                }
                cdl1.countDown();
            }).start();

        }

        cdl1.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }

    private static synchronized boolean checkHasWinner() {
        if (hasWinner == false) {
            return false;
        }
        return true;
    }
}