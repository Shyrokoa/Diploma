package filters;

import java.util.ArrayList;

import static java.lang.Math.pow;

/**
 * This abstract class allows to define certain filter properties
 * and writing a detailed implementation of selected methods for the
 * low-pass filter and high-pass filter.
 *
 * @autor Andrii Shyrokov
 */

public abstract class Filter {

    final int MIN_OMEGA_POWER_OF_10_ = -10;
    final int MAX_OMEGA_POWER_OF_10 = 10;
    final int QUANTITY_OF_CUTOFF_FREQUENCIES = 21;

    private double[] magnitudeCoordinate;
    public abstract String getTransferFunction();


    protected abstract double getAbsoluteValue(int omegasExponentOfBase10) throws Exception;

    protected abstract double getAmplitudeRatio(int omegasExponentOfBase10) throws Exception;

    public abstract Filter createRandomFilter() throws Exception;

    public abstract void mutateFilterTransferFunction() throws Exception;

    public abstract void transferFunctionRecombination(Filter filter) throws Exception;

    public double[] getMagnitudeCoordinate() {
        return this.magnitudeCoordinate;
    }

    void calculateMagnitudePlot() throws Exception {
        double[] magnitudeCoordinate = new double[21];
        for (int i = MIN_OMEGA_POWER_OF_10_; i < MAX_OMEGA_POWER_OF_10 + 1; i++) {
            try {
                getAmplitudeRatio(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            magnitudeCoordinate[i + 10] = getAmplitudeRatio(i);
        }
        this.magnitudeCoordinate = magnitudeCoordinate;
    }

    ArrayList<Double> getCutoffFrequency() {
        ArrayList<Double> cutoffFrequency = new ArrayList<>();
        for (int i = MIN_OMEGA_POWER_OF_10_; i < MAX_OMEGA_POWER_OF_10 + 1; i++) {
            cutoffFrequency.add(1 / pow(10, i));
        }
        return cutoffFrequency;
    }

    public abstract int getFilterKey();

    public abstract double getLPFTimeConstant();

    public abstract double getHPFTimeConstant();

    public abstract double getLPFAmplifier();
}

