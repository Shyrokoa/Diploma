package Genes;

import Input.TextReader;

import java.util.ArrayList;

public class GeneSetList {
    private int geneSetListQuantity;
    private int geneSetQuantity;
    private static ArrayList<GeneSet> geneSetArrayList;
    private TextReader textReader;

    public GeneSetList(int geneSetListQuantity,int geneSetQuantity,TextReader textReader) throws Exception {
        this.geneSetListQuantity=geneSetListQuantity;
        this.geneSetQuantity=geneSetQuantity;
        this.textReader=textReader;
        generateListOfGeneSet();
    }

    private void generateListOfGeneSet() throws Exception {
        ArrayList<GeneSet> listOfSet = new ArrayList<>();
        for (int i=0;i<geneSetListQuantity;i++){
            listOfSet.add(new GeneSet(geneSetQuantity,textReader));
        }
        geneSetArrayList=listOfSet;
    }

    public int getGeneSetListQuantity() {
        return geneSetListQuantity;
    }

    public void setGeneSetListQuantity(int geneSetListQuantity) {
        this.geneSetListQuantity = geneSetListQuantity;
    }

    public int getGeneSetQuantity() {
        return geneSetQuantity;
    }

    public void setGeneSetQuantity(int geneSetQuantity) {
        this.geneSetQuantity = geneSetQuantity;
    }

    public static ArrayList<GeneSet> getGeneSetArrayList() {
        return geneSetArrayList;
    }

    public static void setGeneSetArrayList(ArrayList<GeneSet> geneSetArrayList) {
        GeneSetList.geneSetArrayList = geneSetArrayList;
    }
}