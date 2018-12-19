package Genes;

import Input.TextReader;

import java.util.ArrayList;
import java.util.Comparator;

public class GeneSet{
    private int geneSetQuantity;
    private ArrayList<Gene> listOfGeneSet;
    private double[] geneSetMagnitudeArray;
    private TextReader textReader;
    private double fitness;

    public GeneSet(int geneSetQuantity, TextReader textReader) throws Exception {
        fitness=0;
        this.geneSetQuantity = geneSetQuantity;
        this.textReader = textReader;
        generateRandomGeneSet();
        getGeneSetMagnitudeArray();
        calculateGeneSetFitness();
    }

    private void generateRandomGeneSet() throws Exception {
        ArrayList<Gene> geneSet = new ArrayList<>();
        for (int i = 0; i < geneSetQuantity; i++) {
            geneSet.add(new Gene().generateRandomGene());
        }
        listOfGeneSet=geneSet;
    }

    private void getGeneSetMagnitudeArray() {
        double[] magnitudeArrayOfGeneSet = new double[6];
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < geneSetQuantity; i++) {
                magnitudeArrayOfGeneSet[j] += listOfGeneSet.get(i).getyMagnitude()[j];
            }
        }
        geneSetMagnitudeArray=magnitudeArrayOfGeneSet;
    }

    public void calculateGeneSetFitness(){
        double fitness=0;
        for (int i=0;i<textReader.getYFromFile().length;i++){
            fitness+=Math.abs(this.geneSetMagnitudeArray[i]-textReader.getYFromFile()[i]);
        }
        this.fitness=fitness;
    }

    public int getGeneSetQuantity() {
        return geneSetQuantity;
    }

    public double[] getMagnitude(){
        return geneSetMagnitudeArray;
    }

    public void setGeneSetQuantity(int geneSetQuantity) {
        this.geneSetQuantity = geneSetQuantity;
    }

    public ArrayList<Gene> getListOfGeneSet() {
        return listOfGeneSet;
    }

    public void setListOfGeneSet(ArrayList<Gene> listOfGeneSet) {
        this.listOfGeneSet = listOfGeneSet;
    }


    public void setGeneSetMagnitudeArray(double[] geneSetMagnitudeArray) {
        this.geneSetMagnitudeArray = geneSetMagnitudeArray;
    }

    public TextReader getTextReader() {
        return textReader;
    }

    public void setTextReader(TextReader textReader) {
        this.textReader = textReader;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public static Comparator<GeneSet> fitnessComparator = (o1, o2) -> {

        final int i = o1.getFitness() < o2.getFitness() ? -1 :

                (o1.getFitness() == o2.getFitness() ? 0 : 1);
        return i;
    };
}
