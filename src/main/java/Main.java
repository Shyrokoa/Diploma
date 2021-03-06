import chromosomes.ChromosomeSetList;
import geneticAlgorithm.Evolution;
import textReader.TextReader;

import java.util.Scanner;

public class Main {
    private static final String PATH = "C:\\Users\\Andrii\\Diploma\\src\\main\\java\\coordinates\\lpf.txt";

    public static void main(String[] args) throws Exception {
        TextReader textReader = new TextReader(PATH);

        System.out.print("Input size of the population: ");
        Scanner in = new Scanner(System.in);
        int sizeOfPopulation = in.nextInt();

        ChromosomeSetList chromosomeSetList = new ChromosomeSetList(sizeOfPopulation, 2, textReader);
        Evolution evolution = new Evolution(chromosomeSetList, textReader);
        evolution.evolution();
    }
}
