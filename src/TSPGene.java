import java.util.Objects;

import static java.lang.Math.sqrt;
import static java.lang.StrictMath.pow;

public class TSPGene {
    private final int x;
    private final int y;

    TSPGene(final int x, final int y){
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString(){
        return "( "+this.x+", "+this.y+")";
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    double distance(final TSPGene otherGene){
        return sqrt(pow(getX()-otherGene.getX(),2) + pow(getY()-otherGene.getY(),2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TSPGene tspGene = (TSPGene) o;
        return x == tspGene.x &&
                y == tspGene.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
