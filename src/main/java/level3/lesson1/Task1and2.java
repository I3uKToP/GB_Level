package level3.lesson1;

import java.util.ArrayList;

public class Task1and2 {
    public static void main(String[] args) {

        //Задание №1
        Integer[] arr1 = new Integer[3];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = i;
        }
        for (Integer integer : arr1) {
            System.out.print(integer + "\t");

        }
        System.out.println();
        System.out.println("После метода");
        changePlace(arr1, 0, 2);

        for (Integer integer : arr1) {
            System.out.print(integer + "\t");
        }

        Double[] arr2 = new Double[4];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = i / 1.0;
        }

        System.out.println("\nПроверка double");

        for (Double aDouble : arr2) {
            System.out.print(aDouble + "\t");
        }
        System.out.println();
        System.out.println("После метода");
        changePlace(arr2, 0, 3);
        for (Double aDouble : arr2) {
            System.out.print(aDouble + "\t");
        }
        //Конец Задания №1

        //Задание №2

        ArrayList<Integer> integerArrayList;
        toArrayList(arr2);
        toArrayList(arr1);
        integerArrayList = toArrayList(arr1);
        System.out.println();
        System.out.println(integerArrayList.toString());
        ArrayList<Double> doubleArrayList;
        System.out.println();
        doubleArrayList = toArrayList(arr2);
        System.out.println(doubleArrayList.toString());

    }

    private static <T> ArrayList<T> toArrayList(T[] arr) {
        ArrayList<T> arrayList = new ArrayList<>();
        for (T t : arr) {
            arrayList.add(t);
        }
        return arrayList;
    }


    public static <T> void changePlace(T arr[], int a, int b) {
        if (a < 0 || b < 0) return;
        if (a >= arr.length || b >= arr.length) return;
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}

