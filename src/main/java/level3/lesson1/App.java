package level3.lesson1;

public class App {
    public static void main(String[] args) {
        Box box = new Box();
        box.addToBox(new Apple());
        box.addToBox(new Apple(), 10);
        System.out.println(box.getCount());
        System.out.println(box.getType());

        box.getWeight();
        Box box1 = new Box();
        box1.addToBox(new Orange(), 20);
        box1.getWeight();
        box.compare(box1);

        System.out.println("box 2");
        Box box2 = new Box();
        box2.addToBox(new Apple(), 40);
        box2.getWeight();

        System.out.println("Replace ");
        box2.replaceFruit(box);
        box2.getWeight();
        box.getWeight();

        System.out.println("box с нулл");
        Box boxNull = new Box();
        boxNull.getWeight();

        System.out.println("Перемещение box2 в boxNull");
        boxNull.replaceFruit(box2);
        boxNull.getWeight();
        box2.getWeight();

        System.out.println("OK");
    }
}
