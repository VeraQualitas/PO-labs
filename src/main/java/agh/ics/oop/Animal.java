package agh.ics.oop;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class Animal implements IMapElement {
    private static Integer allAmount = 0;
    private final Integer which;
    private final ArrayList<IPositionChangeObserver> observers;
    private final IWorldMap map;
    private Integer days = 0;
    private Integer childrenAmount = 0;

    private final ArrayList<Integer> genotype;  // przydałaby się osobna klasa

    private int energy;
    private Vector2d position;
    private MapDirection direction = MapDirection.NORTH;

    private static Image up;    // lepiej to wydzielić do GUI
    private static Image upRight;
    private static Image right;
    private static Image downRight;
    private static Image down;
    private static Image downLeft;
    private static Image left;
    private static Image upLeft;

    public Animal(IWorldMap map, int energy, Vector2d initialPosition) {
        allAmount += 1;
        this.which = allAmount;
        this.map = map;
        this.observers = new ArrayList<>();

        this.genotype = new ArrayList<>();
        for (int counter = 0; counter < 32; counter++) {
            this.genotype.add((int) (Math.random() * 8));
        }
        Collections.sort(this.genotype);

        this.energy = energy;
        this.position = initialPosition;

        for (int counter = 0; counter < (int) (Math.random() * 8); counter++) {
            this.direction = this.direction.next();
        }
    }

    public Animal(IWorldMap map, int energy, Vector2d initialPosition, ArrayList<Integer> genotype) {
        allAmount += 1;
        this.which = allAmount;
        this.map = map;
        this.observers = new ArrayList<>();

        this.genotype = genotype;
        Collections.sort(this.genotype);

        this.energy = energy;
        this.position = initialPosition;

        for (int counter = 0; counter < (int) (Math.random() * 8); counter++) {
            this.direction = this.direction.next();
        }
    }

    public void move() {
        this.days += 1;
        int random = (int) (Math.random() * 32);
        switch (this.genotype.get(random)) {
            case 0 -> {
                Vector2d newPosition = this.position.add(this.direction.toUnitVector());
                newPosition = map.moveTo(this.position, newPosition);
                this.positionChanged(this.position, newPosition);
                this.position = newPosition;

            }
            case 4 -> { // za miesiąc będzie Pan pamiętał, czemu akurat 4?
                Vector2d newPosition = this.position.subtract(this.direction.toUnitVector());
                newPosition = map.moveTo(this.position, newPosition);
                this.positionChanged(this.position, newPosition);
                this.position = newPosition;
            }
            case 1, 2, 3, 5, 6, 7 -> {
                int counter = 0;

                while (counter < this.genotype.get(random)) {   // takich rzeczy się nie robi for'em?
                    this.direction = this.direction.next();
                    counter++;
                }
            }
            default -> {    // pusty blok trzeba komentować
            }
        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        this.observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : this.observers) {
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }

    public Integer getWhich() {
        return this.which;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void addEnergy(int energy) {
        this.positionChanged(this.position, new Vector2d(-1, -1));
        this.energy += energy;
        this.positionChanged(new Vector2d(-1, -1), this.position);
    }

    public ArrayList<Integer> getGenotype() {
        return this.genotype;
    }

    public Integer getDays() {
        return this.days;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public boolean isOrientedAt(MapDirection direction) {
        return this.direction == direction;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    @Override
    public String toString() {
        return this.which + " " + this.position + " " + this.hashCode();
    }

    public void addChildren() {
        this.childrenAmount++;
    }

    public Integer getChildrenAmount() {
        return this.childrenAmount;
    }

    @Override
    public Image getImage() throws FileNotFoundException {
        if (up == null) {
            up = new Image(new FileInputStream("src/main/resources/up.png"));
            upRight = new Image(new FileInputStream("src/main/resources/up-right.png"));
            right = new Image(new FileInputStream("src/main/resources/right.png"));
            downRight = new Image(new FileInputStream("src/main/resources/down-right.png"));
            down = new Image(new FileInputStream("src/main/resources/down.png"));
            downLeft = new Image(new FileInputStream("src/main/resources/down-left.png"));
            left = new Image(new FileInputStream("src/main/resources/left.png"));
            upLeft = new Image(new FileInputStream("src/main/resources/up-left.png"));
        }

        return (switch (this.direction) {
            case NORTH -> up;
            case NORTH_EAST -> upRight;
            case EAST -> right;
            case SOUTH_EAST -> downRight;
            case SOUTH -> down;
            case SOUTH_WEST -> downLeft;
            case WEST -> left;
            case NORTH_WEST -> upLeft;
        });
    }
}
