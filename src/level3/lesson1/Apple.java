package level3.lesson1;

public class Apple extends Fruit {

    private final float weight;

    public Apple() {
        this.weight = 1.0f;
    }

    @Override
    public float getWeight() {
        return this.weight;
    }

    @Override
    public String toString() {
        return "Apple";
    }
}
