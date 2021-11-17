package agh.ics.oop;

public class GrassField extends AbstractWorldMap implements IWorldMap {
    public GrassField(int n) {
        super();
        int randomMax = (int) Math.sqrt(n * 10);
        while (n > 0) {
            Vector2d randomPosition = new Vector2d((int) (Math.random() * randomMax), (int) (Math.random() * randomMax));
            if (super.place(new Grass(randomPosition))){
                n--;
            }
        }
    }

    @Override
    protected Vector2d[] getDrawBoundaries() {
        Vector2d[] boundaries = {mapElements.get(0).getPosition(), mapElements.get(0).getPosition()};
        for (IMapElement element : mapElements) {
            boundaries[0] = boundaries[0].lowerLeft(element.getPosition());
            boundaries[1] = boundaries[1].upperRight(element.getPosition());
        }
        return boundaries;
    }

    @Override
    public boolean place(Animal animal) {
        return super.place(animal);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(this.objectAt(position) instanceof Animal);
    }

    @Override
    public Object objectAt(Vector2d position) {
        IMapElement result = null;
        for (IMapElement mapElement : mapElements) {
            if (mapElement.getPosition().equals(position)) {
                if (mapElement instanceof Animal){
                    return mapElement;
                } else result = mapElement;
            }
        }
        return result;
    }
}