package level2.lesson1;

public class Main {
    public static void main(String[] args) {
        Object [] participant = new Object[9];

        for (int i = 0; i < 3; i++) {
            participant[i] = new Human("level2.lesson1.Human " +i);
        }

        for (int i = 3; i < 6; i++) {
            participant[i] = new Cat("level2.lesson1.Cat " +i);
        }

        for (int i = 6; i < 9; i++) {
            participant[i] = new Robot("level2.lesson1.Robot " +i);
        }
        Object [] obstacles = new Object[4];
        for (int i = 0; i < 2; i++) {
            obstacles[i] = new Treadmill();
        }

        for (int i = 2; i < 4; i++) {
            obstacles[i] = new Wall();
        }

//         level2.lesson1.Human human = new level2.lesson1.Human("Victor");
//        System.out.println(human.toString());
//        level2.lesson1.Wall wall = new level2.lesson1.Wall();
//        level2.lesson1.Treadmill treadmill = new level2.lesson1.Treadmill();
//        human.passObstacles(wall);
//        human.passObstacles(treadmill);


        for (int i = 0; i < participant.length; i++) {
            for (int j = 0; j < obstacles.length; j++) {
                if(participant[i] instanceof Human ){
                    if (((Human) participant[i]).isPassObstacle()) {
                        ((Human) participant[i]).passObstacles(obstacles[j]);
                    }
                } else if(participant[i] instanceof Cat) {
                    if (((Cat) participant[i]).isPassObstacle()) {
                        ((Cat) participant[i]).passObstacles(obstacles[j]);
                    }

                } else if (participant[i] instanceof Robot) {
                    if (((Robot) participant[i]).isPassObstacle()) {
                        ((Robot) participant[i]).passObstacles(obstacles[j]);
                    }
                }
            }
        }

    }
}
