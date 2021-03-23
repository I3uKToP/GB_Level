package level3.lesson5;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {


    private static volatile int MAX_SIZE = 2;
    Semaphore smp = new Semaphore(MAX_SIZE);

    BlockingQueue<Car> cars;

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        cars = new ArrayBlockingQueue<>(MAX_SIZE, true);
    }

    @Override
    public void go(Car c) {

        try {
            smp.acquire();
            System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            smp.release();
            System.out.println(c.getName() + " закончил этап: " + description);
        }
    }

}




