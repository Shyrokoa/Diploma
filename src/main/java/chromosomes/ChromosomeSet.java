package chromosomes;

import textReader.TextReader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ChromosomeSet {

    public static Comparator<ChromosomeSet> fitnessComparator = Comparator.comparingDouble(ChromosomeSet::getValueOfTheFitnessFunction);
    private int chromosomeSetSize;
    private ArrayList<Chromosome> populationOfChromosomeSets;
    private double[] chromosomeSetMagnitudeArray;
    private TextReader textReader;
    private double valueOfTheFitnessFunction;

    ChromosomeSet(int chromosomeSetSize, TextReader textReader) throws Exception {
        valueOfTheFitnessFunction = 0;
        this.chromosomeSetSize = chromosomeSetSize;
        this.textReader = textReader;
        generateRandomChromosomeSet();
        getChromosomeSetMagnitudeArray();
        calculateChromosomeSetFitness();
    }

    private void generateRandomChromosomeSet() throws Exception {
        ArrayList<Chromosome> chromosomeSet = new ArrayList<>();
        for (int i = 0; i < chromosomeSetSize; i++) {
            Chromosome chromosome = new Chromosome();
            chromosome.generateRandomChromosome();
            chromosomeSet.add(chromosome);
        }
        this.populationOfChromosomeSets = chromosomeSet;
        getChromosomeSetMagnitudeArray();
        calculateChromosomeSetFitness();
    }

    private void getChromosomeSetMagnitudeArray() {
        int QUANTITY_OF_CUTOFF_FREQUENCIES = 21;
        double[] magnitudeArrayOfGeneSet = new double[QUANTITY_OF_CUTOFF_FREQUENCIES];
        for (int j = 0; j < 21; j++) {
            for (int i = 0; i < chromosomeSetSize; i++) {
                magnitudeArrayOfGeneSet[j] += populationOfChromosomeSets.get(i).getMagnitude()[j];
            }
        }
        this.chromosomeSetMagnitudeArray = magnitudeArrayOfGeneSet;
    }

    public void calculateChromosomeSetFitness() {
        double fitness = 0;
        for (int i = 0; i < textReader.getXFromFile().length; i++) {
            double currentValue = Math.abs(chromosomeSetMagnitudeArray[i] - textReader.getYFromFile()[i]);
            fitness += currentValue;
        }
        this.valueOfTheFitnessFunction = fitness;
    }

    void chromosomeSetRecombination(ChromosomeSet chromosomeSet) throws Exception {
        int chromosomeSetRecombinationType = new Random().nextInt(2);
        switch (chromosomeSetRecombinationType) {
            case 0:
                firstTypeOfChromosomeSetRecombination(chromosomeSet);
                break;
            case 1:
                secondTypeOfChromosomeSetRecombination(chromosomeSet);
                break;
            default:
                break;
        }
        valueOfTheFitnessFunction = 0;
        chromosomeSetSize = populationOfChromosomeSets.size();
        getChromosomeSetMagnitudeArray();
        calculateChromosomeSetFitness();
    }

    private void firstTypeOfChromosomeSetRecombination(ChromosomeSet chromosomeSet) throws Exception {
        for (int i = 0; i < getChromosomeSetSize(); i++) {
            Chromosome chromosome = populationOfChromosomeSets.get(i);
            chromosome.chromosomeRecombination(chromosomeSet.populationOfChromosomeSets.get(i).getFilter());
            setChromosome(chromosome, i);
        }
    }

    private void secondTypeOfChromosomeSetRecombination(ChromosomeSet chromosomeSet) throws Exception {
        for (int i = getChromosomeSetSize() - 1; i > 0; i--) {
            Chromosome chromosome = populationOfChromosomeSets.get(i);
            chromosome.chromosomeRecombination(chromosomeSet.populationOfChromosomeSets.get(i).getFilter());
            setChromosome(chromosome, i);
        }
    }

    private int getChromosomeSetSize() {
        return chromosomeSetSize;
    }

    public ArrayList<Chromosome> getPopulationOfChromosomeSets() {
        return populationOfChromosomeSets;
    }

    public double getValueOfTheFitnessFunction() {
        return valueOfTheFitnessFunction;
    }

    private void setChromosome(Chromosome chromosome, int position) {
        this.populationOfChromosomeSets.set(position, chromosome);
        getChromosomeSetMagnitudeArray();
        calculateChromosomeSetFitness();
    }
}
