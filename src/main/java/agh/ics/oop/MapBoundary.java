package agh.ics.oop;

import java.util.*;

public class MapBoundary implements IPositionChangeObserver{
    protected final TreeSet<IMapElement> elementsX, elementsY;  // czemu nie private?
    protected final ElementComparator comparatorX, comparatorY;

    public MapBoundary(){
        this.comparatorX = new ElementComparator('x');
        this.elementsX = new TreeSet<>(comparatorX);
        this.comparatorY = new ElementComparator('y');
        this.elementsY = new TreeSet<>(comparatorY);
    }

    public void place(IMapElement element) {
        this.elementsX.add(element);
        this.elementsY.add(element);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        IMapElement oldElement = new Grass(oldPosition);    // czemu jak zwierzę zmienia pozycję, to gdzieś się pojawiają dwie trawy?
        IMapElement newElement = new Grass(newPosition);
        this.elementsX.remove(oldElement);
        this.elementsY.remove(oldElement);

        this.elementsX.add(newElement);
        this.elementsY.add(newElement);
    }

    public Vector2d[] getBoundaries() {
        Vector2d[] bounds = new Vector2d[2];
        bounds[0] = new Vector2d(this.elementsX.first().getPosition().x, this.elementsY.first().getPosition().y);
        bounds[1] = new Vector2d(this.elementsX.last().getPosition().x, this.elementsY.last().getPosition().y);
        return bounds;
    }

}