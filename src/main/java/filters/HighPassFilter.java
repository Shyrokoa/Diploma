package filters;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

public class HighPassFilter extends Filter {


    private final int HIGH_PASS_FILTER_KEY = 1;
    private String transferFunction;
    private double timeConstant;
    private ArrayList<Double> cutoffFrequency;

    public HighPassFilter(double timeConstant) throws Exception {
        cutoffFrequency = getCutoffFrequency();
        this.timeConstant = timeConstant;
        transferFunction = getTransferFunction();
        calculateMagnitudePlot();
    }

    public HighPassFilter() {
        cutoffFrequency = getCutoffFrequency();
    }

    @Override
    public String getTransferFunction() {
        transferFunction = timeConstant + "s/" + "(" + timeConstant + "s+" + 1 + ")";
        return transferFunction;
    }

    @Override
    protected double getAbsoluteValue(int omegasExponentOfBase10) throws Exception {
        if (omegasExponentOfBase10 < MIN_OMEGA_POWER_OF_10_)
            throw new Exception("The number is less than 10^-10, current value: " + pow(10, omegasExponentOfBase10));
        if (omegasExponentOfBase10 > MAX_OMEGA_POWER_OF_10)
            throw new Exception("The number is more than 10^10, current value: " + pow(10, omegasExponentOfBase10));
        double numerator = Math.pow(10, omegasExponentOfBase10) * timeConstant;
        double denominator = sqrt(1 + pow(pow(10, omegasExponentOfBase10), 2) * pow(timeConstant, 2));
        return numerator / denominator;
    }

    @Override
    protected double getAmplitudeRatio(int omegasExponentOfBase10) throws Exception {
        return 20 * log10(getAbsoluteValue(omegasExponentOfBase10));
    }

    @Override
    public Filter createRandomFilter() throws Exception {
        timeConstant = getCutoffFrequency().get(new Random().nextInt(QUANTITY_OF_CUTOFF_FREQUENCIES));
        return new HighPassFilter(timeConstant);
    }

    @Override
    public void mutateFilterTransferFunction() throws Exception {
        cutoffFrequency = getCutoffFrequency();
        int HPFMutationPosition = new Random().nextInt(FILTER_PARAMETERS_QUANTITY - 1);
        switch (HPFMutationPosition) {
            case 0:
                break;
            case 1:
                timeConstant = cutoffFrequency.get(new Random().nextInt(QUANTITY_OF_CUTOFF_FREQUENCIES));
                break;
        }
        transferFunction = getTransferFunction();
        calculateMagnitudePlot();
    }

    @Override
    public void recombination(Filter filter) throws Exception {
        int HPFRecombinationPosition = new Random().nextInt(3);
        switch (HPFRecombinationPosition) {
            case 0:
                break;
            case 1:
                if (filter.getFilterKey() == 1) {
                    timeConstant = filter.getHPFTimeConstant();
                } else
                    timeConstant = filter.getLPFTimeConstant();
                break;
            case 2:
                if (filter.getFilterKey() == 1) {
                    timeConstant = filter.getHPFTimeConstant();
                } else
                    timeConstant = filter.getLPFTimeConstant();
                break;
            default:
                break;
        }
        cutoffFrequency = getCutoffFrequency();
        transferFunction = getTransferFunction();
        calculateMagnitudePlot();
    }

    @Override
    public int getFilterKey() {
        return this.HIGH_PASS_FILTER_KEY;
    }

    @Override
    public double getLPFTimeConstant() {
        return 0;
    }

    @Override
    public double getHPFTimeConstant() {
        return this.timeConstant;
    }

    @Override
    public double getLPFAmplifier() {
        return 1;
    }
}
