import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class World extends JPanel {
    private final AtomicInteger generation;
    private final TSPPopulation population;

    static final int WIDTH=800;
    static final int HEIGHT=600;

    private World(){
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBackground(Color.BLACK);
        this.generation=new AtomicInteger(0);
        this.population=new TSPPopulation(TSPUtils.CITIES,100);

        final Timer timer=new Timer(1, (ActionEvent e) ->{
            this.population.update();
            repaint();
        });
        timer.restart();
    }

    @Override
    public void paintComponent(final Graphics graphics){
        super.paintComponent(graphics);
        final Graphics2D g=(Graphics2D) graphics;
        g.setColor(Color.CYAN);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("American Typewriter",Font.PLAIN, 17));
        g.drawString("Population size: "+population.getPopulation().size(),150,25);
        g.drawString("Generation: "+this.generation.incrementAndGet(),350,25);
        g.drawString("Shortest path: "+String.format("%.2f",this.population.getAlpha().calculateDistance()),500,25);
        g.setColor(Color.RED);
        for(final TSPGene gene: TSPUtils.CITIES){
            g.fillOval(gene.getX(),gene.getY(),5,5);
        }
        drawBestChromosome(g);
    }

    private void drawBestChromosome(final Graphics2D g) {
        final List<TSPGene> chromosome=this.population.getAlpha().getChromosome();
        g.setColor(Color.WHITE);
        for(int i=0;i<chromosome.size()-1;i++){
            TSPGene gene=chromosome.get(i);
            TSPGene neighbour=chromosome.get(i+1);
            g.drawLine(gene.getX(),gene.getY(),neighbour.getX(),neighbour.getY());
        }
        TSPGene start=chromosome.get(0);
        TSPGene end=chromosome.get(chromosome.size()-1);
        g.drawLine(end.getX(),end.getY(),start.getX(),start.getY());
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(()->{
            final JFrame frame=new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setTitle("Genetic Algorithm");
            frame.setResizable(false);
            frame.add(new World(),BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
