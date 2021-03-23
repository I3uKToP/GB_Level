package level2.lesson3;

import java.util.*;

public class Main {
    public static int SIZE=20;

    public static void main(String[] args) {

        String[] list = new String[SIZE];
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            list[i] = String.valueOf(random.nextInt(15));
        }
        System.out.println("Исходный массив, размерность: " + list.length);
        for (String s : list) {
            System.out.print(s +"\t");
        }
        System.out.println("\nУникальный массив без повторений");
        for (int i = 0; i < SIZE; i++) {
            int count = 0;
            for (int j = 0; j < SIZE; j++) {
                if (list[i].equals(list[j])) {
                    count++;
                    if (count > 1) {
                        for (int k = j; k < SIZE - 1 ; k++) {
                            list[k] = list[k+1];
                        }
                        SIZE--;
                    }
                }
            }
            System.out.println(list[i] +" количество: " + count + "\t");
            count = 0;

        }


        System.out.println("\nЗадание №2");
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Иван", "Иванов", "+7-777-777-77");
        phoneBook.add("Петр", "Петров", "+7-888-777-77");
        phoneBook.add("Дмитрий", "Песков", "+7-111-777-77");
        phoneBook.add("Владимир", "Путин", "+7-222-777-77");
        phoneBook.add("Сергей", "Шайгу", "+7-333-777-77");
        phoneBook.add("Дмитрий", "Медведев", "+7-444-777-77");
        phoneBook.add("Федор", "Иванов", "+7-555-777-77");
        phoneBook.add("Евгения" , "Панова" , "+998-90-3454710");


        phoneBook.get("Иванов");

    }
}
