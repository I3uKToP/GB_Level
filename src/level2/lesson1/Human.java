package level2.lesson1;

public class Human {
    public static final int maxRun = 1000;
    public static final double maxJump = 2;
    private String name;
    private int canRun;
    private double canJump;
    private boolean passObstacle = true;

    public Human() {
        canRun = (int) (Math.random()*maxRun);
        canJump = Math.random()*maxJump;
        passObstacle = true;
    }

    public Human(String name) {
        canRun = (int) (Math.random()*maxRun);
        canJump = Math.random()*maxJump;
        this.name = name;
        passObstacle = true;
    }

    public void jump(){
        System.out.println(name + " прыгает");
    }

    public void run() {
        System.out.println(name + " бежит");
    }

    @Override
    public String toString() {
        return "level2.lesson1.Human{" +
                "name='" + name + '\'' +
                ", canRun=" + canRun +
                ", canJump=" + canJump +
                '}';
    }

    public boolean isPassObstacle() {
        return passObstacle;
    }

    public void passObstacles(Object obj) {
        if (obj instanceof Wall) {
            if (canJump >= ((Wall) obj).getHight()) {
                System.out.println(name + " перепрыгнул стену выостой " + ((Wall)obj).getHight() + " м");
            }
            else {
                System.out.println(name + " перепрыгнул стену выостой " + ((Wall)obj).getHight() + " м");
                passObstacle = false;
            }
        } else if (obj instanceof Treadmill) {
            if (canRun >= ((Treadmill) obj).getLenght()) {
                System.out.println(name + " пробежал препятствие длиной " + ((Treadmill)obj).getLenght()+ " м");
            }
            else {
                System.out.println(name + " не пробежал препятствие длиной " + ((Treadmill)obj).getLenght()+ " м");
                passObstacle = false;
            }
        }

    }


}
