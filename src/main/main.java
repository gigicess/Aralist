package main;

import analytics.TaskAnalytics;
import exceptions.DuplicateTaskException;
import exceptions.InvalidDateException;
import exceptions.InvalidTaskNameException;
import exceptions.TypeExceptions;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import manager.TaskManager;
import model.Categorize;
import model.Priority;
import model.Recurrence;
import model.Task;
import util.DateHelper;
import util.InputHelper;

public class main {
    public static void main(String[] args) throws TypeExceptions {
        // Ask for username
        try (Scanner scanner = new Scanner(System.in)) {
            // Ask for username
            System.out.print("Enter your username: ");
            String username = scanner.nextLine().trim();
            // Banner
            System.out.println("==================================");
            System.out.println("   Welcome to Aralist, " + username + "!");
            System.out.println("==================================");
            // Initialize TaskManager and load tasks
            TaskManager manager = new TaskManager(username);
            try {
                manager.loadTasks("data/" + username + "/tasks.dat"); // load from data/username/tasks.dat
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("No saved tasks found for " + username + ". Starting fresh.");
            }
            // Initialize analytics
            @SuppressWarnings("unused")
            TaskAnalytics analytics = new TaskAnalytics(manager);
            boolean running = true;
            while (running) {
                System.out.println("\n--- Task Menu ---");
                System.out.println("1. Add Task");
                System.out.println("2. Delete Task");
                System.out.println("3. Edit Task");
                System.out.println("4. Mark Task as Completed");
                System.out.println("5. Search by Name");
                System.out.println("6. Filter by Due Date");
                System.out.println("7. Filter by Category");
                System.out.println("8. Sort Tasks (Due date, Priority, Category)");
                System.out.println("9. Get Highest Priority Task");
                System.out.println("10. Save Tasks to File");
                System.out.println("11. Load Tasks from File");
                System.out.println("12. Get All Tasks");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                
                int choice = InputHelper.parseInt(scanner.nextLine());
                
                try {
                    switch (choice) {
                        case 1 -> {
                            // Add Task
                            System.out.print("Task name: ");
                            String name = scanner.nextLine();
                            
                            System.out.print("Due date: ");
                            String dateInput = scanner.nextLine();
                            LocalDate dueDate = DateHelper.parseDate(dateInput);
                            
                            System.out.print("Recurrence (NONE, DAILY, WEEKLY, MONTHLY): ");
                            Recurrence recurrence = InputHelper.parseEnum(Recurrence.class, scanner.nextLine());
                            
                            System.out.print("Category (WORK, SCHOOL, PERSONAL, SHOPPING, FITNESS, OTHER): ");
                            Categorize category = InputHelper.parseEnum(Categorize.class, scanner.nextLine());
                            
                            System.out.print("Priority (HIGH, MEDIUM, LOW): ");
                            Priority priority = InputHelper.parseEnum(Priority.class, scanner.nextLine());
                            
                            Task task = new Task(name, dueDate, priority, recurrence, category, null);
                            manager.addTask(task);
                            System.out.println("Task added!");
                        }
                        
                        
                        
                        case 2 -> {
                            // Delete Task
                            if (manager.getTasks().isEmpty()) {
                                System.out.println("No tasks available to delete.");
                                break;
                            }
                            
                            // Print all tasks with indices
                            System.out.println("Current tasks:");
                            for (int i = 0; i < manager.getTasks().size(); i++) {
                                Task t = manager.getTasks().get(i);
                                System.out.println(i + ": " + t.getName() + " | Due: " + DateHelper.formatDate(t.getDueDate())
                                        + " | Priority: " + t.getPriority()
                                        + " | Category: " + t.getCategory());
                            }
                            
                            System.out.print("""
                                                Delete Task:
                                                1. By Index
                                                2. By Name
                                                3. By Due Date
                                            """);
                            System.out.print("Choose an option: ");
                            int deleteChoice = Integer.parseInt(scanner.nextLine());
                            switch(deleteChoice) {
                                case 1 -> {
                                    //By Index
                                    System.out.print("Enter Task Index: ");
                                    int index = Integer.parseInt(scanner.nextLine());
                                    manager.deleteTaskIndex(index);
                                    System.out.println("Task deleted!");
                                }
                                
                                case 2 -> {
                                    //By Name
                                    System.out.print("Enter name: ");
                                    String taskName = scanner.nextLine();
                                    manager.deleteTaskName(taskName);
                                    System.out.println("Task deleted!");
                                }
                                
                                case 3 -> {
                                    //All
                                    manager.deleteAllTasks();
                                    System.out.println("All tasks deleted!");
                                }
                                default -> System.out.println("Invalid choice.");
                            }
                        }
                        
                        
                        case 3 -> {
                            //Edit Task
                            if (manager.getTasks().isEmpty()) {
                                System.out.println("No tasks available to edit.");
                                break;
                            }
                            
                            // Print all tasks with indices
                            System.out.println("Current tasks:");
                            for (int i = 0; i < manager.getTasks().size(); i++) {
                                Task t = manager.getTasks().get(i);
                                System.out.println(i + ": " + t.getName() + " | Due: " + DateHelper.formatDate(t.getDueDate())
                                        + " | Priority: " + t.getPriority()
                                        + " | Category: " + t.getCategory());
                            }
                            System.out.print("Enter task index to edit: ");
                            int index = Integer.parseInt(scanner.nextLine());
                            
                            System.out.print("New task name (leave blank to keep current): ");
                            String newName = scanner.nextLine();
                            if (newName.trim().isEmpty()) newName = null;
                            
                            System.out.print("New due date (MM-dd, MM/dd, or include year; leave blank to keep current): ");
                            String dateInput = scanner.nextLine();
                            LocalDate newDueDate = null;
                            if (!dateInput.trim().isEmpty()) {
                                try {
                                    newDueDate = DateHelper.parseDate(dateInput);
                                } catch (InvalidDateException e) {
                                    System.out.println("Invalid date format. Task not updated.");
                                    break;
                                }
                            }
                            
                            System.out.print("New priority (HIGH, MEDIUM, LOW; leave blank to keep current): ");
                            String priorityInput = scanner.nextLine();
                            Priority newPriority = null;
                            if (!priorityInput.trim().isEmpty()) {
                                newPriority = Priority.valueOf(priorityInput.toUpperCase());
                            }

                            System.out.print("New recurrence (NONE, DAILY, WEEKLY, MONTHLY; leave blank to keep current): ");
                            String recurrenceInput = scanner.nextLine();
                            Recurrence newRecurrence = null;
                            if (!recurrenceInput.trim().isEmpty()) {
                                newRecurrence = Recurrence.valueOf(recurrenceInput.toUpperCase());
                            }
                            
                            System.out.print("New category (WORK, SCHOOL, PERSONAL, SHOPPING, FITNESS, OTHER; leave blank to keep current): ");
                            String categoryInput = scanner.nextLine();
                            Categorize newCategory = null;
                            String newCustomCategory = null;
                            if (!categoryInput.trim().isEmpty()) {
                                if (categoryInput.equalsIgnoreCase("OTHER")) {
                                    System.out.print("Enter custom category: ");
                                    newCustomCategory = scanner.nextLine();
                                    newCategory = Categorize.OTHER;
                                } else {
                                    newCategory = Categorize.valueOf(categoryInput.toUpperCase());
                                }
                            }
                            
                            try {
                                manager.editTask(index, newName, newDueDate, newPriority, newRecurrence, newCategory, newCustomCategory);
                                System.out.println("Task updated!");
                            } catch (InvalidTaskNameException | IndexOutOfBoundsException e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                            break;
                        }
                        
                        case 4 -> {
                            //Mark as Completed
                            if (manager.getTasks().isEmpty()) {
                                System.out.println("No tasks available to edit.");
                                break;
                            }
                            
                            // Print all tasks with indices
                            System.out.println("Current tasks:");
                            for (int i = 0; i < manager.getTasks().size(); i++) {
                                Task t = manager.getTasks().get(i);
                                System.out.println(i + ": " + t.getName() + " | Due: " + DateHelper.formatDate(t.getDueDate())
                                        + " | Priority: " + t.getPriority()
                                        + " | Category: " + t.getCategory());
                            }
                            System.out.print("Enter task index to mark as completed: ");
                            int index = Integer.parseInt(scanner.nextLine());
                            
                            try {
                                manager.markTaskCompleted(index);
                                System.out.println("Task marked as completed!");
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                            break;
                        }
                        
                        case 5 -> {
                            //Search by Name
                            if (manager.getTasks().isEmpty()) {
                                System.out.println("No tasks available to search.");
                                break;
                            }
                            
                            System.out.print("Enter keyword to search: ");
                            String keyword = scanner.nextLine();
                            
                            List<Task> results = manager.search(keyword);
                            
                            if (results.isEmpty()) {
                                System.out.println("No tasks found matching \"" + keyword + "\".");
                            } else {
                                System.out.println("Search results:");
                                for (Task t : results) {
                                    System.out.println("- " + t.getName()
                                            + " | Due: " + DateHelper.formatDate(t.getDueDate())
                                            + " | Priority: " + t.getPriority()
                                            + " | Category: " + t.getCategory()
                                            + " | Completed: " + (t.isCompleted() ? "Yes" : "No"));
                                }
                            }
                            break;
                        }
                        
                        case 6 -> {
                            //Filter by Due Date
                            if (manager.getTasks().isEmpty()) {
                                System.out.println("No tasks available to filter.");
                                break;
                            }

                            System.out.print("Enter due date (MM-dd, MM/dd, or include year): ");
                            String dateInput = scanner.nextLine();
                            LocalDate filterDate = null;

                            try {
                                filterDate = DateHelper.parseDate(dateInput);
                            } catch (InvalidDateException e) {
                                System.out.println("Invalid date format. Please try again.");
                                break;
                            }

                            List<Task> results = manager.filterByDueDate(filterDate);

                            if (results.isEmpty()) {
                                System.out.println("No tasks found with due date " + DateHelper.formatDate(filterDate));
                            } else {
                                System.out.println("Tasks due on " + DateHelper.formatDate(filterDate) + ":");
                                for (Task t : results) {
                                System.out.println("- " + t.getName()
                                    + " | Priority: " + t.getPriority()
                                    + " | Category: " + t.getCategory()
                                    + " | Completed: " + (t.isCompleted() ? "Yes" : "No"));
                                }
                        }
                        break;
                        }

                        case 7 -> {
                            //Filter by Category 

                        if (manager.getTasks().isEmpty()) {
                            System.out.println("No tasks available to filter.");
                            break;
                        }

                        System.out.print("Enter category (WORK, SCHOOL, PERSONAL, SHOPPING, FITNESS, OTHER): ");
                        String categoryInput = scanner.nextLine();

                        Categorize category = null;
                        String customCategory = null;

                        if (!categoryInput.trim().isEmpty()) {
                            if (categoryInput.equalsIgnoreCase("OTHER")) {
                                System.out.print("Enter custom category: ");
                                customCategory = scanner.nextLine();
                                category = Categorize.OTHER;
                            } else {
                                try {
                                    category = Categorize.valueOf(categoryInput.toUpperCase());
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Invalid category. Please try again.");
                                    break;
                                }
                            }
                        }

                        List<Task> results = manager.filterbyCategory(category, customCategory);

                        if (results.isEmpty()) {
                            System.out.println("No tasks found for category: " 
                                            + (customCategory != null ? customCategory : category));
                        } else {
                            System.out.println("Tasks in category " 
                                            + (customCategory != null ? customCategory : category) + ":");
                            for (Task t : results) {
                                System.out.println("- " + t.getName()
                                                + " | Due: " + DateHelper.formatDate(t.getDueDate())
                                                + " | Priority: " + t.getPriority()
                                                + " | Completed: " + (t.isCompleted() ? "Yes" : "No"));
                            }
                        }
                        break;
                        }

                        case 8 -> {
                            if (manager.getTasks().isEmpty()) {
                                System.out.println("No tasks available to sort.");
                            break;
                            }

                        manager.sortTasks();

                        System.out.println("Tasks sorted by due date, created time, and priority:");
                        for (int i = 0; i < manager.getTasks().size(); i++) {
                            Task t = manager.getTasks().get(i);
                            System.out.println(i + ": " + t.getName()
                                            + " | Due: " + DateHelper.formatDate(t.getDueDate())
                                            + " | Priority: " + t.getPriority()
                                            + " | Category: " + t.getCategory()
                                            + " | Completed: " + (t.isCompleted() ? "Yes" : "No"));
                        }
                        break;
                        }

                        case 9 -> {
                            //Get Highest Priority Task
                            Task highest = manager.getHighestPriorityTask();

                        if (highest == null) {
                            System.out.println("No tasks available.");
                        } else {
                            System.out.println("Highest priority task:");
                            System.out.println("- " + highest.getName()
                                            + " | Due: " + DateHelper.formatDate(highest.getDueDate())
                                            + " | Priority: " + highest.getPriority()
                                            + " | Category: " + highest.getCategory()
                                            + " | Completed: " + (highest.isCompleted() ? "Yes" : "No"));
                        }
                        break;
                        }

                        case 10 -> {
                            //Save Tasks
                            if (manager.getTasks().isEmpty()) {
                                System.out.println("No tasks to save.");
                                break;
                            }

                            try {
                                manager.saveTasks();
                                System.out.println("Tasks saved successfully");
                            } catch (IOException e) {
                                System.out.println("Error saving tasks: " + e.getMessage());
                            }
                            break;
                        }

                        case 11 -> {
                            //Load Tasks
                            System.out.print("Enter your username: ");
                            String loadUsername = scanner.nextLine();

                            String filename = "data/" + loadUsername + "/tasks.dat";

                            try {
                                manager.loadTasks(filename);
                                System.out.println("Tasks loaded successfully for user: " + loadUsername);

                                if (manager.getTasks().isEmpty()) {
                                    System.out.println("No tasks found in file.");
                                } else {
                                    System.out.println("Loaded tasks:");
                                    for (int i = 0; i < manager.getTasks().size(); i++) {
                                        Task t = manager.getTasks().get(i);
                                        System.out.println(i + ": " + t.getName()
                                                        + " | Due: " + DateHelper.formatDate(t.getDueDate())
                                                        + " | Priority: " + t.getPriority()
                                                        + " | Category: " + t.getCategory()
                                                        + " | Completed: " + (t.isCompleted() ? "Yes" : "No"));
                                    }
                                }
                            } catch (IOException | ClassNotFoundException e) {
                                System.out.println("Error loading tasks: " + e.getMessage());
                            }
                            break;
                        }
                        
                        case 12 -> {
                            //Get All Tasks
                            List<Task> allTasks = manager.getAllTasks();

                            if (allTasks.isEmpty()) {
                                System.out.println("No tasks available.");
                            } else {
                                System.out.println("All tasks:");
                                for (int i = 0; i < allTasks.size(); i++) {
                                    Task t = allTasks.get(i);
                                    System.out.println(i + ": " + t.getName()
                                                    + " | Due: " + DateHelper.formatDate(t.getDueDate())
                                                    + " | Priority: " + t.getPriority()
                                                    + " | Category: " + t.getCategory()
                                                    + (t.getCustomCategory() != null ? " (" + t.getCustomCategory() + ")" : "")
                                                    + " | Completed: " + (t.isCompleted() ? "Yes" : "No"));
                                }
                            }
                            break;
                        }

                        case 0 -> {
                            // Exit
                            running = false;
                            System.out.println("Goodbye, " + username + "!");
                        }
                            default -> // Invalid choice
                            System.out.println("Invalid choice.");
                    }    
                } catch (DuplicateTaskException | InvalidDateException e) {
                    System.err.println("Error: " + e.getMessage());
                } catch (NumberFormatException e) {
                    System.err.println("Unexpected error: " + e.getMessage());
                }
            }    
        }
    }
}