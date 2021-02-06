package level2.lesson5;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;


    public static void main(String[] args) {
        Float[] arr1 = inintArrays(size);
        Float[] arr2 = inintArrays(size);

        int a = (int) System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.print("Время выполнения в 1м потоке: " );
        System.out.println((int) (System.currentTimeMillis()) - a);

        int b = (int) System.currentTimeMillis();
        Float[] a1 = new Float[h];
        Float[] a2 = new Float[h];

        Thread t1 = new Thread(() -> {
            System.arraycopy(arr2, 0, a1, 0, h);
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.arraycopy(a1, 0, arr2, 0, h);
        });


        Thread t2 = new Thread(() -> {
            System.arraycopy(arr2, 0, a2, 0, h);
            for (int i = 0; i < a2.length; i++) {
                a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                System.arraycopy(a2, 0, arr2, h, h);
            }
        });
        t1.start();
        t2.start();
        System.out.print("Время выполнения в 2х потоках: " );
        System.out.println((int) (System.currentTimeMillis()) - b);

        System.out.println("finished");
        System.exit(0);
    }

    private static Float[] inintArrays(int size) {
        Float[] arr = new Float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.f;
        }
        return arr;
    }
}