import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate; //for saving/loading tasks
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
    Comparator.comparing(Task::getDueDate)                // Earliest due date
              .thenComparing(Task::getPriority)           // Higher priority
              .thenComparing(t ->                        // Category
                  t.getCustomCategory() != null && !t.getCustomCategory().isEmpty()
                      ? t.getCustomCategory().toLowerCase()
                      : t.getCategory().name().toLowerCase()
              )
    );

    private final String username; //for multi-user support 

    public TaskManager(String username) {
        this.username = username;
    }

    @SuppressWarnings("unused")
    private String getFilePath() {
        return "data/" + username + "/tasks.dat";
    }
    
//Methods 

    // Add Task
    public void addTask(Task task) throws DuplicateTaskException {
        if (taskMap.containsKey(task.getName().toLowerCase())) {
            throw new DuplicateTaskException("Task already exists."); //Exception for duplicate tasks
        }
        tasks.add(task);
        taskMap.put(task.getName().toLowerCase(), task);
        taskQueue.offer(task);
    }

    //Delete Task 
        //by Index 
    public void deleteTaskIndex(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.remove(index);
            taskMap.remove(task.getName().toLowerCase());
            taskQueue.remove(task);
        } else {
            throw new IndexOutOfBoundsException("Invalid task index.");
        }
    }
        //by Name
    public void deleteTaskName(String name) {
        Task task = taskMap.remove(name.toLowerCase());
        if (task != null) {
            tasks.remove(task);
            taskQueue.remove(task);
        } else {
            throw new NoSuchElementException("Task not found.");
        }
    }

        //Delete All 
    public void deleteAllTasks() {
        tasks.clear();
        taskMap.clear();
        taskQueue.clear();
    }

    //Edit Task 
    public void editTask(int index, String newName, LocalDate newDueDate, Priority newPriority,
                         Recurrence newRecurrence, Categorize newCategory, String newCustomCategory) throws InvalidTaskNameException {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            taskMap.remove(task.getName().toLowerCase()); // Remove old name from map
            taskQueue.remove(task); // Remove from priority queue before updating

           // Update only if new values are provided
            if (newName != null && newName.trim().isEmpty()) {
                throw new InvalidTaskNameException("Task name cannot be empty.");
            }
            if (newDueDate != null) task.setDueDate(newDueDate);
            if (newPriority != null) task.setPriority(newPriority);
            if (newRecurrence != null) task.setRecurrence(newRecurrence);
            if (newCategory != null) task.setCategory(newCategory);
            if (newCustomCategory != null && !newCustomCategory.isEmpty()) task.setCustomCategory(newCustomCategory);

            taskMap.put(task.getName().toLowerCase(), task); // Add updated name to map
            taskQueue.add(task); // Re-add to priority queue with updated fields

        } else {
            throw new IndexOutOfBoundsException("Invalid task index.");
        }
    }

    //Mark Task as Completed
    public void markTaskCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.setCompleted(true);
        } else {
            throw new IndexOutOfBoundsException("Invalid task index.");
        }
    }

    // Search by Name
    public List<Task> search(String keyword) {
        List<Task> results = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(t);
            }
        }
        return results;
    }

    //Filter by due date 
    public List<Task> filterByDueDate(LocalDate date) {
        List<Task> results = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getDueDate().equals(date)) {
                results.add(t);
            }
        }
        return results;
    }

    //Filter by Category 
    public List<Task> filterbyCategory(Categorize category, String customCategory) {
        List<Task> results = new ArrayList<>();
        for (Task t : tasks) {
            if (category != null && t.getCategory() == category) {
                results.add(t);
            } else if (customCategory != null &&
                       t.getCustomCategory() != null &&
                       t.getCustomCategory().equalsIgnoreCase(customCategory)) {
                results.add(t);
            }
        }
        return results;
    }

    //Sort Tasks by Due date, Priority, Category 
    public void sortTasks() {
        tasks.sort(Comparator.comparing(Task::getDueDate)
                            .thenComparing(Task::getCreatedTime)
                            .thenComparing(Task::getPriority));
    }

    //Get Highest Priority Task
    public Task getHighestPriorityTask() {
        return taskQueue.peek();
    }

    //Save tasks to file
    public void saveTasks() throws IOException {
        File dir = new File("data/" + username);
        if (!dir.exists()) dir.mkdirs(); // ensure directory exists

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(getFilePath()))) {
            out.writeObject(tasks);
        }
    }


    //Load Tasks from file
    @SuppressWarnings("unchecked")
    public void loadTasks(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            tasks = (ArrayList<Task>) in.readObject();
            // rebuild map and queue
            taskMap.clear();
            taskQueue.clear();
            for (Task t : tasks) {
                taskMap.put(t.getName().toLowerCase(), t);
                taskQueue.add(t);
            }
        }
    }

    //Get all Tasks
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }
    
// Getters and Setters for GUI
    public ArrayList<Task> getTasks() { return tasks; }
    public HashMap<String, Task> getTaskMap() { return taskMap;}
    public PriorityQueue<Task> getTaskQueue() { return taskQueue; }

    public void setTasks(ArrayList<Task> tasks) { this.tasks = tasks; }
    public void setTaskMap(HashMap<String, Task> taskMap) {this.taskMap = taskMap; }
    public void setTaskQueue(PriorityQueue<Task> taskQueue) { this.taskQueue = taskQueue; }
}