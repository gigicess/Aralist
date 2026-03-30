package analytics;

import model.Task;
import model.Priority;
import model.Categorize;
import model.Recurrence;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TaskAnalytics {

    // Count all tasks
    public static int countTasks(List<Task> tasks) {
        return tasks.size();
    }

    // Count overdue tasks
    public static long countOverdueTasks(List<Task> tasks) {
        LocalDate today = LocalDate.now();
        return tasks.stream()
                .filter(t -> t.getDueDate() != null && t.getDueDate().isBefore(today))
                .count();
    }

    // Get tasks by priority
    public static List<Task> filterByPriority(List<Task> tasks, Priority priority) {
        return tasks.stream()
                .filter(t -> t.getPriority() == priority)
                .collect(Collectors.toList());
    }

    // Get tasks by category
    public static List<Task> filterByCategory(List<Task> tasks, Categorize category) {
        return tasks.stream()
                .filter(t -> t.getCategory() == category)
                .collect(Collectors.toList());
    }

    // Count recurring tasks
    public static long countRecurringTasks(List<Task> tasks) {
        return tasks.stream()
                .filter(t -> t.getRecurrence() != Recurrence.NONE)
                .count();
    }
}