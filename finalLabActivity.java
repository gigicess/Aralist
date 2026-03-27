import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class finalLabActivity {
        public static ArrayList < String > add(ArrayList < String > a, String theTask) {
            a.add(theTask);
            return a;
        }

        public static ArrayList < String > deleteTask(ArrayList < String > a2, int theTask2) {
            a2.remove(theTask2);
            return a2;
        }

        public static void main(String[] args) {
            ArrayList < String > listsTasks = new ArrayList < > ();
            ArrayList < LocalDate > d = new ArrayList < > ();
            ArrayList < LocalTime > t = new ArrayList < > ();
            ArrayList<Boolean> completed = new ArrayList<>();


            Scanner input = new Scanner(System.in);
            System.out.print("Please Enter Your Name: ");
            String line = input.nextLine();
            System.out.println("Welcome to Aralist " + line + "!");
            System.out.println("Access your Task List");

            while (true) {
                System.out.println("Task List");
                String choice[] = {
                    "(1) Add New Task",
                    "(2) Delete Task",
                    "(3) Sort Automatically By Date and Time",
                    "(4) Search Task",
                    "(5) Check/Complete Tasks",
                    "(6) Exit"
                };
                for (String c: choice) {
                    System.out.println(c);
                }

                System.out.print("Choose option: ");
                int user = input.nextInt();
                input.nextLine();

                switch (user) {
                    case 1:               
                        System.out.print("Enter The Task (or type 'done' to exit): ");
                        String task = input.nextLine().trim(); // trim spaces

                        if (task.equalsIgnoreCase("done") || task.equalsIgnoreCase("exit")) {
                            break;
                        } else {
                            try {
                            System.out.print("Enter Due Date Ex.(2025-11-08): ");
                            String date = input.nextLine().trim();
                            LocalDate dueDate = LocalDate.parse(date);
                            LocalTime createdTime = LocalTime.now();
                            LocalDate dateNow = LocalDate.now();

                            int result = dueDate.compareTo(dateNow);                               

                            if (result > 0 || result == 0) {
                                t.add(createdTime);
                                d.add(dueDate);
                                listsTasks.add(task);
                                System.out.println("\n--- Current Task List ---");
                                for (int i = 0; i < listsTasks.size(); i++) {
                                    System.out.println((i + 1) + ". Task: " + listsTasks.get(i) +
                                        "\t Due Date: " + d.get(i) +
                                        "\t Task Created: " + t.get(i));
                                }
                            } else {
                                System.out.println("Invalid Date");
                            }
                            } catch (DateTimeParseException e) {
                                System.out.println("Invalid Format");
                            }
                        }
                        break;
                    case 2:
                        try {
                            System.out.print("Enter The Task Number You Want to Delete: ");
                            int c = input.nextInt() - 1;
                            deleteTask(listsTasks, c);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Format");
                        }
                        
                        break;
                    case 3:
                        if (listsTasks.size() == 0) { /// IF TASK IS 0
                            System.out.println("No tasks to sort!");
                            break;
                        }
                        // Convert ArrayLists to arrays
                        String[] tasksArray = listsTasks.toArray(new String[0]);
                        LocalDate[] dueArray = d.toArray(new LocalDate[0]);
                        LocalTime[] timeArray = t.toArray(new LocalTime[0]);
                    
                        for (int i = 0; i < tasksArray.length - 1; i++) { // sort tasks by due date and by creation time
                            for (int j = 0; j < tasksArray.length - i - 1; j++) {
                                boolean swap = false; // Switch handling 

                                if (dueArray[j].isAfter(dueArray[j + 1])) { // Compare due dates
                                    swap = true;
                                } else if (dueArray[j].isEqual(dueArray[j + 1])) {
                                    // If due dates are equal, compare creation times
                                    if (timeArray[j].isAfter(timeArray[j + 1])) {
                                        swap = true;
                                    }
                                }

                                if (swap) {
                                    String tempTask = tasksArray[j]; // Swap tasks
                                    tasksArray[j] = tasksArray[j + 1];
                                    tasksArray[j + 1] = tempTask;


                                    LocalDate tempDate = dueArray[j]; // Swap due dates
                                    dueArray[j] = dueArray[j + 1];
                                    dueArray[j + 1] = tempDate;

                                    // Swap creation times
                                    LocalTime tempTime = timeArray[j]; // Swap creation times
                                    timeArray[j] = timeArray[j + 1];
                                    timeArray[j + 1] = tempTime;
                                }
                            }
                        }
                        
                        listsTasks.clear();
                        d.clear();
                        t.clear();

                        for (int i = 0; i < tasksArray.length; i++) {
                            listsTasks.add(tasksArray[i]);
                            d.add(dueArray[i]);
                            t.add(timeArray[i]);
                        }

                        // Display sorted tasks
                        System.out.println("\n--- Sorted Task List ---");
                        for (int i = 0; i < tasksArray.length; i++) {
                            System.out.println((i + 1) + ". Task: " + tasksArray[i] +
                                "\t Due Date: " + dueArray[i] +
                                "\t Task Created: " + timeArray[i]);
                        }
                        break;
                    case 4:
                        if (listsTasks.isEmpty()) {
                            System.out.println("\nNo Tasks\n");
                        } else {
                            boolean found = false; //to check

                            System.out.print("Enter the task to search: ");
                            String searchTask = input.next().trim();

                            for (int i = 0; i < listsTasks.size(); i++) { //to check each item in the list
                                if (listsTasks.get(i).toLowerCase().contains(searchTask.toLowerCase())) {
                                    System.out.println("\n--- Task Found: ---\n");
                                    System.out.println("Task: " + listsTasks.get(i) +
                                        "\t Due Date: " + d.get(i) +
                                        "\t Task Created: " + t.get(i));
                                    found = true;
                                }
                            }

                            if (!found) {
                                System.out.println("\nTask not found: " + searchTask + "\n");
                            }
                        }
                        break;
                    case 5:
                        if (listsTasks.isEmpty()) {
                            System.out.println("\nNo tasks available.\n");
                            break;
                        }

                        LocalDate today = LocalDate.now();
                        boolean hasOverdue = false;

                        System.out.println("\n--- Overdue Tasks ---");
                        for (int i = 0; i < listsTasks.size(); i++) {
                            if (d.get(i).isBefore(today) && !completed.get(i)) {
                                System.out.println((i + 1) + ". Task: " + listsTasks.get(i) +
                                                   "\t Due Date: " + d.get(i) +
                                                   "\t Task Created: " + t.get(i) +
                                                   "\t Status: [ ] Overdue");
                                hasOverdue = true;
                            }
                        }
                        if (!hasOverdue) {
                            System.out.println("No overdue tasks.");
                        }

                        System.out.println("\n--- All Tasks ---");
                        for (int i = 0; i < listsTasks.size(); i++) {
                            System.out.println((i + 1) + ". Task: " + listsTasks.get(i) +
                                               "\t Due Date: " + d.get(i) +
                                               "\t Task Created: " + t.get(i));
                        }

                        System.out.print("\nEnter the task number to delete (or done to cancel): ");
                        String in = input.nextLine().trim();
                        int taskNum;

                        try {
                            taskNum = Integer.parseInt(in) - 1;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            break;
                        }

                        if (taskNum == -1) {
                            System.out.println("Cancelled.");
                            break;
                        }

                        if (taskNum < 0 || taskNum >= listsTasks.size()) {
                            System.out.println("Invalid task number.");
                            break;
                        }

                        System.out.println("Deleting task: " + listsTasks.get(taskNum));
                        listsTasks.remove(taskNum);
                        d.remove(taskNum);
                        t.remove(taskNum);

                        System.out.println("\n--- Updated Task List ---");
                        for (int i = 0; i < listsTasks.size(); i++) {
                            System.out.println((i + 1) + ". Task: " + listsTasks.get(i) +
                                               "\t Due Date: " + d.get(i) +
                                               "\t Task Created: " + t.get(i));
                        }
                        break;
                    default:
                        System.out.println("Goodbye " + line + "!");
                        return;
                }
            } 
        }
}