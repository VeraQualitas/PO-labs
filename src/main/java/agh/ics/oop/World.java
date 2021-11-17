// lab 3 podpunkt 10: Statyczna tablica 5x5 boolean mówiąca że coś tam jest?
package agh.ics.oop;

import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
        out.println("Start");

        MoveDirection[] directions = OptionsParser.parse(args);
        IWorldMap map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(20,2), new Vector2d(3,4) };

        IEngine engine = new SimulationEngine(directions, map, positions);
        System.out.println(map);
        engine.run();

        out.println("Stop");
    }
}
