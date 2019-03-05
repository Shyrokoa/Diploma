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
    final int FILTER_PARAMETERS_QUANTITY = 3;

    /**
     * Private field storing magnitudes
     */
    private double[] magnitudeCoordinate;
    /**
     * Private field storing omegas
     */
    private double[] cutoffFrequencyCoordinate;

    /**
     * @return transfer function as a string
     */
    public abstract String getTransferFunction();

    /**
     * This method returns the absolute value of the transfer function at specific
     * frequency.
     *
     * @param omega is a component of the Laplace transform variable s=j*omega.
     * @return absolute value of a transfer function in the frequency domain.
     */
    protected abstract double getAbsoluteValue(int omega) throws Exception;

    /**
     * This method use 20 log |H(jw)|to  compute  the  magnitude  in  dB.
     *
     * @param omega is a component of the Laplace transform variable s = j*omega.
     * @return magnitude in dB.
     */
    protected abstract double getAmplitudeRatio(int omega) throws Exception;

    /**
     * This method creates a random filter randomly generating an amplifier and
     * time constant.
     */
    public abstract Filter createRandomFilter() throws Exception;

    /**
     * This method mutate transfer function of the filter. Mutation is a genetic
     * operator used to maintain genetic diversity from one generation of a population
     * of genetic algorithm chromosomes to the next. It is analogous to biological mutation.
     */
    public abstract void mutateFilterTransferFunction() throws Exception;

    /**
     * This method doing recombination of the transfer function. Crossover, also
     * called recombination, is a genetic operator used to combine the genetic
     * information of two parents to generate new offspring.
     */
    public abstract void recombination(Filter filter) throws Exception;

    /**
     * @return an array of magnitudes.
     */
    public double[] getMagnitudeCoordinate() {
        return this.magnitudeCoordinate;
    }


    /**
     * This method calculates an array of magnitudes using information
     * about content of the omega's array.
     */
    void calculateMagnitudePlot() throws Exception {
        double[] magnitudeCoordinate = new double[21];
        double[] cutoffFrequencyCoordinate = new double[21];
        for (int i = MIN_OMEGA_POWER_OF_10_; i < MAX_OMEGA_POWER_OF_10 + 1; i++) {
            try {
                getAmplitudeRatio(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            magnitudeCoordinate[i + 10] = getAmplitudeRatio(i);
            cutoffFrequencyCoordinate[i + 10] = pow(10, i);
        }
        this.magnitudeCoordinate = magnitudeCoordinate;
        this.cutoffFrequencyCoordinate = cutoffFrequencyCoordinate;
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

