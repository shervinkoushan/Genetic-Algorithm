import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TSPUtils {
    private final static Random R=new Random(10000);

    static final TSPGene[] CITIES=generateData(50);

    private TSPUtils(){
        throw new RuntimeException("Not instantiable");
    }

    private static TSPGene[] generateData(final int numDataPoints) {
        final TSPGene[] data=new TSPGene[numDataPoints];
        for(int i=0;i<numDataPoints;i++){
            data[i]=new TSPGene(10+TSPUtils.randomIndex(World.WIDTH-20),
                                40+TSPUtils.randomIndex(World.HEIGHT-55));
        }
        return data;
    }

    static int randomIndex(final int limit){
        //returns a random number between 0 and limit (not including limit)
        return R.nextInt(limit);

    }

    static <T> List<T>[] split(final List<T> list){
        final List<T> first=new ArrayList<>();
        final List<T> second=new ArrayList<>();
        final int size=list.size();
        IntStream.range(0, size).forEach(i->{
            if(i<(size+1)/2){
                first.add(list.get(i));
            }
            else{
                second.add(list.get(i));
            }
        });
        return (List<T>[]) new List[] {first,second};
    }
}
