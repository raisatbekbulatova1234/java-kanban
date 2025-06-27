package tasks;

import enums.StatusOfTask;
import enums.TypeOfTasks;

import java.util.Objects;

public class Task {

    private String title;
    private String description;
    private int id;
    private StatusOfTask statusOfTask;
    private TypeOfTasks typeOfTasks = TypeOfTasks.TASK;

    public Task(String title, String description, StatusOfTask status) {
        this.title = title;
        this.description = description;
        statusOfTask = status;
    }
    public Task(String title, String description) {
        this.title = title;
        this.description = description;

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public StatusOfTask getStatusOfTask() {
        return statusOfTask;
    }

    public void setStatusOfTask(StatusOfTask statusOfTask) {
        this.statusOfTask = statusOfTask;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, id);
    }

    @Override
    public String toString() {
        return "Задача { " +
                "название = " + title +
                ", описание = '" + description +
                ", id = " + id +
                ", статус задачи = " + statusOfTask +
                '}';
    }

    public TypeOfTasks getTypeOfTasks() {
        return typeOfTasks;
    }

    private void setTypeOfTasks(TypeOfTasks typeOfTasks) {
        this.typeOfTasks = typeOfTasks;
    }
}
