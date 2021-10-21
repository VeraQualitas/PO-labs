package agh.ics.oop;

import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
//        out.println("system wystartował");
////        String[] arguments = {"x", "y"};
//        World.run(args);
//        out.println("system zakończył działanie");
        out.println("Start");
        Direction[] directions = getDirections(args);
        World.run(directions);
        out.println("Stop");
    }

    private static Direction[] getDirections(String[] args) {
        int counter = 0;
        for(String arg: args){
//            out.println(arg);
            switch (arg) {
                case "f", "r", "b", "l" -> counter++;
                default -> {}
            }
        }
        Direction[] directions = new Direction[counter];
        int index = 0;
        for(String arg: args){
            switch (arg) {
                case "f" -> directions[index] = Direction.FORWARD;
                case "r" -> directions[index] = Direction.RIGHT;
                case "b" -> directions[index] = Direction.BACKWARD;
                case "l" -> directions[index] = Direction.LEFT;
                default -> index--;
            }
            index++;
        }
        return directions;
    }

    public static void run(Direction[] arguments) {
//        out.println("Zwierzak idzie do przodu");
//        for (String argument:arguments) {
//            if(argument.equals(arguments[0])){out.print(argument);}
//            else {out.print(", " + argument);}
//        }
//        out.println();
//    }
        for (Direction argument:arguments) {
            switch (argument) {
                case FORWARD -> out.println("Zwierzak idzie do przodu");
                case RIGHT -> out.println("Zwierzak skręca w prawo");
                case BACKWARD -> out.println("Zwierzak idzie do tyłu");
                case LEFT -> out.println("Zwierzak skręca w lewo");
                default -> {
                }
            }
        }
    }


}
