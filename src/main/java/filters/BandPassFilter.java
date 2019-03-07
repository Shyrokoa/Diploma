package filters;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

public class BandPassFilter extends Filter {

    private double LPFAmplifier;
    private double LPFTimeConstant;
    private double HPFTimeConstant;
    private String BPFTransferFunction;
    private ArrayList<Double> cutoffFrequency;

    private BandPassFilter(double LPFAmplifier, double LPFTimeConstant, double HPFTimeConstant) throws Exception {
        cutoffFrequency = getCutoffFrequency();
        this.LPFAmplifier = LPFAmplifier;
        this.LPFTimeConstant = LPFTimeConstant;
        this.HPFTimeConstant = HPFTimeConstant;
        BPFTransferFunction = getTransferFunction();
        calculateMagnitudePlot();
    }

    public BandPassFilter() {
        cutoffFrequency = getCutoffFrequency();
    }

    @Override
    public String getTransferFunction() {
        String numerator = HPFTimeConstant + "*" + LPFAmplifier + "*s";
        String denominator = "((" + HPFTimeConstant + "*s+1)(" + LPFTimeConstant + "*s+1))";
        BPFTransferFunction = numerator + "/" + denominator;
        return BPFTransferFunction;
    }

    @Override
    protected double getAbsoluteValue(int omegasExponentOfBase10) throws Exception {
        if (omegasExponentOfBase10 < MIN_OMEGA_POWER_OF_10_)
            throw new Exception("The number is less than 10^-10, current value: " + pow(10, omegasExponentOfBase10));
        if (omegasExponentOfBase10 > MAX_OMEGA_POWER_OF_10)
            throw new Exception("The number is more than 10^10,  current value: " + pow(10, omegasExponentOfBase10));
        double numerator = LPFAmplifier * Math.pow(10, omegasExponentOfBase10) * HPFTimeConstant;
        double denominator = sqrt(1 + pow(pow(10, omegasExponentOfBase10), 2) * pow(LPFTimeConstant, 2)) * sqrt(1 + pow(pow(10, omegasExponentOfBase10), 2) * pow(HPFTimeConstant, 2));
        return numerator / denominator;
    }

    @Override
    protected double getAmplitudeRatio(int omegasExponentOfBase10) throws Exception {
        return 20 * log10(getAbsoluteValue(omegasExponentOfBase10));
    }

    @Override
    public Filter createRandomFilter() throws Exception {
        cutoffFrequency = getCutoffFrequency();
        this.LPFAmplifier = new Random().nextInt(2) + 1;
        LPFTimeConstant = cutoffFrequency.get(new Random().nextInt(QUANTITY_OF_CUTOFF_FREQUENCIES));
        HPFTimeConstant = cutoffFrequency.get(new Random().nextInt(QUANTITY_OF_CUTOFF_FREQUENCIES));
        return new BandPassFilter(LPFAmplifier, LPFTimeConstant, HPFTimeConstant);
    }

    @Override
    public void mutateFilterTransferFunction() throws Exception {
        cutoffFrequency = getCutoffFrequency();
        int FILTER_PARAMETERS_QUANTITY = 3;
        int BPFRecombinationPosition = new Random().nextInt(FILTER_PARAMETERS_QUANTITY);
        switch (BPFRecombinationPosition) {
            case 0:
                LPFTimeConstant = cutoffFrequency.get(new Random().nextInt(QUANTITY_OF_CUTOFF_FREQUENCIES));
                break;
            case 1:
                LPFAmplifier = new Random().nextInt(1) + 1;
                break;
            case 2:
                HPFTimeConstant = cutoffFrequency.get(new Random().nextInt(QUANTITY_OF_CUTOFF_FREQUENCIES));
                break;
            default:
                break;
        }
        BPFTransferFunction = getTransferFunction();
        calculateMagnitudePlot();
    }

    @Override
    public void transferFunctionRecombination(Filter filter) throws Exception {
        cutoffFrequency = getCutoffFrequency();
        int typeOfRecombination = new Random().nextInt(3);
        switch (typeOfRecombination) {
            case 0:
                if (filter.getFilterKey() != 0) {
                    LPFTimeConstant = filter.getHPFTimeConstant();
                } else {
                    LPFTimeConstant = filter.getLPFTimeConstant();
                }
                break;
            case 1:
                if (filter.getFilterKey() != 1) {
                    HPFTimeConstant = filter.getLPFTimeConstant();
                } else {
                    HPFTimeConstant = filter.getHPFTimeConstant();
                }
                break;
            case 2:
                if (filter.getFilterKey() != 1) {
                    LPFAmplifier = filter.getLPFAmplifier();
                }
                break;
            default:
                break;
        }
        BPFTransferFunction = getTransferFunction();
        calculateMagnitudePlot();
    }

    /**
     * The Band Pass Filter has key 2.
     */
    @Override
    public int getFilterKey() {
        return 2;
    }

    @Override
    public double getLPFAmplifier() {
        return this.LPFAmplifier;
    }

    @Override
    public double getLPFTimeConstant() {
        return this.LPFTimeConstant;
    }

    @Override
    public double getHPFTimeConstant() {
        return this.HPFTimeConstant;
    }
}
