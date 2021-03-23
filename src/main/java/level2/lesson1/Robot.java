package level2.lesson1;

public class Robot {
    private String name;
    private int canRun;
    private double canJump;
    public static final int maxRun = 10000;
    public static final int minRun = 500;
    public static final double maxJump = 10;
    public static final double minJump = 1;
    private boolean passObstacle = true;

    public Robot() {
        canRun = (int) (Math.random()*maxRun + minRun);
        canJump = Math.random()*maxJump + minJump;
        passObstacle = true;
    }

    public Robot(String name) {
        canRun = (int) (Math.random()*maxRun + minRun);
        canJump = Math.random()*maxJump + minJump;
        this.name = name;
        passObstacle = true;
    }

    public void jump(){
        System.out.println(name + " прыгает");
    }

    public void run() {
        System.out.println(name + " бежит");
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
