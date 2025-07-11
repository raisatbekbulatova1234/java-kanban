package tasks;

import enums.StatusOfTask;
import enums.TypeOfTasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Epic extends Task {

    private List<Integer> listSubtask;


    public Epic(String title, String description, List<Integer> subtaskId, StatusOfTask status) {
        super(title, description, status);
        listSubtask = subtaskId;
    }


    public Epic(String title, String description, StatusOfTask status) {
        super(title, description, status);
    }

    public Epic(String title, String description, StatusOfTask statusOfTask, LocalDateTime startTime, Duration duration) {
        super(title, description, statusOfTask, startTime, duration);
    }

    public List<Integer> getListSubtask() {
        return listSubtask;
    }

    public void setListSubtask(List<Integer> listSubtask) {
        this.listSubtask = listSubtask;
    }

    @Override
    public TypeOfTasks getTypeOfTasks() {
        return TypeOfTasks.EPIC;
    }

}