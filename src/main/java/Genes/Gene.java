package Genes;

import filters.Filter;
import filters.HighPassFilter;
import filters.LowPassFilter;

import java.util.Random;

/**
 * The process begins with a set of individuals which is
 * called a Population. Each individual is a solution to
 * the problem you want to solve.
 *
 * An individual is characterized by a set of parameters
 * (variables) known as Genes. Genes are joined into a
 * string to form a Chromosome (solution).
 *
 * This class defines a Chromosome and assigns a filter. All
 * the attributes in this class are private.
 *
 * <p/>
 *  * @author Andrii Shyrokov
* */
public class Gene {

    private double[] xOmega;
    private double[] yMagnitude;
    private Filter filter;
    private double totalFitness;
    private double[] fitnessByOmega = new double[6];


    /**
     * This constructor creates default gene.
     * @see Gene#Gene()(Filter)
     */
    public Gene() {
    }

    /**
     * This constructor creates gene using information
     * about specific kind of filters.
     * @param filter - abstract filter, depending on the situation
     *               can represent Low Path Filter or High Path Filter.
     * @see Gene#Gene(Filter) ()
     */
    public Gene(Filter filter) throws Exception {
        xOmega = filter.getOmegaCoordinate();
        yMagnitude = filter.getMagnitudeCoordinate();
        this.filter = filter;
        totalFitness =0;
        fitnessByOmega=null;
    }

    public double[] getxOmega() {
        return xOmega;
    }

    /**
     * @param xOmega
     */
    public void setxOmega(double[] xOmega) {
        this.xOmega = xOmega;
    }

    public double[] getyMagnitude() {
        return yMagnitude;
    }

    public void setyMagnitude(double[] yMagnitude) {
        this.yMagnitude = yMagnitude;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public double getTotalFitness() {
        return totalFitness;
    }

    public void setTotalFitness(double totalFitness) {
        this.totalFitness = totalFitness;
    }

    /**
     * This method generates random filter. Kind of generated filter depends
     * from the random generated number. In a situation where this number is
     * 0 - method generates Low Path Filter. In another case (1) - High Path
     * Filter.
     * @return gene as a particular filter
     */
    public Gene generateRandomGene() throws Exception {
        Gene gene = new Gene();
        int typeFilter = new Random().nextInt(2);
        switch (typeFilter){
            case 0:
                gene = new Gene(new LowPassFilter().createRandomFilter());
                break;
            case 1:
                gene = new Gene(new HighPassFilter().createRandomFilter());
                break;
            default:
                break;
        }
        return gene;
    }

    public double[] getFitnessByOmega() {
        return fitnessByOmega;
    }

    public void setFitnessByOmega(double[] fitnessByOmega) {
        this.fitnessByOmega = fitnessByOmega;
    }
}