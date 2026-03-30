package analytics;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import manager.TaskManager;
import model.Categorize;
import model.Priority;
import model.Task;

/**
 * TaskAnalytics provides insights into a user's tasks.
 * It queries TaskManager for the current task list and calculates statistics.
 */
public class TaskAnalytics {
    private final TaskManager manager;

    public TaskAnalytics(TaskManager manager) {
        this.manager = manager;
    }

    // Count completed tasks
    public int countCompletedTasks() {
        return (int) manager.getAllTasks().stream()
                .filter(Task::isCompleted)
                .count();
    }

    // Count overdue tasks
    public int countOverdueTasks() {
        LocalDate today = LocalDate.now();
        return (int) manager.getAllTasks().stream()
                .filter(t -> !t.isCompleted() && t.getDueDate().isBefore(today))
                .count();
    }

    // Count tasks by category
    public Map<Categorize, Long> tasksByCategory() {
        return manager.getAllTasks().stream()
                .collect(Collectors.groupingBy(Task::getCategory, Collectors.counting()));
    }

    // Count tasks by priority
    public Map<Priority, Long> tasksByPriority() {
        return manager.getAllTasks().stream()
                .collect(Collectors.groupingBy(Task::getPriority, Collectors.counting()));
    }

    // Get percentage of tasks completed
    public double completionRate() {
        List<Task> tasks = manager.getAllTasks();
        if (tasks.isEmpty()) return 0.0;
        long completed = tasks.stream().filter(Task::isCompleted).count();
        return (completed * 100.0) / tasks.size();
    }

    // Get next upcoming task (earliest due date not completed)
    public Task nextUpcomingTask() {
        return manager.getAllTasks().stream()
                .filter(t -> !t.isCompleted())
                .sorted((t1, t2) -> t1.getDueDate().compareTo(t2.getDueDate()))
                .findFirst()
                .orElse(null);
    }

    // Get average days until due date (for incomplete tasks)
    public double averageDaysUntilDue() {
        LocalDate today = LocalDate.now();
        List<Task> tasks = manager.getAllTasks().stream()
                .filter(t -> !t.isCompleted())
                .toList();

        if (tasks.isEmpty()) return 0.0;

        double totalDays = tasks.stream()
                .mapToLong(t -> java.time.temporal.ChronoUnit.DAYS.between(today, t.getDueDate()))
                .sum();

        return totalDays / tasks.size();
    }
}