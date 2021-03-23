package level2.lesson1;

public class Treadmill {
    public static final int MAX_LENGHT = 1000;
    private int lenght;

    public Treadmill(int hight) {
        this.lenght = hight;
    }

    public Treadmill() {
        this.lenght = (int) (Math.random()*MAX_LENGHT);
    }

    public int getLenght() {
        return lenght;
    }
}
