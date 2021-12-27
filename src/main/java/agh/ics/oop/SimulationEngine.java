package agh.ics.oop;

import javax.net.ssl.HandshakeCompletedEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimulationEngine implements IEngine, Runnable {
    private final IWorldMap map;
    private final ArrayList<Animal> animals;
    private final ArrayList<Grass> grasses;
    private final ArrayList<IGenericObserver> observers = new ArrayList<>();

    private Integer counter = 0;
    private boolean flag = true;
    private final int moveEnergy;
    private final int plantEnergy;
    private final int startEnergy;
    private Integer days = 0;
    private ArrayList<Integer> deadAnimalsDays = new ArrayList<>();
    private final boolean isMagical;
    private Integer magicalAlert = 0;
    private boolean ifMagicHappened = false;


    public SimulationEngine(IWorldMap map, boolean isMagical, ArrayList<Vector2d> firstAnimals,
                            ArrayList<Vector2d> firstGrasses,
                            int startEnergy, int moveEnergy, int plantEnergy) {
        this.map = map;
        this.isMagical = isMagical;
        this.animals = new ArrayList<>();
        this.grasses = new ArrayList<>();

        for (Vector2d firstAnimal : firstAnimals) {
            Animal animal = new Animal(this.map, startEnergy, firstAnimal);
            this.map.place(animal);
            this.animals.add(animal);
        }

        for (Vector2d firstGrass : firstGrasses) {
            Grass grass = new Grass(firstGrass);
            this.map.place(grass);
            this.grasses.add(grass);
        }
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.startEnergy = startEnergy;
    }

    public void addObserver(IGenericObserver observer) {
        this.observers.add(observer);
    }

    private void updateObservers() {
        for (IGenericObserver observer : this.observers) {
            observer.update();
        }
    }
    public void changeFlag() {
        this.flag = !this.flag;
    }

    public ArrayList<Integer> getDominantGenotype() {
        HashMap<ArrayList<Integer>, Integer> genotypesAmount = new HashMap<>();
        for (Animal animal:this.animals) {
            if (genotypesAmount.containsKey(animal.getGenotype())) {
                genotypesAmount.put(animal.getGenotype(), genotypesAmount.get(animal.getGenotype()) + 1);
            }
            else {
                genotypesAmount.put(animal.getGenotype(), 1);
            }
        }
        ArrayList<Integer> dominantGenotype = null;
        Integer genotypeAmount = 0;
        for (ArrayList<Integer> genotype:genotypesAmount.keySet()) {
            if (genotypesAmount.get(genotype) > genotypeAmount) dominantGenotype = genotype;
        }
        return dominantGenotype;
    }
    public Integer getAnimalSize() { return animals.size(); }
    public Integer getGrassesSize() { return grasses.size(); }
    public Integer getDaysAmount() { return this.days; }
    public double getAverageEnergy() {
        Integer averageEnergy = 0;
        for (Animal animal:this.animals) {
            averageEnergy += animal.getEnergy();
        }
        return averageEnergy * 1.0 / this.animals.size();
    }
    public double getAverageAnimalLife() {
        Integer averageLife = 0;
        for (Integer days:this.deadAnimalsDays) {
            averageLife += days;
        }
        return this.deadAnimalsDays.size() != 0 ? averageLife * 1.0 / this.deadAnimalsDays.size() : 0;
    }
    public double getAverageChildrenAmount() {
        Integer averageChildrenAmount = 0;
        for (Animal animal:this.animals) {
            averageChildrenAmount += animal.getChildrenAmount();
        }
        return averageChildrenAmount * 1.0 / this.animals.size();
    }

    private ArrayList<Vector2d> moveAndRemoveAnimals() {
        Animal tmpAnimal = null;
        ArrayList<Vector2d> grassPositionsToEat = new ArrayList<>();
        while (this.counter != this.animals.size()) {
            tmpAnimal = this.animals.get(this.counter % this.animals.size());
            if (tmpAnimal.getEnergy() < this.moveEnergy) {
                this.deadAnimalsDays.add(tmpAnimal.getDays());
                this.animals.remove(tmpAnimal);
                this.map.removeAnimal(tmpAnimal);
            }
            else {
                tmpAnimal.move();
                tmpAnimal.addEnergy(-this.moveEnergy);
                this.counter++;
                if (this.map.isGrassAt(tmpAnimal.getPosition()) && !grassPositionsToEat.contains(tmpAnimal.getPosition())) {
                    grassPositionsToEat.add(tmpAnimal.getPosition());
                }
            }
        }
        this.counter = 0;
        return grassPositionsToEat;
    }
    private void feedAnimals(ArrayList<Vector2d> grassPositionsToEat) {
        ArrayList<Animal> eatingAnimals = new ArrayList<>();
        for (Vector2d position:grassPositionsToEat) {
            this.grasses.remove(this.map.getGrassAt(position));
            Grass newGrass = this.map.removeGrass(this.map.getGrassAt(position));
            if (newGrass != null) this.grasses.add(newGrass);

            TreeSet<Animal> tree = this.map.getAnimalAt(position);
            int max = tree.first().getEnergy();
            Iterator<Animal> animal = tree.iterator();
            while (animal.hasNext()) {
                Animal tmp = animal.next();
                if (tmp.getEnergy() == max) { eatingAnimals.add(tmp); }
            }
            for (Animal eatingAnimal:eatingAnimals) {
                eatingAnimal.addEnergy((int) (plantEnergy/(eatingAnimals.size())));
            }
            eatingAnimals.clear();
        }
        grassPositionsToEat.clear();
    }
    public boolean ifMagicHappened() {
        if (this.ifMagicHappened) {
            this.ifMagicHappened = false;
            return true;
        }
        return false;
    }


    public void run() {
        while(true) {
            try {
                if (flag) {
                    if (this.animals.size() > 0) {
                        ArrayList<Vector2d> grassPositionsToEat = this.moveAndRemoveAnimals();
                        this.feedAnimals(grassPositionsToEat);
                        this.animals.addAll(this.map.reproduceAnimals(this.startEnergy));
                    }
                    this.grasses.addAll(this.map.addDailyGrasses());
                    updateObservers();
                    this.days++;
                }


                if (this.animals.size() == 0 && this.grasses.size() == (this.map.getDrawBoundaries()[1].x+1)*(this.map.getDrawBoundaries()[1].y+1)) { return; }

                if (this.isMagical && this.magicalAlert < 3 && this.animals.size() == 5) {
                    for (Animal animal:this.animals) {
                        Vector2d emptyPosition = this.map.getEmptyPosition(false);
                        if (emptyPosition == null) emptyPosition = this.map.getEmptyPosition(true);
                        if (emptyPosition != null) {
                            Animal newAnimal = new Animal(this.map, this.startEnergy, emptyPosition, animal.getGenotype());
                            this.map.place(newAnimal);
                            this.animals.add(newAnimal);
                        }
                        else break;
                    }
                    this.magicalAlert += 1;
                    this.ifMagicHappened = true;
                }


                Thread.sleep(500);

            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
        }
    }
}
