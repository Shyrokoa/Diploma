package Filters;

import java.util.Random;

import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class HighPathFilter extends Filter {

    private int timeConstant;
    private String transferFunction;
    private int filterKey;

    public HighPathFilter(int timeConstant) throws Exception {
        filterKey = 1;
        this.timeConstant = timeConstant;
        calculateMagnitudePlot();
        transferFunction = getTransferFunction();
        try {
            calculateMagnitudePlot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public HighPathFilter(int[] dataArray) {
        filterKey = 1;
        this.timeConstant = dataArray[0];
        transferFunction = getTransferFunction();
        try {
            calculateMagnitudePlot();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public HighPathFilter() {
        transferFunction = getTransferFunction();           //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    @Override
    public String getTransferFunction() {
        transferFunction = timeConstant + "s / " + "( " + timeConstant + "s + " + 1 + " )";
        return transferFunction;
    }

    @Override
    protected void printFilterTransferFunction() {
        System.out.println("HPF transfer function: " + getTransferFunction());
    }

    @Override
    protected double getAbsoluteValue(int omega) throws Exception {
        if (omega < -2) throw new Exception("The number is less than 10^-3, current value: " + pow(10, omega));
        if (omega > 3) throw new Exception("The number is more than 10^3, current value: " + pow(10, omega));
        double num = Math.pow(10, omega) * timeConstant;
        double den = sqrt(1 + pow(pow(10, omega), 2) * pow(timeConstant, 2));
        return num / den;
    }

    @Override
    protected double getAmplitudeRatio(int omega) throws Exception {
        return 20 * log10(getAbsoluteValue(omega));
    }

    @Override
    public Filter createRandomFilter() throws Exception {
        timeConstant = new Random().nextInt(10) + 1;
        calculateMagnitudePlot();
        filterKey = 1;
        return this;
    }

    @Override
    public Filter mutateFilterTransferFunction() throws Exception {
        timeConstant = new Random().nextInt(10) + 1;
        calculateMagnitudePlot();
        return this;
    }

    @Override
    @Deprecated
    public Filter recombination() {
        return null;
    }

    @Override
    public int getFilterKey() {
        return filterKey;
    }

    @Override
    @Deprecated
    protected int getAmplifier() {
        return 0;
    }

    @Override
    protected int getTimeConstant() {
        return timeConstant;
    }
}
