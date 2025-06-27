package tasks;

import enums.StatusOfTask;
import enums.TypeOfTasks;

public class Subtask extends Task {

    private int epicId;
    private TypeOfTasks typeOfTasks = TypeOfTasks.SUBTASK;


    public Subtask(String title, String description, int epicId, StatusOfTask status) {
        super(title, description, status);
        this.epicId = epicId;
    }
    public Subtask(String title, String description, StatusOfTask status) {
        super(title, description, status);
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

    public TypeOfTasks getTypeOfTasks() {
        return typeOfTasks;
    }

    private void setTypeOfTasks(TypeOfTasks typeOfTasks) {
        this.typeOfTasks = typeOfTasks;
    }
}
