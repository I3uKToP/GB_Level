package level3.lesson1;

public class Orange extends Fruit {
    private final float weight;

    public Orange() {
        this.weight = 1.5f;
    }

    @Override
    public float getWeight() {
        return this.weight;
    }

    @Override
    public String toString() {
        return "Orange";
    }
}
