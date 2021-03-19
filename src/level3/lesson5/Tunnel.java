package level3.lesson5;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Tunnel extends Stage {


    private static volatile int MAX_SIZE = 2;

    BlockingQueue<Car> cars;

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        cars = new ArrayBlockingQueue<>(MAX_SIZE, true);
    }

    @Override
    public void go(Car c) {
        try {
            cars.put(c);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!cars.isEmpty()) {
            try {
                Car temp = cars.take();
                try {
                    System.out.println(temp.getName() + " готовится к этапу(ждет): " + description);
                    System.out.println(temp.getName() + " начал этап: " + description);
                    Thread.sleep(length / c.getSpeed() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(c.getName() + " закончил этап: " + description);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

