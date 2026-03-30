package main;

import java.time.LocalDate;
import java.util.Scanner;
import manager.*;
import model.*;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask for username
        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();

        TaskManager manager = new TaskManager(username);
        System.out.println("Welcome, " + username + "!");

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
            System.out.println("8. Sort Tasks");
            System.out.println("9. Sort Tasks by Priority");
            System.out.println("10. Save Tasks");
            System.out.println("11. Load Tasks");
            System.out.println("12. Get All Tasks");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1: // Add
                        System.out.print("Task name: ");
                        String name = scanner.nextLine();

                        System.out.print("Due date (yyyy-MM-dd): ");
                        LocalDate dueDate = LocalDate.parse(scanner.nextLine());

                        System.out.print("Priority (HIGH, MEDIUM, LOW): ");
                        Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());

                        System.out.print("Category (WORK, PERSONAL, STUDY, OTHER): ");
                        Categorize category = Categorize.valueOf(scanner.nextLine().toUpperCase());

                        Task task = new Task(name, dueDate, priority,
                                             Recurrence.NONE, category, null);
                        manager.addTask(task);
                        System.out.println("Task added!");
                        break;

                    case 2: // Delete Task
                        System.out.print("Enter deletion method (1: by index, 2: by name, 3: delete all): ");
                        int method = Integer.parseInt(scanner.nextLine());
                        if (method == 1) {
                            System.out.print("Enter index: ");
                            int index = Integer.parseInt(scanner.nextLine());
                            manager.deleteTaskIndex(index);
                        } else if (method == 2) {
                            System.out.print("Enter task name: ");
                            String taskName = scanner.nextLine();
                            manager.deleteTaskName(taskName);
                        } else if (method == 3) {
                            manager.deleteAllTasks();
                        }
                        System.out.println("Task deleted.");
                        break;

                    case 3: // Edit
                        System.out.print("Enter index to edit: ");
                        int editIndex = Integer.parseInt(scanner.nextLine());
                        System.out.print("New name: ");
                        String newName = scanner.nextLine();
                        System.out.print("New due date (yyyy-MM-dd): ");
                        LocalDate newDate = LocalDate.parse(scanner.nextLine());
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
                        LocalDate filterDate = LocalDate.parse(scanner.nextLine());
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

                    case 9: // Sort by priority
                        manager.getHighestPriorityTask();
                        System.out.println("Tasks sorted by priority.");
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
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}

//Sample main class 