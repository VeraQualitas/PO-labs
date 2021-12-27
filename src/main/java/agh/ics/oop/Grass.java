package agh.ics.oop;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Grass implements IMapElement{
    private final Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }
    @Override
    public String toString() {
        return "*";
    }
    @Override
    public Image getImage() throws FileNotFoundException {
        return new Image(new FileInputStream("src/main/resources/grass.png"));
    }
}