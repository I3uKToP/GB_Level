package level3.lesson7;




import level3.lesson7.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;


public class TesterClass {

    private static int countBeforeSuite = 0;
    private static int indexBeforeSuite = -1;
    private static int countAfterSuite = 0;
    private static int indexAfterSuite = -1;
    private static Object obj;
    private static ArrayList<Method> methodsTest = new ArrayList<>();


    public static void start(Class testClass) {

        Method[] methods = testClass.getDeclaredMethods();

        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getAnnotation(afterSuite.class) != null) {
                countAfterSuite++;
                indexAfterSuite = i;
            }
            if (methods[i].getAnnotation(beforeSuite.class) != null) {
                countBeforeSuite++;
                indexBeforeSuite = i;
            }
        }


//        System.out.println(countAfterSuite + "countAfterSuite");
//        System.out.println(countBeforeSuite + "countBeforeSuite");
        if (countAfterSuite > 1 | countBeforeSuite > 1) {
            throw new RuntimeException();
        }

        for (Method method : methods) {
            if (method.getAnnotation(test.class) != null) {
                methodsTest.add(method);
            }
        }

        try {
            obj = testClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        methodsTest.sort(Comparator.comparingInt(o -> o.getAnnotation(test.class).priority()));

        //вызов методов

        try {

            if (indexBeforeSuite != -1) {
                methods[indexBeforeSuite].invoke(obj);
            }

            for (Method method : methodsTest) {
                method.invoke(obj);
            }

            if (indexAfterSuite != -1) {
                methods[indexAfterSuite].invoke(obj);
            }

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
