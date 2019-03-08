package geneticAlgorithm;

import chromosomes.ChromosomeSet;
import chromosomes.ChromosomeSetList;
import textReader.TextReader;

import java.util.ArrayList;
import java.util.Collections;

public class Evolution {

    private final static int MAX_ERROR = 50;
    private ArrayList<ChromosomeSet> chromosomeSetArrayList;
    private TextReader textReader;
    private static int population;


    public Evolution(ChromosomeSetList chromosomeSetList, TextReader textReader) {
        this.chromosomeSetArrayList = chromosomeSetList.getListOfChromosomeSet();
        this.textReader = textReader;
        population = 0;
    }

    public void determinationOfTheWinners() {
        population++;
        this.chromosomeSetArrayList = sortListOfChromosomeSet();
        System.out.println("POPULATION " + (population) + " -->");
    }

    private ArrayList<ChromosomeSet> sortListOfChromosomeSet() {
        Collections.sort(chromosomeSetArrayList, ChromosomeSet.fitnessComparator);
        return chromosomeSetArrayList;
    }

    private void loserGenesTransformation() throws Exception {
        ChromosomeSetList chromosomeSetList = new ChromosomeSetList(chromosomeSetArrayList, textReader);
        ChromosomeSetList chromosomeSetList1 = chromosomeSetList.chromosomeSetListRecombination();
        this.chromosomeSetArrayList = chromosomeSetList1.getListOfChromosomeSet();
    }


    public void evolution() throws Exception {
        determinationOfTheWinners();
        loserGenesTransformation();

        if (chromosomeSetArrayList.get(0).getValueOfTheFitnessFunction() < MAX_ERROR) {
            for (int i = 0; i < chromosomeSetArrayList.get(0).getPopulationOfChromosomeSets().size(); i++) {
                System.out.println(chromosomeSetArrayList.get(0).getPopulationOfChromosomeSets().get(i).getFilter().getTransferFunction());
            }
            System.out.println("Fitness: " + chromosomeSetArrayList.get(0).getValueOfTheFitnessFunction());
        } else {
            System.out.println("EVOLUTION for " + population + " population");
            this.chromosomeSetArrayList = sortListOfChromosomeSet();
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < chromosomeSetArrayList.get(i).getPopulationOfChromosomeSets().size(); j++) {

                    System.out.print(chromosomeSetArrayList.get(i).getPopulationOfChromosomeSets().get(j).getFilter().getTransferFunction() + " --> ");
                }
                chromosomeSetArrayList.get(i).calculateChromosomeSetFitness();
                System.out.println();
                System.out.println(getChromosomeSetArrayList().get(i).getValueOfTheFitnessFunction());
            }
            evolution();
        }
    }

    public ArrayList<ChromosomeSet> getChromosomeSetArrayList() {
        return chromosomeSetArrayList;
    }
}
