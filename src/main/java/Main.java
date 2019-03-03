import Genes.GeneSetList;
import Input.TextReader;
import geneticAlgorithm.Evolution;

public class Main {
    private final static String PATH = "C:\\Users\\shyrokoa\\crisisTest\\src\\main\\java\\Input\\coordinate5.txt";
    private static int geneSetListQuantity = 20;
    private static int geneSetQuantity = 2;
    public static void main(String[] args) throws Exception {
        TextReader textReader = new TextReader(PATH);
        GeneSetList geneSetList = new GeneSetList(geneSetListQuantity,geneSetQuantity,textReader);
        Evolution evolution = new Evolution(geneSetList,textReader);
        /*System.out.println("INITIAL POPULATION:");
        for (int i=0;i<geneSetListQuantity;i++){
            System.out.println(i+" from ChromosomeSetList fitness -> "+evolution.getChromosomeSetArrayList().get(i).getTotalFitness());
            for (int z=0;z<geneSetQuantity;z++){
                System.out.println("ChromosomeSet "+z+": "+evolution.getChromosomeSetArrayList().get(i).getListOfChromosomeSet().get(z).getFilter().getTransferFunction());
            }
        }

        System.out.println("\nEVOLUTION: --> SORTING");
        evolution.determinationOfTheWinners();
        for (int i=0;i<geneSetListQuantity;i++){
            System.out.println(i+" --> "+evolution.getWinnersGeneSet().get(i).getTotalFitness());
            for (int z=0;z<geneSetQuantity;z++){
                System.out.println(evolution.getWinnersGeneSet().get(i).getListOfChromosomeSet().get(z).getFilter().getTransferFunction());
            }
        }

        System.out.println("\nEVOLUTION: --> LOSERS_TRANSFORMATION");
        evolution.loserGenesTransformation();
        for (int i=1;i<geneSetListQuantity;i++){
            System.out.println(i+" --> "+evolution.getWinnersGeneSet().get(i).getTotalFitness());
            for (int z=0;z<geneSetQuantity;z++){
                System.out.println(evolution.getWinnersGeneSet().get(i).getListOfChromosomeSet().get(z).getFilter().getTransferFunction());
            }
        }*/
        evolution.evolution();
    }
}
