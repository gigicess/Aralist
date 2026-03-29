import java.util.*;


public class TaskManager {
    /** Hybrid Data Structure:
     * ArrayList<Task> for ordered storage and easy iteration
     * HashMap<String, Task> for O(1) access by task name (assuming unique names)
     * Priority Queue for sorting tasks by due date or priority
     */
    private ArrayList<Task> tasks = new ArrayList<>();

    private HashMap<String, Task> taskMap = new HashMap<>();

    private PriorityQueue<Task> taskQueue = new PriorityQueue<>(
        Comparator.comparing(Task::getDueDate)
              .thenComparing(t -> t.getPriority().getLevel()) //Faster sorting using Comparator
        );
    
    //Methods 

}
