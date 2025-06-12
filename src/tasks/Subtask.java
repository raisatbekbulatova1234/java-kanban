package tasks;

import enums.StatusOfTask;

public class Subtask extends Task {

    private int epicId;

    public Subtask(String title, String description, int epicId, StatusOfTask status) {
        super(title, description, status);
        this.epicId = epicId;
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
}
