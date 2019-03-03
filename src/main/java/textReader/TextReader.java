package textReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is necessary for processing incoming data in the form of a
 * text document with coordinates. The absolute path to the text file is
 * passed to the constructor.
 *
 * @author Andrii Shyrokov
 */
public class TextReader {

    /**
     * Private field storing the number of incoming function extremum(maximum
     * and minimum of a function), are the largest and smallest value of the
     * function, either within a given range (the local or relative extrema)
     * or on the entire domain of a function (the global or absolute extrema).
     */
    private static int extremaQuantity;
    /**
     * Private field storing an unique location incoming file in a file
     * system.
     */
    private String pathToTheFile;
    /**
     * Private field storing an array of the X-coordinate of incoming file.
     */
    private double[] xPoints;
    /**
     * Private field storing an array of the Y-coordinate of incoming file.
     */
    private double[] yPoints;
    /**
     * Private field storing the chromosome quantity in the set.
     */
    private int chromosomeQuantity;
    /**
     * Private field storing maximum fall at Bode magnitude plot.
     */
    private double maxFall;
    /**
     * Private field storing maximum growth at Bode magnitude plot.
     */
    private double maxGrowth;

    /**
     * Constructor - creating a new text reader with specific values.
     *
     * @param pathToTheFile - an unique location incoming file in a file system.
     */
    public TextReader(String pathToTheFile) {
        this.pathToTheFile = pathToTheFile;
        readPoints(pathToTheFile);
        //extrema(yPoints, yPoints.length);
    }

    /**
     * The method helps us to read arrays of points
     * from the text file.
     */
    private void readPoints(String pathToTheFile) {
        this.pathToTheFile = pathToTheFile;
        List<String> listOfPoints = new ArrayList<>();
        File readFile = new File(this.pathToTheFile);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(readFile));
            String lineFromFile;

            while ((lineFromFile = reader.readLine()) != null) {
                listOfPoints.add(lineFromFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.xPoints = new double[listOfPoints.size()];
        this.yPoints = new double[listOfPoints.size()];
        for (int i = 0; i < listOfPoints.size(); i++) {
            String lineInStringFormat = listOfPoints.get(i);
            String[] split = lineInStringFormat.split(" {1}");      //split line into the xPoints array and yPoints array
            xPoints[i] = Double.parseDouble(split[0]);                    //x-points
            yPoints[i] = Double.parseDouble(split[1]);                    //y-points
        }
    }

    /**
     * This method returns x-points from file.
     *
     * @return X-points.
     */
    public double[] getXFromFile() {
        readPoints(pathToTheFile);
        return xPoints;
    }

    /**
     * This method returns y-points from file
     *
     * @return Y-points.
     */
    public double[] getYFromFile() {
        readPoints(pathToTheFile);
        return yPoints;
    }

    /**
     * This method help us to find local extremum of the incoming function.
     *
     * @return the quantity of function extremum.
     */
    /*private static void extrema(double[] a, int n) {
        double deltaPlus = 0;
        double deltaMinus =0;
        double counterExtrema=0;
        double counter =0;

        for (int i=1;i<n-1;i++){
            if ((a[i-1]>a[i]&& a[i]<a[i+1])|| (a[i-1]<a[i]&& a[i]>a[i+1])){
                counterExtrema++;
            }
        }

        ArrayList<Double> deltaMinusList = new ArrayList<>();
        ArrayList<Double> deltaPlusList = new ArrayList<>();
        for (int i=1;i<n;i++){
            if (a[i]-a[i-1]>0){
                deltaPlusList.add(Math.abs(a[i]-a[i-1]));
            }else if (a[i]-a[i-1]<0){
                deltaMinusList.add(Math.abs(a[i]-a[i-1]));
            }
        }


        System.out.println("\nDELTA PLUS:");
        for (int i=0;i<deltaPlusList.size();i++){
            System.out.print(deltaPlusList.get(i)+" ");
        }

        System.out.println("\nDELTA MINUS:");
        for (int i=0;i<deltaMinusList.size();i++){
            System.out.print(deltaMinusList.get(i)+" ");
        }

        for (int i=0;i<deltaPlusList.size();i++){
            double delta= deltaPlusList.get(i);
            if (delta>deltaPlus){

                deltaPlus=delta;
            }
            for (int k=0;k<deltaMinusList.size();k++) {
                if (deltaMinusList.get(k)+1>delta && deltaMinusList.get(k)-1<delta){
                    deltaMinusList.remove(deltaMinusList.get(k));
                    //remove++;
                }
            }
        }

        Collections.sort(deltaPlusList);
        Collections.reverse(deltaPlusList);
        if (deltaPlusList.get(0)-deltaPlusList.get(1)>30){
            counter=1;
            deltaPlus=deltaPlusList.get(1);
            System.out.println("delta+: "+deltaPlus);
        }

        for (int i=0;i<deltaMinusList.size();i++){
            double delta = deltaMinusList.get(i);
            if (delta>deltaMinus){
                deltaMinus=delta;
                System.out.println("delta -: "+deltaMinus);
            }
        }


        System.out.println("\nNEW DELTA PLUS:");
        for (int i=0;i<deltaPlusList.size();i++){
            System.out.print(deltaPlusList.get(i)+" ");
        }

        System.out.println("\nNEW DELTA MINUS:");
        for (int i=0;i<deltaMinusList.size();i++){
            System.out.print(deltaMinusList.get(i)+" ");
        }

        System.out.println("\nDelta plus:"+deltaPlus);
        System.out.println("\nDelta minus:"+deltaMinus);
        counter = counter+deltaPlus/20+deltaMinus/20+(counterExtrema);
        extremaQuantity=(int)Math.round(counter);
        System.out.println(extremaQuantity);
    }*/


    /**
     * @return the quantity of function extremum.
     */
    public int getExtremaQuantity() {
        return extremaQuantity;
    }

    /**
     * @return the maximum fall at Bode magnitude plot.
     */
    public double getMaxFall() {
        return maxFall;
    }

    /**
     * @return the maximum growth at Bode magnitude plot.
     */
    public double getMaxGrowth() {
        return maxGrowth;
    }

    /**
     * @return the number of the chromosome at the set.
     */
    public int getChromosomeQuantity() {
        return chromosomeQuantity;
    }
}