package chromosomes;

import textReader.TextReader;

import java.util.ArrayList;

public class ChromosomeSetList {

    private int listSize;
    private int chromosomeSetSize;
    private ArrayList<ChromosomeSet> listOfChromosomeSet;
    private TextReader textReader;

    public ChromosomeSetList(int listSize, int chromosomeSetSize, TextReader textReader) throws Exception {
        this.listSize = listSize;
        this.chromosomeSetSize = chromosomeSetSize;
        this.textReader = textReader;
        generateListOfGeneSet();
    }

    public ChromosomeSetList(ArrayList<ChromosomeSet> listOfChromosomeSet, TextReader textReader) {
        this.listSize = listOfChromosomeSet.size();
        this.listOfChromosomeSet = listOfChromosomeSet;
        this.chromosomeSetSize = listOfChromosomeSet.get(0).getPopulationOfChromosomeSets().size();
        this.textReader = textReader;
    }

    private void generateListOfGeneSet() throws Exception {
        ArrayList<ChromosomeSet> randomListOfChromosomeSet = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            randomListOfChromosomeSet.add(new ChromosomeSet(chromosomeSetSize, textReader));
        }
        this.listOfChromosomeSet = randomListOfChromosomeSet;
    }

    public ChromosomeSetList chromosomeSetListRecombination() throws Exception {
        int listSize = getListSize();
        ChromosomeSet winnerSet = listOfChromosomeSet.get(0);

        if (listSize < 3) {
            readAndRecombinationSmallChromosomeSet(winnerSet);
        } else {
            readAndRecombinationLargeChromosomeSet(winnerSet);
        }
        return new ChromosomeSetList(listOfChromosomeSet, textReader);
    }

    private void readAndRecombinationSmallChromosomeSet(ChromosomeSet winnerSet) throws Exception {
        for (int i = 1; i < listSize; i++) {
            ChromosomeSet chromosomeSet = listOfChromosomeSet.get(i);
            chromosomeSet.chromosomeSetRecombination(winnerSet);
            setChromosomeSet(chromosomeSet, i);
        }
    }

    private void readAndRecombinationLargeChromosomeSet(ChromosomeSet winnerSet) throws Exception {
        double INJECTION_PERCENTAGE = 0.2;
        int newInjection = (int) Math.round(INJECTION_PERCENTAGE * listSize);
        for (int i = 1; i < listSize - newInjection; i++) {
            readAndRecombinationSurvivorsChromosomeSet(i, winnerSet);
        }
        for (int i = listSize - newInjection; i < listSize; i++) {
            readAndRecombinationInjectionChromosomeSet(i, winnerSet);
        }
    }

    private void readAndRecombinationSurvivorsChromosomeSet(int i, ChromosomeSet winnerSet) throws Exception {
        ChromosomeSet chromosomeSet = listOfChromosomeSet.get(i);
        chromosomeSet.chromosomeSetRecombination(winnerSet);
        setChromosomeSet(chromosomeSet, i);
    }

    private void readAndRecombinationInjectionChromosomeSet(int i, ChromosomeSet winnerSet) throws Exception {
        ChromosomeSet newChromosomeSet = new ChromosomeSet(winnerSet.getPopulationOfChromosomeSets().size(), textReader);
        newChromosomeSet.chromosomeSetRecombination(winnerSet);
        setChromosomeSet(newChromosomeSet, i);
    }

    public ArrayList<ChromosomeSet> getListOfChromosomeSet() {
        return listOfChromosomeSet;
    }

    private void setChromosomeSet(ChromosomeSet chromosomeSet, int position) {
        this.listOfChromosomeSet.set(position, chromosomeSet);
        chromosomeSetSize = listOfChromosomeSet.size();
    }

    private int getListSize() {
        return listSize;
    }
}