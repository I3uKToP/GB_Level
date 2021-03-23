package level3.lesson6;

public class Main {

    public  int[] returnArr(int[] arr) {
        int indexStop = -1;
        if (arr.length == 0) {
            return null;
        }
        int index = arr.length - 1;
        for (int i = index; i >= 0; i--) {
            if (arr[i] == 4) {
                indexStop = i;
                break;
            }
        }
        if (indexStop != -1) {
            int[] arrToReturn = new int[arr.length - indexStop - 1];
            for (int i = 0; i < arrToReturn.length; i++) {
                arrToReturn[i] = arr[i + indexStop + 1];

            }
            return arrToReturn;
        } else {
            throw new RuntimeException();
        }
    }

    public boolean chekFourOrOne (int arr[]) {
        boolean hasOne = false;
        boolean hasFour = false;
        for (int i : arr) {
            if (i == 1) {
               hasOne = true;
            }
            if (i==4) {
                hasFour= true;
            }
        }
        if (hasOne && hasFour) {
            return true;
        }
        else return false;
    }
}
