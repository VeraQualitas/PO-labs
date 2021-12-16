package agh.ics.oop;

public class OptionsParser {
    public static MoveDirection[] parse(String[] arguments) throws IllegalArgumentException {
        int counter = 0;
        for (String arg : arguments) {
            switch (arg) {
                case "f", "forward", "r", "right", "b", "backward", "l", "left" -> counter++;
                default -> throw new IllegalArgumentException(arg + " is not legal move specification");
            }
        }
        MoveDirection[] directions = new MoveDirection[counter];
        int index = 0;
        for (String arg : arguments) {
            switch (arg) {
                case "f", "forward" -> directions[index] = MoveDirection.FORWARD;
                case "r", "right" -> directions[index] = MoveDirection.RIGHT;
                case "b", "backward" -> directions[index] = MoveDirection.BACKWARD;
                case "l", "left" -> directions[index] = MoveDirection.LEFT;
                default -> index--;
            }
            index++;
        }
        return directions;
    }
}
