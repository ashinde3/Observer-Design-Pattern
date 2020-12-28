package studentskills.mytree;

/**
 * registerObserver and notifyAll methods declared
 */
public interface SubjectI {
    public void registerObserver(StudentRecord observer);
    public void notifyAll(StudentRecord node, Operation operation, String line);
}

