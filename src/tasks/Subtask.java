package tasks;

import enums.StatusOfTask;
import enums.TypeOfTasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {

    private int epicId;


    public Subtask(String title, String description, int epicId, StatusOfTask status) {
        super(title, description, status);
        this.epicId = epicId;
    }


    public Subtask(String title, String description, StatusOfTask status) {
        super(title, description, status);
    }

    public Subtask(String title, String description, StatusOfTask statusOfTask, LocalDateTime startTime, Duration duration, LocalDateTime endTime) {
        super(title, description, statusOfTask, startTime, duration, endTime);
    }

    @Override
    public int getId() {
        if (super.getId() == epicId) {
            System.out.println("Ошибка! Epic нельзя добавить в самого себя в виде подзадачи!!!");
            return -1;
        } else {
            return super.getId();
        }
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Подзадача { " +
                " id = " + super.getId() +
                " название = " + super.getTitle() +
                ", описание = " + super.getDescription() +
                ", статус подзадачи = " + super.getStatusOfTask() +
                '}';
    }

    @Override
    public TypeOfTasks getTypeOfTasks() {
        return TypeOfTasks.SUBTASK;
    }


}
