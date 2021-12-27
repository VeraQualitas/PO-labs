package agh.ics.oop;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class RectangularMap extends AbstractWorldMap {
    private final Vector2d upperRightBound;
    private final Vector2d lowerLeftBound;
    private final Vector2d jungleUpperRightBound;
    private final Vector2d jungleLowerLeftBound;
    private final boolean isWrapped;

    public RectangularMap(int width, int height, double jungleRatio, boolean isWrapped) {
        this.lowerLeftBound = new Vector2d(0,0);
        this.upperRightBound = new Vector2d(width-1, height-1);
        this.isWrapped = isWrapped;
        if (jungleRatio > 0) {
            double mapFields = height * width;
            Vector2d jungleLowerLeftBound, jungleUpperRightBound;
            int x1, x2, y1, y2;

            if (width % 2 == 0) {
                x1 = width / 2 - 1;
                x2 = width / 2;
            }
            else {
                x1 = width / 2;
                x2 = width / 2;
            }
            if (height % 2 == 0) {
                y1 = height / 2 - 1;
                y2 = height / 2;
            }
            else {
                y1 = height / 2;
                y2 = height / 2;
            }
            jungleLowerLeftBound = new Vector2d(x1 , y1);
            jungleUpperRightBound = new Vector2d(x2, y2);
            Vector2d xAxis = new Vector2d(1, 0);
            Vector2d yAxis = new Vector2d(0, 1);
            Vector2d tmp1;
            Vector2d tmp2;
            boolean flag = false;

            while (true) {
                flag = false;
                if (height * 1.0 / width > (upperRightBound.y - lowerLeftBound.y + 1) * 1.0 / (upperRightBound.x - lowerLeftBound.x + 1)) {
                    tmp1 = jungleLowerLeftBound.subtract(xAxis);
                    tmp2 = jungleUpperRightBound.add(xAxis);
                    if (tmp1.follows(lowerLeftBound)
                            && tmp2.precedes(upperRightBound)
                            && (tmp2.x - tmp1.x + 1) * (tmp2.y - tmp1.y + 1) <= (jungleRatio / 100) * mapFields) {
                        jungleLowerLeftBound = jungleLowerLeftBound.subtract(xAxis);
                        jungleUpperRightBound = jungleUpperRightBound.add(xAxis);
                        flag = true;
                    }
                    tmp1 = jungleLowerLeftBound.subtract(yAxis);
                    tmp2 = jungleUpperRightBound.add(yAxis);
                    if (tmp1.follows(lowerLeftBound)
                            && tmp2.precedes(upperRightBound)
                            && (tmp2.x - tmp1.x + 1) * (tmp2.y - tmp1.y + 1) <= (jungleRatio / 100) * mapFields) {
                        jungleLowerLeftBound = jungleLowerLeftBound.subtract(yAxis);
                        jungleUpperRightBound = jungleUpperRightBound.add(yAxis);
                        flag = true;
                    }
                }
                else {
                    tmp1 = jungleLowerLeftBound.subtract(yAxis);
                    tmp2 = jungleUpperRightBound.add(yAxis);
                    if (tmp1.follows(lowerLeftBound)
                            && tmp2.precedes(upperRightBound)
                            && (tmp2.x - tmp1.x + 1) * (tmp2.y - tmp1.y + 1) <= (jungleRatio / 100) * mapFields) {
                        jungleLowerLeftBound = jungleLowerLeftBound.subtract(yAxis);
                        jungleUpperRightBound = jungleUpperRightBound.add(yAxis);
                        flag = true;
                    }
                    tmp1 = jungleLowerLeftBound.subtract(xAxis);
                    tmp2 = jungleUpperRightBound.add(xAxis);
                    if (tmp1.follows(lowerLeftBound)
                            && tmp2.precedes(upperRightBound)
                            && (tmp2.x - tmp1.x + 1) * (tmp2.y - tmp1.y + 1) <= (jungleRatio / 100) * mapFields) {
                        jungleLowerLeftBound = jungleLowerLeftBound.subtract(xAxis);
                        jungleUpperRightBound = jungleUpperRightBound.add(xAxis);
                        flag = true;
                    }
                }
                if (!flag) break;
            }
            this.jungleLowerLeftBound = jungleLowerLeftBound;
            this.jungleUpperRightBound = jungleUpperRightBound;
        }
        else {
            this.jungleLowerLeftBound = new Vector2d(-1, -1);
            this.jungleUpperRightBound = new Vector2d(-1, -1);
        }

    }
    public int getSize() { return this.animalMap.size(); }

    public Vector2d moveTo(Vector2d oldPosition, Vector2d newPosition) {
        if (!isWrapped) {
            if(newPosition.follows(lowerLeftBound) && newPosition.precedes(upperRightBound)) return newPosition;
            else return oldPosition;
        }
        else return new Vector2d((newPosition.x + upperRightBound.x + 1) % (upperRightBound.x + 1), (newPosition.y + upperRightBound.y + 1) % (upperRightBound.y + 1));
    }

    @Override
    public Vector2d[] getDrawBoundaries() {
        return new Vector2d[]{this.lowerLeftBound, this.upperRightBound};
    }
    public boolean isJungle(Vector2d position) {
        return position.follows(jungleLowerLeftBound) && position.precedes(jungleUpperRightBound);
    }
    @Override
    public void place(Animal animal) throws IllegalArgumentException {
        Vector2d position = animal.getPosition();
        if (!(position.follows(lowerLeftBound) && position.precedes(upperRightBound))) {
            throw new IllegalArgumentException("Cannot place animal at " + position);
        }
        if (this.animalMap.containsKey(position)) {
            this.animalMap.get(position).add(animal);
        }
        else {
            TreeSet<Animal> treeSetAnimal = new TreeSet<>(energyComparator);
            treeSetAnimal.add(animal);
            this.animalMap.put(position, treeSetAnimal);
        }
        animal.addObserver(this);
    }
    @Override
    public void place(Grass grass) throws IllegalArgumentException {
        Vector2d position = grass.getPosition();
        if (!(position.follows(lowerLeftBound) && position.precedes(upperRightBound))) {
            throw new IllegalArgumentException("Cannot place grass at " + position);
        }
        if (!this.animalMap.containsKey(position) && !this.grassMap.containsKey(position)) {
            this.grassMap.put(position, grass);
        }
        else throw new IllegalArgumentException("Cannot place grass at " + position);
    }
    public Vector2d getEmptyPosition(boolean inJungle) {
        int size = animalMap.size() + grassMap.size();
        if (size < (upperRightBound.x+1) * (upperRightBound.y+1)){
            ArrayList<Vector2d> notEmptyPositions = new ArrayList<>();
            while(true) {
                if (inJungle) {
                    Vector2d randomPosition = new Vector2d(
                            (int) (Math.random() * (jungleUpperRightBound.x - jungleLowerLeftBound.x + 1) + jungleLowerLeftBound.x),
                            (int) (Math.random() * (jungleUpperRightBound.y - jungleLowerLeftBound.y + 1) + jungleLowerLeftBound.y));
                    if (!animalMap.containsKey(randomPosition) && !grassMap.containsKey(randomPosition)) {
                        return randomPosition;
                    }
                    else {
                        if (!notEmptyPositions.contains(randomPosition)) {
                            notEmptyPositions.add(randomPosition);
                        }
                        if (notEmptyPositions.size() == (jungleUpperRightBound.x - jungleLowerLeftBound.x + 1) * (jungleUpperRightBound.y - jungleLowerLeftBound.y + 1)) return null;
                    }
                }
                else {
                    Vector2d randomPosition = new Vector2d(
                            (int) (Math.random() * (upperRightBound.x + 1)),
                            (int) (Math.random() * (upperRightBound.y + 1)));
                    if (!(randomPosition.follows(jungleLowerLeftBound) && randomPosition.precedes(jungleUpperRightBound))) {
                        if (!animalMap.containsKey(randomPosition) && !grassMap.containsKey(randomPosition)) {
                            return randomPosition;
                        }
                        if (!notEmptyPositions.contains(randomPosition)) {
                            notEmptyPositions.add(randomPosition);
                        }

                    }
                    if (notEmptyPositions.size() == (upperRightBound.x+1) * (upperRightBound.y+1) - (jungleUpperRightBound.x - jungleLowerLeftBound.x + 1) * (jungleUpperRightBound.y - jungleLowerLeftBound.y + 1)) return null;
                }
            }

        }
        else return null;
    }
    public ArrayList<Grass> addDailyGrasses() {
        ArrayList<Grass> addedGrasses = new ArrayList<>();

        Vector2d emptyPositionInJungle = this.getEmptyPosition(true);
        if (emptyPositionInJungle != null) {
            Grass grass = new Grass(emptyPositionInJungle);
            this.place(grass);
            addedGrasses.add(grass);
        }
        Vector2d emptyPositionNotInJungle = this.getEmptyPosition(false);
        if (emptyPositionNotInJungle != null) {
            Grass grass = new Grass(emptyPositionNotInJungle);
            this.place(grass);
            addedGrasses.add(grass);
        }
        return addedGrasses;
    }

    public void removeAnimal(Animal animal) {
        Vector2d position = animal.getPosition();
        while(animalMap.get(position).remove(animal)) {};

        if (this.animalMap.get(position).isEmpty()) {
            this.animalMap.remove(position);
        }
    }
    public Grass removeGrass(Grass grass) {
        grassMap.remove(grass.getPosition());
        Vector2d emptyPosition = this.getEmptyPosition(true);
        if (emptyPosition != null) emptyPosition = this.getEmptyPosition(false);
        if (emptyPosition != null) {
            grassMap.put(emptyPosition, new Grass(emptyPosition));
            return grassMap.get(emptyPosition);
        }
        return null;
    }

    public ArrayList<Animal> reproduceAnimals(int startEnergy) {
        ArrayList<Animal> addedAnimals = new ArrayList<>();
        Animal animalFather;
        Animal animalMother;
        Animal animalChild;
        int fatherEnergy;
        int motherEnergy;
        int childEnergy;
        int fatherGenomesAmount;
        int whichSide;
        for (Vector2d position : this.animalMap.keySet()) {
            if (this.animalMap.get(position).size() > 1) {
                Iterator<Animal> animal = this.animalMap.get(position).iterator();
                animalFather = animal.next();
                fatherEnergy = animalFather.getEnergy();
                animalMother = animal.next();
                motherEnergy = animalMother.getEnergy();

                if (motherEnergy > startEnergy/2 && fatherEnergy > startEnergy/2) {
                    ArrayList<Integer> childGenotype = new ArrayList<>();
                    childEnergy = (motherEnergy/4) + (fatherEnergy/4);
                    fatherGenomesAmount = ((32 * fatherEnergy) / (fatherEnergy+motherEnergy));
                    // if fatherEnergy is greater than motherEnergy then must add 1
                    if (fatherEnergy > motherEnergy) { fatherGenomesAmount += 1; }

                    whichSide = (int) (Math.random() * 2);
                    if (whichSide == 0) {
                        childGenotype.addAll(animalFather.getGenotype().subList(0, fatherGenomesAmount));
                        childGenotype.addAll(animalMother.getGenotype().subList(fatherGenomesAmount, 32));
                    }
                    else {
                        childGenotype.addAll(animalMother.getGenotype().subList(0, fatherGenomesAmount - 1));
                        childGenotype.addAll(animalFather.getGenotype().subList(fatherGenomesAmount - 1, 32));
                    }
                    animalChild = new Animal(this, childEnergy, animalFather.getPosition(), childGenotype);
                    this.place(animalChild);

                    animalFather.addChildren();
                    animalMother.addChildren();

                    animalMother.addEnergy(-(animalMother.getEnergy()/4));
                    animalFather.addEnergy(-(animalFather.getEnergy()/4));
                    addedAnimals.add(animalChild);
                }
            }

        }
        return addedAnimals;
    }
}