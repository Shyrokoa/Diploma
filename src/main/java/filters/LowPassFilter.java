package filters;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

public class LowPassFilter extends Filter {

    private double amplifier;
    private String transferFunction;
    private double timeConstant;
    private ArrayList<Double> cutoffFrequency;

    private LowPassFilter(double amplifier, double timeConstant) throws Exception {
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
        transferFunction = amplifier + "/" + "(" + timeConstant + "s+" + 1 + ")";
        return transferFunction;
    }


    @Override
    protected double getAbsoluteValue(int omegasExponentOfBase10) throws Exception {
        if (omegasExponentOfBase10 < MIN_OMEGA_POWER_OF_10_)
            throw new Exception("The number is less than 10^-10, current value: " + pow(10, omegasExponentOfBase10));
        if (omegasExponentOfBase10 > MAX_OMEGA_POWER_OF_10)
            throw new Exception("The number is more than 10^10,  current value: " + pow(10, omegasExponentOfBase10));
        double numerator = amplifier;
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
        amplifier = new Random().nextInt(2) + 1;
        return new LowPassFilter(amplifier, timeConstant);
    }

    @Override
    public void mutateFilterTransferFunction() throws Exception {
        cutoffFrequency = getCutoffFrequency();
        int FILTER_PARAMETERS_QUANTITY = 2;
        int LPFMutationPosition = new Random().nextInt(FILTER_PARAMETERS_QUANTITY);
        switch (LPFMutationPosition) {
            case 0:
                amplifier = new Random().nextInt(2) + 1;
                break;
            case 1:
                timeConstant = cutoffFrequency.get(new Random().nextInt(QUANTITY_OF_CUTOFF_FREQUENCIES));
                break;
            default:
                break;
        }
        transferFunction = getTransferFunction();
        calculateMagnitudePlot();
    }

    @Override
    public void transferFunctionRecombination(Filter filter) throws Exception {
        cutoffFrequency = getCutoffFrequency();
        int typeOfRecombination = new Random().nextInt(2);
        switch (typeOfRecombination) {
            case 0:
                firstTypeOfLPFRecombination(filter);
                break;
            case 1:
                secondTypeOfLPFRecombination(filter);
                break;
            default:
                break;
        }
        transferFunction = getTransferFunction();
        calculateMagnitudePlot();
    }

    private void firstTypeOfLPFRecombination(Filter filter) {
        if (filter.getFilterKey() == 1) {
            amplifier = new Random().nextInt(1) + 1;
        } else {
            amplifier = filter.getLPFAmplifier();
        }
    }

    private void secondTypeOfLPFRecombination(Filter filter) {
        if (filter.getFilterKey() == 0) {
            timeConstant = filter.getLPFTimeConstant();
        } else if (filter.getFilterKey() == 1) {
            timeConstant = filter.getHPFTimeConstant();
        } else if (filter.getFilterKey() == 2) {
            timeConstant = filter.getLPFTimeConstant();
        } else if (filter.getFilterKey() == 3) {
            timeConstant = filter.getLPFTimeConstant();
        }
    }

    /**
     * The Low Pass Filter has key 0.
     */
    @Override
    public int getFilterKey() {
        return 0;
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
