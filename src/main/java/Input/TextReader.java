package Input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextReader {

    private String pathToTheFile;                                 //unique location file in a file system
    private double[] xPoints, yPoints;                            //arrays of reading points

    /**
     * Parameterized constructor
     */
    public TextReader(String pathToTheFile) {
        this.pathToTheFile = pathToTheFile;
    }

    /**
     * The method helps us to read arrays of points
     * from the file
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

        xPoints = new double[listOfPoints.size()];
        yPoints = new double[listOfPoints.size()];
        for (int i = 0; i < listOfPoints.size(); i++) {
            String lineInStringFormat = listOfPoints.get(i);
            String[] split = lineInStringFormat.split(" {1}");      //split line into the xPoints array and yPoints array
            xPoints[i] = Double.parseDouble(split[0]);                    //x-points
            yPoints[i] = Double.parseDouble(split[1]);                    //y-points
        }
    }

    /**
     * This method returns x-points from file
     */
    double[] getXFromFile() {
        readPoints(pathToTheFile);
        return xPoints;
    }

    /**
     * This method returns y-points from file
     */
    public double[] getYFromFile() {
        readPoints(pathToTheFile);
        return yPoints;
    }
}