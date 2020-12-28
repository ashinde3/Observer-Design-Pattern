package studentskills.driver;

import studentskills.mytree.TreeHelper;
import studentskills.util.FileProcessor;
import studentskills.util.Results;
import studentskills.util.MyLogger;
import studentskills.util.MyLogger.DebugLevel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;


/**
 * @author Akshay Shinde
 *
 */
public class Driver {
    private static final int REQUIRED_NUMBER_OF_CMDLINE_ARGS = 7;

    public static void main(String[] args) throws IllegalArgumentException{

        /*
         * As the build.xml specifies the arguments as input,output or metrics, in case the
         * argument value is not given java takes the default value specified in
         * build.xml. To avoid that, below condition is used
         */
        try {
            if ((args.length != 7) || (args[0].equals("${input}")) || (args[1].equals("${modify}")) || (args[2].equals("${output1}")) || (args[3].equals("${output2}")) || (args[4].equals("${output3}")) || (args[5].equals("${error}")) || (args[6].equals("${debug}"))) {
                System.err.printf("Error: Incorrect number of arguments. Program accepts %d arguments.", REQUIRED_NUMBER_OF_CMDLINE_ARGS);
                System.exit(0);
            }
            System.out.println("Let's start Assignment 3..");
            System.out.println();
            String line = null;
            Results error = new Results(args[5]);
            /**
             * Building the tree and it's 2 replicas with the
             * help of TreeHelper class
             */
            TreeHelper replicaTree0 = new TreeHelper(error);
            TreeHelper replicaTree1 = new TreeHelper(error);
            TreeHelper replicaTree2 = new TreeHelper(error);

            /**
             * File processor instance to read input file argument
             */
            FileProcessor readFile = new FileProcessor(args[0]);
            line = readFile.poll();

            /**
             * Results instance to get output file argument
             * and store the result in it
             */
            Results output1 = new Results(args[2]);
            Results output2 = new Results(args[3]);
            Results output3 = new Results(args[4]);
            boolean inputFileCheck = false;
            boolean modifyFileCheck = false;
            int debugValue = Integer.parseInt(args[6]);
            if(!(debugValue > 0 && debugValue <=6)) {
                throw new NumberFormatException("Debug value should be between 1 and 6 including");
            }
            MyLogger.setDebugValue(debugValue);
            while(null != line) {
                inputFileCheck = true;
                replicaTree0.readInputLine(line,replicaTree0,replicaTree1,replicaTree2);
                line = readFile.poll();
            }
            if(inputFileCheck == false) {
                System.err.println("Empty input.txt file..Exiting");
                System.exit(0);
            }
            //read modify text file
            readFile = new FileProcessor(args[1]);
            line = readFile.poll();

            while(null != line) {
                modifyFileCheck = true;
                if(line.contains(replicaTree0.getId()+""))
                    replicaTree0.readModifyLine(line,replicaTree0);
                else if(line.contains(replicaTree1.getId()+""))
                    replicaTree1.readModifyLine(line,replicaTree1);
                else if(line.contains(replicaTree2.getId()+""))
                    replicaTree2.readModifyLine(line,replicaTree2);
                line = readFile.poll();
            }

            if(modifyFileCheck == false) {
                System.err.println("Empty modify.txt file..Exiting");
                System.exit(0);
            }
            /**
             * Printing the nodes
             */
            System.out.println("--Replica Tree 0--");
            replicaTree0.print1(replicaTree0.getStudentRootNode());
            replicaTree0.printNodes(output1);
            output1.writeToFile(output1.getSentence());

            System.out.println("--Replica Tree 1--");
            replicaTree1.print1(replicaTree1.getStudentRootNode());
            replicaTree1.printNodes(output2);
            output2.writeToFile(output2.getSentence());

            System.out.println("--Replica Tree 2--");
            replicaTree2.print1(replicaTree2.getStudentRootNode());
            replicaTree2.printNodes(output3);
            output3.writeToFile(output3.getSentence());

            output1.close();
            output2.close();
            output3.close();
            error.close();
        }
        /**
         * Exceptions handling
         */
        catch (FileNotFoundException file) {
            System.err.println("No File found...Exiting");
            System.err.println(file.getMessage());
            file.printStackTrace();
            System.exit(0);
        }
        catch (IOException io) {
            System.err.println("Invalid I/O processed...Exiting");
            System.err.println(io.getMessage());
            io.printStackTrace();
            System.exit(0);
        }
        catch(InvalidPathException path) {
            System.err.println("Invalid path...Exiting");
            System.err.println(path.getMessage());
            path.printStackTrace();
            System.exit(0);
        }
        catch(SecurityException security) {
            System.err.println("Security Exception check...Exiting");
            System.err.println(security.getMessage());
            security.printStackTrace();
            System.exit(0);
        }
        catch(NumberFormatException number) {
            System.err.println("Invalid number detected...Exiting");
            System.err.println(number.getMessage());
            number.printStackTrace();
            System.exit(0);
        }
        catch(IllegalArgumentException arg) {
            System.err.println("Illegal arguments...Exiting");
            System.err.println(arg.getMessage());
            arg.printStackTrace();
            System.exit(0);
        }
        catch(NullPointerException nullPtr) {
            System.err.println("Null pointer exception...Exiting");
            System.err.println(nullPtr.getMessage());
            nullPtr.printStackTrace();
            System.exit(0);
        }
        finally {

        }
    }
}

