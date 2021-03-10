package level3.lesson1;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Box<T extends Fruit> {
    private float boxWeight;
    private final List<T> list;

    public Box() {
        this.boxWeight = 0;
        this.list = new ArrayList<>();
    }

    //Добавляет 1 фрукт
    public void addToBox(T fruit) {
        this.boxWeight += fruit.getWeight();
        list.add(fruit);
    }

    //Добавляет в бокс количество count фрукта
    public void addToBox(List<T> fruitTList) {
        if (fruitTList.size() !=0 ){
            this.boxWeight += fruitTList.size()* fruitTList.get(0).getWeight();
            this.list.addAll(fruitTList);
        }
    }

    public void addToBox(T ... fruits) {
        this.boxWeight += fruits.length * fruits[0].getWeight();
        this.list.addAll(Arrays.asList(fruits));
    }

    public void addToBox(T fruit, int count) {
        for (int i = 0; i < count; i++) {
            list.add(fruit);
            this.boxWeight += fruit.getWeight();
        }
    }

    public boolean compare(Box<?> boxCompare) {
        return this.boxWeight == boxCompare.boxWeight;
    }

    public void replaceFruit (Box<T> boxToReplace) {
        boxToReplace.boxWeight += this.boxWeight;
        boxToReplace.list.addAll(this.list);
        this.list.clear();
    }

    public void getWeight() {
        if (!this.list.isEmpty()) {
            System.out.println("Вес коробки с " + this.list.get(0).getClass().getSimpleName() + " : " + boxWeight);
        }
        else System.out.println("Коробка пустая");
    }
}
