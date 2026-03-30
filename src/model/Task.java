package model;

import java.io.Serializable; //for saving/loading tasks
import java.time.LocalDate;
import java.time.LocalTime;

public class Task implements Serializable {
    private String name;
    private LocalDate dueDate;
    private LocalTime createdTime;
    private boolean completed;
    private Priority priority; //enum for task priority
    private Recurrence recurrence; //enum for task recurrence
    private Categorize category; //enum for task category
    private String customCategory; //for user-defined categories

    private static final long serialVersionUID = 1L; //for serialization
    
    public Task (String name, LocalDate dueDate, Priority priority, Recurrence recurrence, Categorize category, String customCategory) {
        this.name = name;
        this.dueDate = dueDate;
        this.createdTime = LocalTime.now();
        this.completed = false;
        this.priority = priority;
        this.recurrence = recurrence;
        this.category = category;
        this.customCategory = customCategory;
    }

    public Task() {
    }

    //Getters
    public String getName() { return name; }
    public LocalDate getDueDate() {return dueDate;}
    public LocalTime getCreatedTime() {return createdTime;}
    public boolean isCompleted() {return completed;}
    public Priority getPriority() {return priority;}
    public Recurrence getRecurrence() {return recurrence;}
    public Categorize getCategory() {return category;}
    public String getCustomCategory() {return customCategory;}


    //Setters 
    public void setName(String name) { this.name = name; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public void setCreatedTime(LocalTime createdTime) { this.createdTime = createdTime;}
    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public void setRecurrence(Recurrence recurrence) { this.recurrence = recurrence;}
    public void setCategory(Categorize category) { this.category = category; }
    public void setCustomCategory(String customCategory) { this.customCategory = customCategory; }

    public String getStatus() {
        if (completed) return "✔ Completed";
        if (dueDate.isBefore(LocalDate.now())) return "✘ Overdue";
        return "Pending";
    }

    
}