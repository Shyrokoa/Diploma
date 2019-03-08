package geneticAlgorithm;

import chromosomes.ChromosomeSet;
import chromosomes.ChromosomeSetList;
import textReader.TextReader;

import java.util.ArrayList;

public class Evolution {

    private final static int MAX_ERROR_DECIBEL = 50;
    private ArrayList<ChromosomeSet> chromosomeSetArrayList;
    private TextReader textReader;
    private static int population;

    public Evolution(ChromosomeSetList chromosomeSetList, TextReader textReader) {
        this.chromosomeSetArrayList = chromosomeSetList.getListOfChromosomeSet();
        this.textReader = textReader;
        population = 0;
    }

    private void determinationOfTheWinners() {
        population++;
        this.chromosomeSetArrayList = sortListOfChromosomeSet();
        System.out.println("POPULATION " + (population) + " -->");
    }

    private ArrayList<ChromosomeSet> sortListOfChromosomeSet() {
        chromosomeSetArrayList.sort(ChromosomeSet.fitnessComparator);
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
        evolutionProcessCheck();
    }

    private void evolutionProcessCheck() throws Exception {
        if (chromosomeSetArrayList.get(0).getValueOfTheFitnessFunction() < MAX_ERROR_DECIBEL) {
            getEvolutionResult();
        } else {
            continueEvolution();
            evolution();
        }
    }

    private void getEvolutionResult() {
        printWinnerChromosomeSet();
        System.out.println("Fitness: " + chromosomeSetArrayList.get(0).getValueOfTheFitnessFunction());
    }

    private void continueEvolution() {
        System.out.println("EVOLUTION for " + population + " population");
        this.chromosomeSetArrayList = sortListOfChromosomeSet();
        for (int i = 0; i < 1; i++) {
            printChromosomeSet(i);
            calculateAndPrintChromosomeSetFitness(i);
        }
    }

    private void calculateAndPrintChromosomeSetFitness(int chromosomeSetIndex) {
        chromosomeSetArrayList.get(chromosomeSetIndex).calculateChromosomeSetFitness();
        System.out.println();
        System.out.println(getChromosomeSetArrayList().get(chromosomeSetIndex).getValueOfTheFitnessFunction());
    }

    private void printChromosomeSet(int chromosomeSetIndex) {
        for (int chromosomeIndex = 0; chromosomeIndex < chromosomeSetArrayList.get(chromosomeSetIndex).getPopulationOfChromosomeSets().size(); chromosomeIndex++) {

            System.out.print(chromosomeSetArrayList.get(chromosomeSetIndex).getPopulationOfChromosomeSets().get(chromosomeIndex).getFilter().getTransferFunction() + " --> ");
        }
    }

    private void printWinnerChromosomeSet() {
        for (int i = 0; i < chromosomeSetArrayList.get(0).getPopulationOfChromosomeSets().size(); i++) {
            System.out.println(chromosomeSetArrayList.get(0).getPopulationOfChromosomeSets().get(i).getFilter().getTransferFunction());
        }
    }

    private ArrayList<ChromosomeSet> getChromosomeSetArrayList() {
        return chromosomeSetArrayList;
    }
}
