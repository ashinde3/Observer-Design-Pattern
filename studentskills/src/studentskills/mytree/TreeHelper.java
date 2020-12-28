package studentskills.mytree;

/**
 * BST SOURCE:
 * For insert: https://www.geeksforgeeks.org/binary-search-tree-set-1-search-and-insertion/
 * For search: https://algorithms.tutorialhorizon.com/binary-search-tree-complete-implementation/
 */

import studentskills.util.MyLogger;
import studentskills.util.Results;

import java.util.HashSet;
import java.util.Set;

public class TreeHelper {
    String data[];
    private final int id;
    private int bNumber;
    private String firstName;
    private String lastName;
    private double gpa;
    private String major;
    private static int count =0;
    private int insertNodes = 0;
    private StudentRecord studentRootNode;
    StudentRecord currentRecordNode = null;
    private StudentRecord replicaNode1 = null;
    private StudentRecord replicaNode2 = null;
    StudentRecord node = null;
    public String resultString = "";
    private Results result;

    /**
     * Constructor to set up initial root node as null
     */
    public TreeHelper(Results result) {
        this.result = result;
        this.id=getCounter();
        MyLogger.writeMessage("In TreeHelper constructor", MyLogger.DebugLevel.CONSTRUCTOR);
        studentRootNode = null;
    }
    public int getCounter(){
            return this.count++;
    }

    public int getId(){
        return this.id;
    }
    /**
     * Function to read the input
     * @param line to be read from input file
     * @param replicaTree0 object of TreeHelper
     * @param replicaTree1 object clone of TreeHelper
     * @param replicaTree2 object clone of TreeHelper
     */
    public void readInputLine(String line,TreeHelper replicaTree0,TreeHelper replicaTree1,TreeHelper replicaTree2) throws NumberFormatException {
        //String line,TreeHelper replicaTree0,TreeHelper replicaTree1,TreeHelper replicaTree2
        data = line.split(":");
        bNumber = Integer.parseInt(data[0]);
        if(bNumber < 1000 || bNumber > 9999) {
            result.writeToFile("Bnumber should be 4-digit integer..Exiting");
            System.err.println("Bnumber should be 4-digit integer..Exiting");
            System.exit(0);
        }
        data = data[1].split(",");
        firstName = data[0];
        lastName = data[1];
        gpa = Double.parseDouble(data[2]);
        major = data[3];
        Set<String> skills = new HashSet<>();
        for(int skillCount = 4; skillCount < data.length; skillCount++) {
            if(skills.size()<=10)
                skills.add(data[skillCount]);
        }

        if(skills.size() > 10) {
            result.writeToFile("Skills size exceeded. Only 10 Skills allowed.");
            //System.exit(0);
        }
        currentRecordNode = replicaTree0.searchRecord(bNumber);
        if(null == currentRecordNode) {
            node = new StudentRecord(bNumber,firstName,lastName,gpa,major,skills);
            replicaNode1 = node.clone();
            replicaNode2 = node.clone();
            node.registerObserver(replicaNode1);
            node.registerObserver(replicaNode2);
            replicaTree0.insertRecord(node);
            insertNodes++;
            MyLogger.writeMessage("New node added",MyLogger.DebugLevel.INSERT);

            /**
             * Creating first clone
             */
           // replicaNode1 = node.clone();

            replicaNode1.registerObserver(node);
            replicaNode1.registerObserver(replicaNode2);

            replicaTree1.insertRecord(replicaNode1);
            //node.registerObserver(replicaNode1);

            /**
             * Creating second clone
             */
            //replicaNode2 = node.clone();
            replicaNode2.registerObserver(node);
            replicaNode2.registerObserver(replicaNode1);
            replicaTree2.insertRecord(replicaNode2);
           // node.registerObserver(replicaNode2);
        }
        else if(null != currentRecordNode) {
            currentRecordNode.notifyAll(currentRecordNode,Operation.INSERT,line );
        }
    }

    public void readModifyLine(String line, TreeHelper replicaTree) throws NumberFormatException {
        data = line.split(":|,");
        bNumber = Integer.parseInt(data[1]);
        if(bNumber < 1000 || bNumber > 9999) {
            result.writeToFile("Bnumber should be 4-digit integer..Exiting");
            System.err.println("Bnumber should be 4-digit integer..Exiting");
            System.exit(0);
        }
        currentRecordNode = replicaTree.searchRecord(bNumber);
        String oldValue = data[2];
        String newValue =null;
        if(data.length != 4) {
            //System.out.println("no new value found");
            result.writeToFile("No new value found for replacement after ':'");
        }else {
            newValue = data[3];
        }
        String firstName = currentRecordNode.getFirstName();
        String lastName = currentRecordNode.getLastName();
        String major = currentRecordNode.getMajor();
        Set<String> skills = currentRecordNode.getSkills();
        if(null != currentRecordNode && data.length == 4 ) {
            if(firstName.equals(oldValue)) {
                currentRecordNode.setFirstName(newValue);
            }
            if(lastName.equals(oldValue)) {
                currentRecordNode.setLastName(newValue);
            }
            if(major.equals(oldValue)) {
                currentRecordNode.setMajor(newValue);
            }
            if(skills.contains(oldValue)) {
                currentRecordNode.getSkills().remove(oldValue);
                currentRecordNode.getSkills().add(newValue);
            }
            currentRecordNode.notifyAll(currentRecordNode,Operation.MODIFY, line);
            MyLogger.writeMessage("Node Modified",MyLogger.DebugLevel.MODIFY);
        }
        //currentRecordNode.notifyAll(currentRecordNode,Operation.MODIFY,line);
    }

    /**
     * Function to search record using BNumber
     * @param bNumber
     * @return null
     */
    public StudentRecord searchRecord(int bNumber) {
        MyLogger.writeMessage("Node Search",MyLogger.DebugLevel.SEARCH);
        StudentRecord temp = studentRootNode;
        while(null != temp) {
            if(temp.getbNumber() == bNumber){
                return temp;
            }
            else if(temp.getbNumber() > bNumber) {
                temp = temp.getLeft();
            }
            else {
                temp = temp.getRight();
            }
        }
        return null;
    }

    /**
     * Function to insert record in the root node
     * @param node
     * @return studentRootNode
     */
    public StudentRecord insertRecord(StudentRecord node) {
        studentRootNode = insertRecursive(studentRootNode, node);
        return studentRootNode;
    }

    /**
     * Recursive call to the insert function
     * @param studentRootNode
     * @param node
     * @return studentRootNode
     */
    public StudentRecord insertRecursive(StudentRecord studentRootNode, StudentRecord node) {
        if(null == studentRootNode) {
            studentRootNode = node;
            return studentRootNode;
        }
       else if(node.getbNumber() == studentRootNode.getbNumber()) {
            //Insert data bno:firstName,lastName,gpa,major,skillset
            if(!studentRootNode.getFirstName().contains(node.getFirstName())) {
                studentRootNode.setFirstName(node.getFirstName()) ;
            }
            if(!studentRootNode.getLastName().contains(node.getLastName())) {
                studentRootNode.setLastName(node.getLastName()) ;
            }
            if(studentRootNode.getGpa() != (node.getGpa())) {
                studentRootNode.setGpa(node.getGpa()); ;
            }
            if(!studentRootNode.getMajor().contains(node.getMajor())) {
                studentRootNode.setMajor(node.getMajor()); ;
            }
            if(!studentRootNode.getSkills().contains(node.getSkills())) {
                studentRootNode.getSkills().addAll(node.getSkills());
            }
        }
        else if(node.getbNumber() < studentRootNode.getbNumber()) {
            studentRootNode.setLeft(insertRecursive(studentRootNode.getLeft(),node));
        }
        else {
            studentRootNode.setRight(insertRecursive(studentRootNode.getRight(),node));
        }
        return studentRootNode;
    }


    /**
     * Printing the nodes with the help of Results instance
     */
    public void printNodes(Results result) {
        result.writeToStdout(resultString);
    }


    /**
     * Printing the traversal left,root,right
     * @param studentRootNode
     */
    public void print1( StudentRecord studentRootNode) {
        if(null != studentRootNode) {
            print1(studentRootNode.getLeft());
            resultString += studentRootNode.getbNumber() + ":";
            resultString += studentRootNode.getFirstName() + ",";
            resultString += studentRootNode.getLastName() + ",";
            resultString += studentRootNode.getGpa() + ",";
            resultString += studentRootNode.getMajor() + ",";
            for(String skills : studentRootNode.getSkills()) {
                resultString += skills + ",";
            }
            resultString += "\n";
            //printNodes();
            print1(studentRootNode.getRight());
        }
    }

    /**
     * Setters and getters to set and retrieve root nodes and its two replica nodes
     * @return studentRootNode,replicaNode1,replicaNode2
     */
    public StudentRecord getStudentRootNode() {
        return studentRootNode;
    }

    public void setStudentRootNode(StudentRecord studentRootNode) {
        this.studentRootNode = studentRootNode;
    }

    public StudentRecord getReplicaNode1() {
        return replicaNode1;
    }

    public void setReplicaNode1(StudentRecord replicaNode1) {
        this.replicaNode1 = replicaNode1;
    }

    public StudentRecord getReplicaNode2() {
        return replicaNode2;
    }

    public void setReplicaNode2(StudentRecord replicaNode2) {
        this.replicaNode2 = replicaNode2;
    }

    /**
     * Debugging purpose
     * @return class name of string type
     */
    public String toString()
    {
        return "TREE HELPER CLASS" + "\n" + getClass().getName() + "\n" ;
    }
}

