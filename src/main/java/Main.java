import chromosomes.ChromosomeSetList;
import geneticAlgorithm.Evolution;
import textReader.TextReader;

public class Main {
    private static String PATH = "C:\\Users\\Andrii\\Diploma\\src\\main\\java\\coordinates\\lpf.txt";

    public static void main(String[] args) throws Exception {
        TextReader textReader = new TextReader(PATH);
        System.out.println(textReader.getExtremaQuantity());

        ChromosomeSetList chromosomeSetList = new ChromosomeSetList(5, 1, textReader);
        Evolution evolution = new Evolution(chromosomeSetList, textReader);
        evolution.evolution();
    }
}
