package Filters;

import java.util.Random;

import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class LowPathFilter extends Filter {

    /**
    * @param amplifier - is the gain of the filter in the
     *                  range of frequencies or wavelengths that can pass through a
     *                  filter.
    * */
    private int amplifier;
    //is the parameter characterizing the response to a step input of a first-order, linear time-invariant (LTI) system.
    private int timeConstant;
    private String transferFunction;
    private int filterKey;

    public LowPathFilter(int amplifier, int timeConstant) throws Exception {
        filterKey = 0;
        this.amplifier = amplifier;
        this.timeConstant = timeConstant;
        calculateMagnitudePlot();
        transferFunction = getTransferFunction();

    }

    public int getAmplifier() {
        return amplifier;
    }

    public int getTimeConstant() {
        return timeConstant;
    }

    public LowPathFilter() {
        transferFunction = getTransferFunction();           //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    public String getTransferFunction() {
        transferFunction = amplifier + " / " + "( " + timeConstant + "s + " + 1 + " )";
        return transferFunction;
    }

    protected void printFilterTransferFunction() {
        System.out.println("LPF transfer function: " + getTransferFunction());
    }


    /**
     * Returns the absolute value of the low path filter transfer function
     * at specific omega.
     * @param omega - is a frequency.
     */
    protected double getAbsoluteValue(int omega) throws Exception {
        if (omega < -2) throw new Exception("The number is less than 10^-3, current value: " + pow(10, omega));
        if (omega > 3) throw new Exception("The number is more than 10^3,  current value: " + pow(10, omega));
        double num = amplifier;
        double den = sqrt(1 + pow(pow(10, omega), 2) * pow(timeConstant, 2));
        return num / den;
    }

    protected double getAmplitudeRatio(int omega) throws Exception {
        return 20 * log10(getAbsoluteValue(omega));
    }

    public Filter createRandomFilter() throws Exception {
        amplifier = new Random().nextInt(10) + 1;
        timeConstant = new Random().nextInt(10) + 1;
        calculateMagnitudePlot();
        filterKey = 0;
        return this;
    }

    public Filter mutateFilterTransferFunction() throws Exception {
        int mutatePosition = new Random().nextInt(2);
        int newValue = new Random().nextInt(10) + 1;
        //System.out.println("Mutate position: " + mutatePosition + "  New value: " + newValue);
        switch (mutatePosition) {
            case 0:
                amplifier = newValue;
                break;
            case 1:
                timeConstant = newValue;
                break;
            default:
                break;
        }
        calculateMagnitudePlot();
        return this;
    }

    public Filter recombination() throws Exception {
        int temp = amplifier;
        amplifier = timeConstant;
        timeConstant = temp;
        calculateMagnitudePlot();
        return this;
    }

    public int getFilterKey() {
        return filterKey;
    }

}
