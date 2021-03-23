package level3.lesson1;


public class Box {

    private String type;
    private int count = 0;
    private float weight = 0;


    public Box() {
    }


    //Добавляет 1 фрукт
    public <T extends Fruit> void addToBox(T fruit) {
        if (type == null || fruit.toString().equals(type)) {
            type = fruit.toString();
            this.count++;
            if (type.equals("Apple")) {
                weight = this.count * 1.0f;
            }
            if (type.equals("Orange")) {
                weight = this.count * 1.5f;
            }
        }
    }

    //Добавляет в бокс количество count фрукта
    public <T extends Fruit> void addToBox(T fruit, int count) {
        if (type == null || fruit.toString().equals(type)) {
            type = fruit.toString();
            this.count += count;
            if (type.equals("Apple")) {
                weight = this.count * 1.0f;
            }
            if (type.equals("Orange")) {
                weight = this.count * 1.5f;
            }
        }
    }

    public void compare (Box boxCompare) {
        float different = Math.abs(this.weight - boxCompare.getWeightFloat());
        if (different < 0.0001) {
            System.out.println("Коробки равны по весу");
        } else System.out.println("Коробки не равны, разница в весе: " + different);
    }

    // сделал немного логичнее если в коробку с яблоками пересыпать яблоки
    // то вес суммируется, если нет, то как по условию одни фрукты выкидываются и занимаются теми что переданы
    public void replaceFruit (Box boxToReplace) {
        if (type ==null || type.equals(boxToReplace.getType())) {
            this.count += boxToReplace.getCount();
            this.weight += boxToReplace.getWeightFloat();
            this.type = boxToReplace.getType();
        }
        else {
            this.count = boxToReplace.getCount();
            this.type = boxToReplace.getType();
            this.weight = boxToReplace.getWeightFloat();
        }
        boxToReplace.setCount(0);
        boxToReplace.setType(null);
        boxToReplace.setWeight(0);
    }

    public void getWeight() {
        System.out.println("Вес коробки с " + getType() + " : " + weight);
    }

    private float getWeightFloat() {
        return weight;
    }


    public int getCount() {
        return count;
    }

    public String getType() {
        return type;
    }

    private void setType(String type) {
        this.type = type;
    }

    private void setCount(int count) {
        this.count = count;
    }

    private void setWeight(float weight) {
        this.weight = weight;
    }
}
