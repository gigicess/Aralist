package main;

import analytics.TaskAnalytics;
import exceptions.DuplicateTaskException;
import exceptions.InvalidDateException;
import exceptions.InvalidTaskNameException;
import java.time.LocalDate;
import java.util.Scanner;
import manager.TaskManager;
import model.Categorize;
import model.Priority;
import model.Recurrence;
import model.Task;
import util.DateHelper;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
        } catch (Exception e) {
            System.out.println("No saved tasks found for " + username + ". Starting fresh.");
        }

        // Initialize analytics
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

            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1: // Add Task
                        System.out.print("Task name: ");
                        String name = scanner.nextLine();

                        System.out.print("Due date: ");
                        String dateInput = scanner.nextLine();
                        LocalDate dueDate = DateHelper.parseDate(dateInput);

                        System.out.print("Priority (HIGH, MEDIUM, LOW): ");
                        Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());

                        System.out.print("Category (WORK, PERSONAL, STUDY, OTHER): ");
                        Categorize category = Categorize.valueOf(scanner.nextLine().toUpperCase());

                        Task task = new Task(name, dueDate, priority,
                                             Recurrence.NONE, category, null);
                        manager.addTask(task);
                        System.out.println("Task added!");
                        break;

                    case 2: // Delete Task submenu
                        System.out.println("a. Delete by ID");
                        System.out.println("b. Delete by Category");
                        System.out.println("c. Delete All Tasks");
                        System.out.print("Choose option: ");
                        String delChoice = scanner.nextLine().toLowerCase();

                        switch (delChoice) {
                            case "a":
                                System.out.print("Enter index: ");
                                int index = Integer.parseInt(scanner.nextLine());
                                manager.deleteTaskIndex(index);
                                System.out.println("Task deleted by ID.");
                                break;
                            case "b":
                                System.out.print("Enter category: ");
                                Categorize delCat = Categorize.valueOf(scanner.nextLine().toUpperCase());
                                manager.getAllTasks().removeIf(t -> t.getCategory() == delCat);
                                System.out.println("Tasks deleted by category.");
                                break;
                            case "c":
                                manager.deleteAllTasks();
                                System.out.println("All tasks deleted.");
                                break;
                            default:
                                System.out.println("Invalid delete option.");
                        }
                        break;

                    case 3: // Edit Task
                        System.out.print("Enter index to edit: ");
                        int editIndex = Integer.parseInt(scanner.nextLine());
                        System.out.print("New name: ");
                        String newName = scanner.nextLine();
                        System.out.print("New due date (yyyy-MM-dd): ");
                        LocalDate newDate = DateHelper.parseDate(scanner.nextLine());
                        System.out.print("New priority: ");
                        Priority newPriority = Priority.valueOf(scanner.nextLine().toUpperCase());
                        System.out.print("New category: ");
                        Categorize newCategory = Categorize.valueOf(scanner.nextLine().toUpperCase());

                        manager.editTask(editIndex, newName, newDate, newPriority,
                                         Recurrence.NONE, newCategory, null);
                        System.out.println("Task edited.");
                        break;

                    case 4: // Mark completed
                        System.out.print("Enter index: ");
                        int compIndex = Integer.parseInt(scanner.nextLine());
                        manager.markTaskCompleted(compIndex);
                        System.out.println("Task marked as completed.");
                        break;

                    case 5: // Search
                        System.out.print("Enter keyword: ");
                        String keyword = scanner.nextLine();
                        System.out.println(manager.search(keyword));
                        break;

                    case 6: // Filter by due date
                        System.out.print("Enter date (yyyy-MM-dd): ");
                        LocalDate filterDate = DateHelper.parseDate(scanner.nextLine());
                        System.out.println(manager.filterByDueDate(filterDate));
                        break;

                    case 7: // Filter by category
                        System.out.print("Enter category: ");
                        Categorize filterCat = Categorize.valueOf(scanner.nextLine().toUpperCase());
                        System.out.println(manager.filterbyCategory(filterCat, null));
                        break;

                    case 8: // Sort tasks
                        manager.sortTasks();
                        System.out.println("Tasks sorted by due date, created time, and priority.");
                        break;

                    case 9: // Highest priority task
                        System.out.println("Highest priority task: " + manager.getHighestPriorityTask());
                        break;

                    case 10: // Save
                        manager.saveTasks();
                        System.out.println("Tasks saved.");
                        break;

                    case 11: // Load
                        System.out.print("Enter filename: ");
                        String filename = scanner.nextLine();
                        manager.loadTasks(filename);
                        System.out.println("Tasks loaded.");
                        break;

                    case 12: // Get all
                        System.out.println(manager.getAllTasks());
                        break;

                    case 0: // Exit
                        running = false;
                        System.out.println("Goodbye, " + username + "!");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (DuplicateTaskException | InvalidTaskNameException | InvalidDateException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}