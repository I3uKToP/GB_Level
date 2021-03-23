package level3.lesson66;

import level3.lesson6.Main;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    public static Main action;
    private static int[] arr;
    private static int[] arr1;
    private static int[] arr2;
    private static int[] arr3;


    @BeforeAll
    public static void init() {
        action = new Main();
        arr = new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7};
        arr1 = new int[]{2, 3, 5, 6, 7, 8, 9};
        arr2 = new int[]{4, 1, 2, 3, 5, 6, 7, 8, 9, 10};
        arr3 = new int[]{7};
    }

    @Test
    public void returnArrTest() {
        int[] test = new int[]{1, 7};
        int[] test2 = new int[]{1, 2, 3, 5, 6, 7, 8, 9, 10};
        int[] result = action.returnArr(arr);
        int[] result2 = action.returnArr(arr2);
        assertArrayEquals(test2, result2);
        assertArrayEquals(test, result);
        try {
            action.returnArr(arr1);
            action.returnArr(arr3);
            fail();
        } catch (RuntimeException ignored) {
        }
    }


    @Test
    public void chekFourOrOneTest() {
        assertTrue(action.chekFourOrOne(arr));
        assertFalse(action.chekFourOrOne(arr1));
        assertTrue(action.chekFourOrOne(arr2));
        assertFalse(action.chekFourOrOne(arr3));
    }
}

