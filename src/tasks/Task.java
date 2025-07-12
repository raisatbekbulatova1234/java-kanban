package tasks;

import enums.StatusOfTask;
import enums.TypeOfTasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task> {

    private String title;
    private String description;
    private int id;
    private StatusOfTask statusOfTask;
    private LocalDateTime endTime;

    private Duration duration;
    private LocalDateTime startTime;

    public LocalDateTime getEndTime() {
        return endTime = startTime.plus(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Task(String title, String description, StatusOfTask status) {
        this.title = title;
        this.description = description;
        statusOfTask = status;
    }

    public Task(String title, String description, StatusOfTask statusOfTask, LocalDateTime startTime, Duration duration) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;
        this.statusOfTask = statusOfTask;
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
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s", this.getId(), this.getTypeOfTasks(), this.getTitle(),
                this.getDescription(), this.getStatusOfTask(), this.getStartTime(), this.getDuration(), this.getEndTime());

    }

    public TypeOfTasks getTypeOfTasks() {
        return TypeOfTasks.TASK;
    }

    @Override
    public int compareTo(Task other) {
        return this.startTime.compareTo(other.startTime);
    }
}
