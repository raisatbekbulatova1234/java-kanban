package tasks;

import enums.StatusOfTask;
import enums.TypeOfTasks;

import java.util.List;

public class Epic extends Task {

    private List<Integer> listSubtask;
    private TypeOfTasks typeOfTasks = TypeOfTasks.TASK;


    public Epic(String title, String description, List<Integer> subtaskId, StatusOfTask status) {
        super(title, description, status);
        listSubtask = subtaskId;
    }


    public Epic(String title, String description, StatusOfTask status) {
        super(title, description, status);
    }

    public List<Integer> getListSubtask() {
        return listSubtask;
    }

    @Override
    public String toString() {
        return "Эпик { " +
                "название = " + super.getTitle() +
                ", " + "описание = " + super.getDescription() +
                ", " + "id = " + super.getId() + ", " + '\n' +
                "количество подзадач = " + listSubtask.size() +
                ", статус эпика = " + super.getStatusOfTask() +
                '}';
    }

    @Override
    public TypeOfTasks getTypeOfTasks() {
        return typeOfTasks;
    }

    private void setTypeOfTasks(TypeOfTasks typeOfTasks) {
        this.typeOfTasks = typeOfTasks;
    }
}