import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TSPChromosome {
    private final List<TSPGene> chromosome;

    TSPChromosome(final List<TSPGene> chromosome){
        this.chromosome= Collections.unmodifiableList(chromosome);
    }

    static TSPChromosome create(final TSPGene[] points){
        final List<TSPGene> genes= Arrays.asList(Arrays.copyOf(points,points.length));
        Collections.shuffle(genes);
        return new TSPChromosome(genes);
    }

    @Override
    public String toString(){
        final StringBuilder builder=new StringBuilder();
        for(final TSPGene gene:this.chromosome){
            builder.append(gene.toString()).append((" : ")+this.calculateDistance());
        }
        return builder.toString();
    }

    List<TSPGene> getChromosome(){
        return this.chromosome;
    }

    //fitness method, program will spend most time on this method
    double calculateDistance(){
        double total=0.0f;
        for(int i=0;i<this.chromosome.size()-1;i++){
            total+=this.chromosome.get(i).distance(this.chromosome.get(i+1));
        }
        total+=this.chromosome.get(this.chromosome.size()-1).distance(this.chromosome.get(0));
        return total;
    }

    TSPChromosome[] crossOver(final TSPChromosome other) {
        final List<TSPGene>[] myDNA = TSPUtils.split(this.chromosome);
        final List<TSPGene>[] otherDNA = TSPUtils.split(other.getChromosome());

        final List<TSPGene> firstCrossover = new ArrayList<>(myDNA[0]);

        for (TSPGene gene : otherDNA[0]) {
            if (!firstCrossover.contains(gene)) {
                firstCrossover.add(gene);
            }
        }

        for (TSPGene gene : otherDNA[1]) {
            if (!firstCrossover.contains(gene)) {
                firstCrossover.add(gene);
            }
        }

        final List<TSPGene> secondCrossOver = new ArrayList<>(otherDNA[1]);

        for (TSPGene gene : myDNA[0]) {
            if (!secondCrossOver.contains(gene)) {
                secondCrossOver.add(gene);
            }
        }

        for (TSPGene gene : myDNA[1]) {
            if (!secondCrossOver.contains(gene)) {
                secondCrossOver.add(gene);
            }
        }

        if(firstCrossover.size() != TSPUtils.CITIES.length ||
           secondCrossOver.size() != TSPUtils.CITIES.length){
            throw new RuntimeException("Invalid solution");
        }

        return new TSPChromosome[]{
                new TSPChromosome(firstCrossover),
                new TSPChromosome(secondCrossOver)
        };
    }

    TSPChromosome mutate(){
        final List<TSPGene> copy=new ArrayList<>(this.chromosome);
        int indexA=TSPUtils.randomIndex(copy.size());
        int indexB=TSPUtils.randomIndex(copy.size());

        while(indexA==indexB){
            indexA=TSPUtils.randomIndex(copy.size());
            indexB=TSPUtils.randomIndex(copy.size());
        }

        Collections.swap(copy,indexA,indexB);
        return new TSPChromosome(copy);
    }
}
