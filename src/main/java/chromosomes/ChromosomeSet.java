package chromosomes;

import textReader.TextReader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ChromosomeSet {

    public static Comparator<ChromosomeSet> fitnessComparator = Comparator.comparingDouble(ChromosomeSet::getFitness);
    private int chromosomeSetSize;
    private ArrayList<Chromosome> listOfChromosomeSet;
    private double[] chromosomeSetMagnitudeArray;
    private TextReader textReader;
    private double fitness;

    ChromosomeSet(int chromosomeSetSize, TextReader textReader) throws Exception {
        fitness = 0;
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
        this.listOfChromosomeSet = chromosomeSet;
        getChromosomeSetMagnitudeArray();
        calculateChromosomeSetFitness();
    }

    private void getChromosomeSetMagnitudeArray() {
        double[] magnitudeArrayOfGeneSet = new double[21];
        for (int j = 0; j < 21; j++) {
            for (int i = 0; i < chromosomeSetSize; i++) {
                magnitudeArrayOfGeneSet[j] += listOfChromosomeSet.get(i).getMagnitude()[j];
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
        this.fitness = fitness;
    }

    void chromosomeSetRecombination(ChromosomeSet chromosomeSet) throws Exception {
        int probability = new Random().nextInt(2);
        switch (probability) {
            case 0:
                for (int i = 0; i < getChromosomeSetSize(); i++) {
                    Chromosome chromosome = listOfChromosomeSet.get(i);
                    chromosome.chromosomeRecombination(chromosomeSet.listOfChromosomeSet.get(i).getFilter());
                    setChromosome(chromosome, i);
                }
                break;
            case 1:
                for (int i = getChromosomeSetSize() - 1; i > 0; i--) {
                    Chromosome chromosome = listOfChromosomeSet.get(i);
                    chromosome.chromosomeRecombination(chromosomeSet.listOfChromosomeSet.get(i).getFilter());
                    setChromosome(chromosome, i);
                }
                break;
            default:
                break;
        }
        fitness = 0;
        chromosomeSetSize = listOfChromosomeSet.size();
        getChromosomeSetMagnitudeArray();
        calculateChromosomeSetFitness();
    }

    private int getChromosomeSetSize() {
        return chromosomeSetSize;
    }

    public ArrayList<Chromosome> getListOfChromosomeSet() {
        return listOfChromosomeSet;
    }

    public double getFitness() {
        return fitness;
    }

    private void setChromosome(Chromosome chromosome, int position) {
        this.listOfChromosomeSet.set(position, chromosome);
        getChromosomeSetMagnitudeArray();
        calculateChromosomeSetFitness();
    }
}
