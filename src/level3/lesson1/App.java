package level3.lesson1;

public class App {
    public static void main(String[] args) {

        Box<Orange> orangeBox = new Box<>();
        orangeBox.addToBox(new Orange());
        orangeBox.getWeight();
        Box<Apple> appleBox = new Box<>();
        appleBox.addToBox(new Apple(), 10);
        appleBox.getWeight();
        Box<Orange> orangeBox1 = new Box<>();
        orangeBox1.addToBox(new Orange(), new Orange(), new Orange());
        orangeBox1.getWeight();
        System.out.println(orangeBox.compare(orangeBox1));
        System.out.println("orangeBox.replaceFruit(orangeBox1); ");
        orangeBox.replaceFruit(orangeBox1);
        System.out.println(" orangeBox.getWeight(); ");
        orangeBox.getWeight();
        System.out.println("orangeBox1.getWeight();");
        orangeBox1.getWeight();

        System.out.println("finish");

        Box box = new Box();
        box.addToBox(new Apple());
        box.addToBox(new Orange());
        box.getWeight();
        orangeBox.compare(new Box());
    }
}
