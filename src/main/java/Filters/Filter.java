package Filters;

import static java.lang.Math.pow;

public abstract class Filter {

    private double[] magnitudeCoordinate;
    private double[] omegaCoordinate;

    public abstract String getTransferFunction();

    protected abstract void printFilterTransferFunction();

    protected abstract double getAbsoluteValue(int omega) throws Exception;

    protected abstract double getAmplitudeRatio(int omega) throws Exception;

    protected abstract Filter createRandomFilter() throws Exception;

    public abstract Filter mutateFilterTransferFunction() throws Exception;

    public abstract Filter recombination() throws Exception;

    public abstract int getFilterKey();

    protected abstract int getAmplifier();

    protected abstract int getTimeConstant();


    public double[] getMagnitudeCoordinate() {
        return this.magnitudeCoordinate;
    }

    public double[] getOmegaCoordinate() {
        return this.omegaCoordinate;
    }

    public void calculateMagnitudePlot() throws Exception {
        double[] magnitudeCoordinate = new double[6];
        double[] omegaCoordinate = new double[6];
        for (int i = -2; i < 4; i++) {
            try {
                getAmplitudeRatio(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            magnitudeCoordinate[i + 2] = getAmplitudeRatio(i);
            omegaCoordinate[i + 2] = pow(10, i);
        }
        this.magnitudeCoordinate = magnitudeCoordinate;
        this.omegaCoordinate = omegaCoordinate;
    }
}
