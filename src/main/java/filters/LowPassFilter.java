package filters;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

public class LowPassFilter extends Filter {

    private final int LOW_PASS_FILTER_KEY = 0;
    private double amplifier;
    private String transferFunction;
    private double timeConstant;
    private ArrayList<Double> cutoffFrequency;

    public LowPassFilter(double amplifier, double timeConstant) throws Exception {
        cutoffFrequency = getCutoffFrequency();
        this.amplifier = amplifier;
        this.timeConstant = timeConstant;
        transferFunction = getTransferFunction();
        calculateMagnitudePlot();
    }

    public LowPassFilter() {
        cutoffFrequency = getCutoffFrequency();
    }

    @Override
    public String getTransferFunction() {
        return amplifier + "/" + "(" + timeConstant + "s+" + 1 + ")";
    }


    @Override
    protected double getAbsoluteValue(int omega) throws Exception {
        if (omega < -10) throw new Exception("The number is less than 10^-10, current value: " + pow(10, omega));
        if (omega > 10) throw new Exception("The number is more than 10^10,  current value: " + pow(10, omega));
        double numerator = amplifier;
        double denominator = sqrt(1 + pow(pow(10, omega), 2) * pow(timeConstant, 2));
        return numerator / denominator;
    }

    @Override
    protected double getAmplitudeRatio(int omega) throws Exception {
        return 20 * log10(getAbsoluteValue(omega));
    }

    @Override
    public Filter createRandomFilter() throws Exception {
        timeConstant = getCutoffFrequency().get(new Random().nextInt(21));
        amplifier = new Random().nextInt(2) + 1;
        return new LowPassFilter(amplifier, timeConstant);
    }

    @Override
    public void mutateFilterTransferFunction() throws Exception {
        cutoffFrequency = getCutoffFrequency();
        int LPFMutationPosition = new Random().nextInt(2);
        switch (LPFMutationPosition) {
            case 0:
                amplifier = new Random().nextInt(2) + 1;
                break;
            case 1:
                timeConstant = cutoffFrequency.get(new Random().nextInt(21));
                break;
            default:
                break;
        }
        transferFunction = getTransferFunction();
        calculateMagnitudePlot();
    }

    @Override
    public void recombination(Filter filter) throws Exception {
        cutoffFrequency = getCutoffFrequency();
        int LPFRecombinationPosition = new Random().nextInt(2);
        switch (LPFRecombinationPosition) {
            case 0:
                if (filter.getFilterKey() == 1) {
                    amplifier = new Random().nextInt(1) + 1;
                } else {
                    amplifier = filter.getLPFAmplifier();
                }
                break;
            case 1:
                if (filter.getFilterKey() == 0) {
                    timeConstant = filter.getLPFTimeConstant();
                } else if (filter.getFilterKey() == 1) {
                    timeConstant = filter.getHPFTimeConstant();
                } else if (filter.getFilterKey() == 2) {
                    timeConstant = filter.getLPFTimeConstant();
                } else if (filter.getFilterKey() == 3) {
                    timeConstant = filter.getLPFTimeConstant();
                }
                break;
            default:
                break;
        }
        transferFunction = getTransferFunction();
        calculateMagnitudePlot();
    }

    @Override
    public int getFilterKey() {
        return this.LOW_PASS_FILTER_KEY;
    }

    @Override
    public double getLPFTimeConstant() {
        return this.timeConstant;
    }

    @Override
    public double getHPFTimeConstant() {
        return 0;
    }

    @Override
    public double getLPFAmplifier() {
        return this.amplifier;
    }
}
