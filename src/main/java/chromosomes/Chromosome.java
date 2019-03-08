package chromosomes;

import filters.*;

import java.util.Random;

public class Chromosome {

    private double[] magnitude;
    private Filter filter;
    
    Chromosome() {
    }

    void generateRandomChromosome() throws Exception {
        int filterTypeKey = new Random().nextInt(4);
        switch (filterTypeKey) {
            case 0:
                filter = new LowPassFilter().createRandomFilter();
                break;
            case 1:
                filter = new HighPassFilter().createRandomFilter();
                break;
            case 2:
                filter = new BandPassFilter().createRandomFilter();
                break;
            case 3:
                filter = new BandStopFilter().createRandomFilter();
                break;
            default:
                break;
        }
        magnitude = filter.getMagnitudeCoordinate();
    }

    void chromosomeRecombination(Filter filterPattern) throws Exception {
        int typeOfChromosomeRecombination = new Random().nextInt(3);

        switch (typeOfChromosomeRecombination) {
            case 0:
                this.filter.transferFunctionRecombination(filterPattern);
                break;
            case 1:
                this.filter.mutateFilterTransferFunction();
                break;
            case 2:
                this.filter.transferFunctionRecombination(filterPattern);
                this.filter.mutateFilterTransferFunction();
                break;
            default:
                break;
        }
        magnitude = filter.getMagnitudeCoordinate();
    }

    double[] getMagnitude() {
        return magnitude;
    }

    public Filter getFilter() {
        return filter;
    }
}
