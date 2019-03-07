package chromosomes;

import filters.*;

import java.util.Random;

public class Chromosome {

    private double[] Magnitude;
    private Filter filter;


    Chromosome() {
    }

    void generateRandomChromosome() throws Exception {
        int typeFilter = new Random().nextInt(4);
        switch (typeFilter) {
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
        Magnitude = filter.getMagnitudeCoordinate();
    }

    void chromosomeRecombination(Filter filterPattern) throws Exception {
        int type = new Random().nextInt(3);

        switch (type) {
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
        Magnitude = filter.getMagnitudeCoordinate();
    }

    double[] getMagnitude() {
        return Magnitude;
    }

    public Filter getFilter() {
        return filter;
    }
}
