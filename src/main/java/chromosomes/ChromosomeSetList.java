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
        this.chromosomeSetSize = listOfChromosomeSet.get(0).getListOfChromosomeSet().size();
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
        int setSize = getChromosomeSetSize();
        ChromosomeSet winnerSet = listOfChromosomeSet.get(0);

        if (listSize < 3) {
            for (int i = 1; i < listSize; i++) {
                ChromosomeSet chromosomeSet = listOfChromosomeSet.get(i);
                chromosomeSet.chromosomeSetRecombination(winnerSet);
                setChromosomeSet(chromosomeSet, i);
            }
        } else if (listSize >= 3) {
            int newInjection = (int) Math.round(0.20 * listSize);
            for (int i = 1; i < listSize - newInjection; i++) {
                ChromosomeSet chromosomeSet = listOfChromosomeSet.get(i);
                chromosomeSet.chromosomeSetRecombination(winnerSet);
                setChromosomeSet(chromosomeSet, i);
            }
            for (int i = listSize - newInjection; i < listSize; i++) {
                ChromosomeSet newChromosomeSet = new ChromosomeSet(setSize, textReader);
                newChromosomeSet.chromosomeSetRecombination(winnerSet);
                setChromosomeSet(newChromosomeSet, i);
            }
        }
        return new ChromosomeSetList(listOfChromosomeSet, textReader);
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

    private int getChromosomeSetSize() {
        return chromosomeSetSize;
    }
}