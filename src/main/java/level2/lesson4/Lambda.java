package level2.lesson4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Lambda {
    public static void main( String[] args ) {

        System.out.println("Задание №2");
        List<String> list = Arrays.asList("a", "b", "c");
        Integer[] list4 = new Integer[]{1,2,3,8,5,6};

        Function<List, Integer> function = x -> {
            int k = -1;
            for (int i = 0; i < list.size(); i++) {

                try {
                    Integer.valueOf(list.get(i));
                    k = i;
                    return k;
                } catch (NumberFormatException e) {
                }
            }
            return k;
        };

        int a = function.apply(list);
        System.out.println("Первое число в списке находится на месте: " + a);


        System.out.println("Задание №3");
        MyInterface string = (str) -> {
            StringBuilder result = new StringBuilder();
            for (int i = str.length()-1; i >= 0 ; i--)
                result.append(str.charAt(i));
            return result.toString();
        };
        System.out.println(string.reverse("java interview"));

        System.out.println("Задание №4");

        UnaryOperator<Integer []> maximum = x -> {
            int temp =0;
            for (Integer integer : list4) {
                if(integer > temp) temp=integer;
            }
            Integer[] list_temp = new Integer[1];
            list_temp[0] = temp;
            return list_temp;
        };

        int max = maximum.apply(list4)[0];
        System.out.println("Максимальное значение в листе = " + max);

        System.out.println("Задание №5");

        Consumer<Integer []> averangeSum = integers -> {
            int sum=0;
            for (Integer integer : integers) {
                sum += integer;
            }
            System.out.println("Среднее значение элементов в массиве = " +  ((double)sum/integers.length));
        };
        averangeSum.accept(list4);

        System.out.println("Задание №6");

        List<String> list6 = new ArrayList<>();
        list6.add("Первая строка, которая никогда не выведится");
        list6.add("Вторая строка, которая никогда не выведится");
        list6.add("ааа");
        list6.add("Четвертая строка, которая никогда не выведится");
        list6.add("авв");



        List<String> listsResult;
        UnaryOperator<List> threeLetters = list1 -> {
            List<String> listsResultTemp = new ArrayList<>();
            for (String s : list6) {
                if (s.charAt(0) == 'а' && s.length() == 3) {
                    listsResultTemp.add(s);
                }
            }
            return listsResultTemp;
        };

        listsResult = threeLetters.apply(list6);


        for (String s : listsResult) {
            System.out.println(s);
        }
    }
}

