package level3.lesson4;

public class Main {
    private final Object obj  = new Object();
    private volatile char currentLetter = 'A';

    public static void main(String[] args) {

        Main m = new Main();
        new Thread(()-> m.writeA()).start();
        new Thread(()-> m.writeB()).start();
        new Thread(()-> m.writeC()).start();

    }

    private void writeA() {
        synchronized (obj) {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'A'){
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("A");
                currentLetter = 'B';
                obj.notifyAll();
            }
        }
    }
    private void writeB() {
        synchronized (obj) {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'B'){
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("B");
                currentLetter = 'C';
                obj.notifyAll();
            }
        }
    }
    private void writeC() {
        synchronized (obj) {
            for (int i = 0; i < 5; i++) {
                while (currentLetter != 'C'){
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("C\t");
                currentLetter = 'A';
                obj.notifyAll();
            }
        }
    }


}
