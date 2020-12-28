package studentskills.mytree;

import studentskills.util.MyLogger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentRecord implements SubjectI, ObserverI, Cloneable {
    /**
     * Declaration of data members
     */
    private StudentRecord left;
    private StudentRecord right;
    private int bNumber;
    private String firstName;
    private String lastName;
    private double gpa;
    private String major;
    private Set<String> skills = new HashSet<>();
    private List<StudentRecord> observers = new ArrayList<>();

    /**
     * Parameterized Constructor
     * @param bNumber
     * @param firstName
     * @param lastName
     * @param gpa
     * @param major
     * @param skills
     */
    public StudentRecord(int bNumber, String firstName, String lastName, double gpa, String major, Set<String> skills) {
        MyLogger.writeMessage("In Student Record Constructor",MyLogger.DebugLevel.CONSTRUCTOR);
        this.bNumber = bNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gpa = gpa;
        this.major = major;
        this.skills = skills;
        left = null;
        right = null;

    }

    public StudentRecord() {

    }

    /**
     * Fetches left node value
     * @return left
     */
    public StudentRecord getLeft() {
        return left;
    }

    /**
     * Sets the value for left node
     * @param left
     */
    public void setLeft(StudentRecord left) {
        this.left = left;
    }

    /**
     * Fetches right node value
     * @return right
     */
    public StudentRecord getRight() {
        return right;
    }

    /**
     * Sets the value for right node
     * @param right
     */
    public void setRight(StudentRecord right) {
        this.right = right;
    }

    /**
     * Fetches BNumber
     * @return bNumber
     */
    public int getbNumber() {
        return bNumber;
    }

    /**
     * Sets the value for BNumber
     * @param bNumber
     */
    public void setbNumber(int bNumber) {
        this.bNumber = bNumber;
    }

    /**
     * Fetches the first name
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Fetches the last name
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Fetches the gpa
     * @return gpa of double type
     */
    public double getGpa() {
        return gpa;
    }

    /**
     * Sets the gpa
     * @param gpa
     */
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    /**
     * Fetches the major
     * @return major
     */
    public String getMajor() {
        return major;
    }

    /**
     * Sets the major
     * @param major
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * Fetches the skillset
     * @return skills of string type
     */
    public Set<String> getSkills() {
        return skills;
    }

    /**
     * Sets the skills and stores it in set
     * @param skills
     */
    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }

    /**
     * Retrieves the observers
     * @return observers
     */
    public List<StudentRecord> getObservers() {
        return observers;
    }

    /**
     * Sets the observers
     * @param observers
     */
    public void setObservers(List<StudentRecord> observers) {
        this.observers = observers;
    }

    /**
     * Updates the observer nodes when it's called
     * @param node
     * @param operation
     */
    @Override
    public void update(StudentRecord node, Operation operation, String line) throws NumberFormatException {
        switch(operation) {
            case INSERT:
                MyLogger.writeMessage("In Student record Insert", MyLogger.DebugLevel.INSERT);
                break;

            case MODIFY:
            {
                //0,1234,John:John7
                MyLogger.writeMessage("In Student record Modify", MyLogger.DebugLevel.MODIFY);
                String[] data = line.split(":|,");
                bNumber = Integer.parseInt(data[1]);
                if(bNumber < 1000 || bNumber > 9999) {
                    System.err.println("Bnumber should be 4-digit integer..Exiting");
                    System.exit(0);
                }
                String oldValue = data[2];
                String newValue =null;
                if(data.length != 4) {
                    System.out.println("no new value found");
                }else {
                    newValue = data[3];
                }

                //currentRecordNode = replicaTree.searchRecord(bNumber);
                String firstName = node.getFirstName();
                String lastName = node.getLastName();
                String major = node.getMajor();
                Set<String> skills = node.getSkills();
                if(null != node && data.length == 4 ) {
                    if(firstName.equals(oldValue)) {
                        node.setFirstName(newValue);
                    }
                    if(lastName.equals(oldValue)) {
                        node.setLastName(newValue);
                    }
                    if(major.equals(oldValue)) {
                        node.setMajor(newValue);
                    }
                    if(skills.contains(oldValue)) {
                        node.getSkills().remove(oldValue);
                        node.getSkills().add(newValue);
                    }
                }
            }

            break;
        }
    }

    /**
     * Method to register the observer
     * @param observer
     */
    @Override
    public void registerObserver(StudentRecord observer) {
        observers.add(observer);
    }

    /**
     * Method to notify all the observers if changes made to any observer
     * @param node
     * @param operation
     */
    @Override
    public void notifyAll(StudentRecord node, Operation operation, String line) {
        for(StudentRecord student : observers) {
            update(student,operation, line);
        }
    }

    /**
     * Creates the clones of the tree
     * @return left,right,bNumber,firstName,lastName,gpa,major,skills
     */
    public StudentRecord clone() {
        MyLogger.writeMessage("In Student Record Clone",MyLogger.DebugLevel.CLONE);
        StudentRecord cloneRecord = new StudentRecord();
        Set<String> cloneSkills = new HashSet<>();
        cloneRecord.left = null;
        cloneRecord.right = null;
        cloneRecord.setbNumber(this.bNumber);
        cloneRecord.setFirstName(this.firstName);
        cloneRecord.setLastName(this.lastName);
        cloneRecord.setGpa(this.gpa);
        cloneRecord.setMajor(this.major);
        for(String skillSet : this.skills) {
            cloneSkills.add(skillSet);
        }
        cloneRecord.setSkills(cloneSkills);
        return cloneRecord;
    }

    /**
     * For debugging purpose
     * @return class name of string type
     */
    public String toString()
    {
        return "STUDENT RECORD CLASS" + "\n" + getClass().getName() + "\n" ;
    }
}

