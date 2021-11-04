package agh.ics.oop;
import java.lang.Math;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d( Math.max(this.x, other.x), Math.max(this.y, other.y) );
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d( Math.min(this.x, other.x), Math.min(this.y, other.y) );
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }
    @Override
    public int hashCode() {
        final int prime1 = 31;
        final int prime2 = 37;
        int result = 1;
        result = prime1 * this.x + prime2 * this.y;
//        result = (prime1 + prime2) * result;
        return result;
    }


}
