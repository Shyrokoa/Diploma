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

    private String pathToTheFile;
    private double[] xPoints;
    private double[] yPoints;

    public TextReader(String pathToTheFile) {
        this.pathToTheFile = pathToTheFile;
        readPointsFromFile(pathToTheFile);
    }

    private void readPointsFromFile(String pathToTheFile) {
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

    public double[] getXFromFile() {
        readPointsFromFile(pathToTheFile);
        return xPoints;
    }

    public double[] getYFromFile() {
        readPointsFromFile(pathToTheFile);
        return yPoints;
    }
}