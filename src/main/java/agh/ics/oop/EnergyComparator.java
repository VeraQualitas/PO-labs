package agh.ics.oop;

import java.util.Comparator;

public record EnergyComparator() implements Comparator<Animal> {

    @Override
    public int compare(Animal animal1, Animal animal2) {
        int e1 = animal1.getEnergy();
        int e2 = animal2.getEnergy();
        if (e2 - e1 != 0) { return e2 - e1; }
        else {
            return animal2.getWhich() - animal1.getWhich();
        }
    }
}