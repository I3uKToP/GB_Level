package level2.lesson2;

import java.util.Scanner;


public class Main {

    private static int sum = 0;

    public static void main(String[] args) {

        System.out.println("Введите размер массива:");
        Scanner scanner = new Scanner(System.in);
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        System.out.println("Введите значения массива");
        String[][] arr = new String[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                arr[i][j] = scanner.next();
            }
        }

        for (String[] strings : arr) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
        /*
        System.out.println("Задание №1: бросить исключение MyArraySizeException");
        sumOfArray(arr);
        */


        // так как в первом задании надо Кинуть исключение, во 2 задании тоже выкинуть исключение
        // а в 3 обработать, то первую и вторую задачу заккоментил
        System.out.println("В методе main() вызвать полученный метод, обработать возможные " +
                "исключения MySizeArrayException и MyArrayDataException, и вывести результат расчета");

        try {
            sumOfArray(arr);
        } catch (MyArrayDataException | MyArraySizeException e) {
            System.out.println(e);
        } finally {
            System.out.println("Сумма массива равна " + sum);
        }
        System.out.println("OK");
    }

    private static void sumOfArray(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        if (arr.length >= 4) {
            throw new MyArraySizeException("Введен массив размерность больше 4х");
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (Exception e) {
                    throw new MyArrayDataException("Ошибка данных в ячейке [" + i + "][" + j + "] данные не числовые," +
                            "а именно " + arr[i][j]);
                }
            }
        }
    }
}
