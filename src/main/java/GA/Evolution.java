package GA;

import Genes.GeneSet;
import Genes.GeneSetList;
import Input.TextReader;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class Evolution {
    private static ArrayList<GeneSet> geneSetArrayList;
    private TextReader textReader;
    private static int population;

    public Evolution(GeneSetList geneSetList, TextReader textReader) {
        this.geneSetArrayList = geneSetList.getGeneSetArrayList();
        this.textReader = textReader;
        population = 0;
    }

    /**
     * The method determines the winner gene set and put it into the first place
     * at the winners gene set list.
     * The remaining elements also will be sorted.
     */
    public void determinationOfTheWinners() {
        population++;
        geneSetArrayList=sortListOfGeneSet();
        System.out.println("POPULATION " + (population) + " -->");
        for (int i=0;i<geneSetArrayList.size();i++){
            getGeneSetArrayList().set(i,geneSetArrayList.get(i));
        }
    }

    /**
     * The method sorts our incoming list of the gene's set using Fitness comparator
     */
    private ArrayList<GeneSet> sortListOfGeneSet() {
        Collections.sort(geneSetArrayList, GeneSet.fitnessComparator);
        return geneSetArrayList;
    }

    /**
     * This method transforms the list of losing chromosomes(from position 1 of the
     * sorted list of GenSet objects).After transformation, the method recalculates
     * the new values ​​of the fitness function for the modified list.
     **/
    public void loserGenesTransformation() throws Exception {                   //new algorithm
        for (int i = 1; i < geneSetArrayList.size(); i++) {
            geneSetArrayList.set(i, new GeneSet(geneSetArrayList.get(0).getGeneSetQuantity(), textReader));
            geneSetArrayList.get(i).calculateGeneSetFitness();
        }
    }

    /**
    * This recursion method looking for a math equation till the approximation
    * error will be acceptable for us.
    * */
    public void evolution() throws Exception {
        determinationOfTheWinners();
        loserGenesTransformation();

        int kukarek = 0;
        if(geneSetArrayList.get(0).getFitness()<5){
            for (int i=0;i<6;i++){
                if (geneSetArrayList.get(0).getGeneSetFitness()[i]<5){
                    ++kukarek;
                }            }
            if (kukarek==6){
                System.out.println("FINISH: ");
                for (int i=0;i<geneSetArrayList.get(0).getListOfGeneSet().size();i++){
                    System.out.println(geneSetArrayList.get(0).getListOfGeneSet().get(i).getFilter().getTransferFunction());
                }
                System.out.println("Fitness: "+geneSetArrayList.get(0).getFitness());

                final AudioFormat audioFormat = new AudioFormat(4048, 8, 1, true, true);
                SourceDataLine line = AudioSystem.getSourceDataLine(audioFormat);
                line.open(audioFormat);
                line.start();
                byte[] buffer = makeSinWave(audioFormat, 800, 1, TimeUnit.SECONDS);
                line.write(buffer, 0, buffer.length);
                line.drain();
                line.close();
            }

        }else{
            System.out.println("EVOLUTION for "+population+" population");
            for (int i=0;i<geneSetArrayList.size();i++){
                for (int j=0;j<geneSetArrayList.get(i).getListOfGeneSet().size();j++){
                    System.out.print(geneSetArrayList.get(i).getListOfGeneSet().get(j).getFilter().getTransferFunction()+" ");
                }
                System.out.println();
            }
            evolution();
        }
    }

    public static byte[] makeSinWave (AudioFormat audioFormat, double frequency, long duration, TimeUnit timeUnit)
    {
        byte[] buffer = new byte[(int) (timeUnit.toSeconds(duration) * audioFormat.getSampleRate())];
        double period = audioFormat.getSampleRate() / frequency;
        for (int i = 0; i < buffer.length; i++) {
            double angle = 2d * Math.PI * i / period;
            buffer[i] = (byte) (Math.sin(angle) * 127d);
        }
        return buffer;
    }


    public static ArrayList<GeneSet> getGeneSetArrayList() {
        return geneSetArrayList;
    }

    public static void setGeneSetArrayList(ArrayList<GeneSet> geneSetArrayList) {
        Evolution.geneSetArrayList = geneSetArrayList;
    }

    public TextReader getTextReader() {
        return textReader;
    }

    public void setTextReader(TextReader textReader) {
        this.textReader = textReader;
    }

    public static int getPopulation() {
        return population;
    }

    public static void setPopulation(int population) {
        Evolution.population = population;
    }
}
