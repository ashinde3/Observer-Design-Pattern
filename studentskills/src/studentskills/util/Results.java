package studentskills.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Results implements FileDisplayInterface, StdoutDisplayInterface {
    private String sentence;
    private FileWriter outputFile;

    /**
     * Constructor to create new file and store results in it
     * @param fileName
     */
    public Results(String fileName) {
        try {
            outputFile = new FileWriter(new File(fileName));
        } catch (IOException e) {
            System.err.println("No file");
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * Method to write the output to the file
     * @param sentence
     */
    @Override
    public void writeToFile(String sentence) {
        try {
            outputFile.write(sentence+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to write the output to the std out
     * @param sentence
     */
    @Override
    public void writeToStdout(String sentence) {
        this.sentence = sentence;
        System.out.println(sentence+"\n");
    }

    /**
     * closing the file connection
     */
    public void close() {
        try {
            outputFile.close();
        } catch (IOException e) {
            System.err.println("could not close file");
            e.printStackTrace();
        } finally {

        }
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    /**
     * Debugging purpose
     * @return class name of string type
     */
    public String toString()
    {
        return "RESULTS CLASS" + "\n" + getClass().getName() + "\n" ;
    }
}

