package level3.lesson7;

public class MainApp {

    public static void main(String[] args) {

        Class<ClassForTesting> test = ClassForTesting.class;

        TesterClass.start(test);

        System.out.println("OK");

    }

}

