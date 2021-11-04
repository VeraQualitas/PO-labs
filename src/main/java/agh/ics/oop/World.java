// lab 3 podpunkt 10: Statyczna tablica 5x5 boolean mówiąca że coś tam jest?
package agh.ics.oop;

import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
        out.println("Start");

//        Vector2d position1 = new Vector2d(1,2);
//        System.out.println(position1);
//        Vector2d position2 = new Vector2d(-2,1);
//        System.out.println(position2);
//        System.out.println(position1.add(position2));
//
//        Direction[] directions = getDirections(args);
//        World.run(directions);

//        Animal animal = new Animal();
//        for(MoveDirection direction: OptionsParser.parse(args)) animal.move(direction);
//        System.out.println(animal);

        MoveDirection[] directions = OptionsParser.parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };

        IEngine engine = new SimulationEngine(directions, map, positions);
        System.out.println(map);
        engine.run();
        System.out.println(map);

        out.println("Stop");
    }

//    private static Direction[] getDirections(String[] args) {
//        int counter = 0;
//        for(String arg: args){
////            out.println(arg);
//            switch (arg) {
//                case "f", "r", "b", "l" -> counter++;
//                default -> {}
//            }
//        }
//        Direction[] directions = new Direction[counter];
//        int index = 0;
//        for(String arg: args){
//            switch (arg) {
//                case "f" -> directions[index] = Direction.FORWARD;
//                case "r" -> directions[index] = Direction.RIGHT;
//                case "b" -> directions[index] = Direction.BACKWARD;
//                case "l" -> directions[index] = Direction.LEFT;
//                default -> index--;
//            }
//            index++;
//        }
//        return directions;
//    }

//    public static void run(Direction[] arguments) {
//        for (Direction argument:arguments) {
//            switch (argument) {
//                case FORWARD -> out.println("Zwierzak idzie do przodu");
//                case RIGHT -> out.println("Zwierzak skręca w prawo");
//                case BACKWARD -> out.println("Zwierzak idzie do tyłu");
//                case LEFT -> out.println("Zwierzak skręca w lewo");
//                default -> {}
//            }
//        }
//    }


}
