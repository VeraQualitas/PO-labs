package agh.ics.oop;

import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
        try {
            out.println("Start");

            MoveDirection[] directions = OptionsParser.parse(args);
            IWorldMap map = new GrassField(10);
            Vector2d[] firstPlaces = {new Vector2d(3, 4), new Vector2d(3, 4)};

            IEngine engine = new SimulationEngine(directions, map, firstPlaces);
            System.out.println(map);
            engine.run();

            out.println("Stop");
        } catch (IllegalArgumentException e) {
            System.out.println("Something went wrong:\n" + e.getMessage());
        }
    }
}
