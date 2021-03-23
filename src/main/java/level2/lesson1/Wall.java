package level2.lesson1;

public class Wall {
    public static final double MAX_HIGHT =2;
    private double hight;

    public Wall(int hight) {
        this.hight = hight;
    }

    public Wall() {
        this.hight = Math.random()*MAX_HIGHT;
    }

    public double getHight() {
        return hight;
    }
}
