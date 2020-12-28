Following are the commands and the instructions to run ANT on your project.
#### Note: build.xml is present in studentskills/src folder.

-----------------------------------------------------------------------
## Instruction to clean:

```commandline
ant -buildfile studentskills/src/build.xml clean
```

Description: It cleans up all the .class files that were generated when you
compiled your code.

-----------------------------------------------------------------------
## Instruction to compile:

```commandline
ant -buildfile studentskills/src/build.xml all
```

Description: Compiles your code and generates .class files inside the BUILD folder.

-----------------------------------------------------------------------
## Instruction to run:

```commandline
ant -buildfile studentskills/src/build.xml run -Dinput="input.txt" -Dmodify="modify.txt" -Doutput1="output1.txt" -Doutput2="output2.txt" -Doutput3="output3.txt" -Derror="error.txt" -Ddebug=2
```

Note: Arguments accept the absolute path of the files. Debug value can be 1/2/3/4/5/6


-----------------------------------------------------------------------
## Description:
The problem statement describes about designing the replicas of the Student Records which is to be represented as a tree, where each node of the tree corresponds to a Student Record.

2 classes i.e StudentRecord and TreeHelper has been created. StudentRecord class represents a tree node and TreeHelper is the helper class that helps build the tree.

StudentRecord node contains the fields like first name, last name, gpa, major, skills, left(left node), right(right node) and observers.

Student record implements both subject interface(SubjectI that has registerObserver() and notifyAll() methods declared) and observer interface(ObserverI that has update() method declared). Student record also has a clone method to create clones of StudentRecord node. StudentRecord node acts as both subject and observer.

TreeHelper class helps build the trees. Binary search tree data structure has been used to create tree because it is efficient and can perform fast searching and inserting operations.  This class has the following methods: readInputLine(), readModifyLine(), searchRecord(), insertRecord() and printNodes().

readInputLine() reads and splits the input line and fetches the data(bnumber, first name, last name, gpa, major and skills). It also holds references to the roots of 3 trees i.e. TreeHelper replicaTree0, TreeHelper replicaTree1, TreeHelper replicaTree2. For inserting the record, firstly, it is checked whether the currentRecordNode is null. If the currentRecordNode is null, new node is created and is cloned twice. In this case we have original node(node) and 2 clones(replicaNode1, replicaNode2). And each node registers other 2 nodes as its observers. i.e. node registers replicaNode1 and replicaNode2 as its observers, replicaNode1 registers node and replicaNode2 as its observers, replicaNode2 registers node and replicaNode1 as its observers. If the currentRecordNode is not null ,then notifyAll method is called and the particular tree is updated. Here, the insertion of nodes has been done in recursive manner with the help of insertRecursive() method.

readModifyLine() reads and splits the input line and fetches the data(bnumber, first name, last name, gpa, major and skills) and hold reference to the tree i.e. TreeHelper replicaTree. In this we modify the old value of the particular field with the new value specified in the file, call the notifyAll() method and update the tree.

searchRecord() is used to search the bnumber(if exists) in the node.

insertRecord() calls the insertRecursive() method to insert the record in the tree.

printNodes() takes the new Results reference/instance as a parameter to print the nodes/results.

Driver.java implementation:
Driver code creates 3 instances of TreeHelper class i.e. replicaTree0, replicaTree1, replicaTree2; which takes reference of writing errors to error.txt file respectively.
It creates 3 instances of Results class i.e output1, output2, output3; which is used to write the output to 3 different files(output1.txt, output2.txt, output3.txt).
Driver code parses input.txt file and calls readInputLine method to create/insert nodes, create clones and update or notify the tree.
Driver code parses the modify.txt file and call readModifyLine method to set new values in the node and then notify or update the tree.
Finally, printNodes() method is called that prints the results to the 3 output files.

MyLogger class is used for debugging purpose.

## Note: Maximum skills size needed is 10. So,if there are more than 10 skills in any input line, this code takes random 10 skills from the input line and prints in the output files and prints the error message of 'skills size exceeded 10' in the error.txt

## Justification for Data Structures used in this project:
1. String array is used to split the input line.
Time complexity: O(n^2)

2. List: ArrayList is used to store the Observers
Time complexity: O(n)

3. Set: HashSet is used to store the unique skills in, even if they are repeated. However, skills are not displayed in orderly manner.
Time complexity: O(1) in the best case. O(n) in the worst case

4. Binary search tree: BST has been used to created StudentRecord node because BST provides an ordering among keys so that the operations like search, Insert and delete can be done fast. However, our code only implements searching and inserting of node operations.
Time complexity: O(n) for all BST operations, where n is height of the tree.



## Citations:
BST SOURCE:
For insert: https://www.geeksforgeeks.org/binary-search-tree-set-1-search-and-insertion/
For search: https://algorithms.tutorialhorizon.com/binary-search-tree-complete-implementation/
