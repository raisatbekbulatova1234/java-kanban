package managers;

import enums.StatusOfTask;
import exceptions.ManagerSaveException;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileBackedTaskManager extends InMemoryTaskManager {

    String fileName;

    public FileBackedTaskManager(String fileName) {
        this.fileName = fileName;
    }

    private boolean fileIsEmptyCheck(String file) throws IOException {
        FileReader reader = new FileReader(file);
        boolean isEmpty = false;
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            if (bufferedReader.readLine() == null) {
                isEmpty = true;
            }
        }
        return isEmpty;
    }

    private String taskToString(Task task) {
        if (task instanceof Subtask) {
            return String.format("%s,%s,%s,%s,%s,%s", task.getId(), task.getTypeOfTasks(), task.getTitle(),
                    task.getDescription(), task.getStatusOfTask(), ((Subtask) task).getEpicId());
        } else {
            return String.format("%s,%s,%s,%s,%s", task.getId(), task.getTypeOfTasks(), task.getTitle(),
                    task.getDescription(), task.getStatusOfTask());

        }
    }

    private Task taskFromString(String value) {

        String[] str = value.split(",");
        String status = str[4].toUpperCase();
        StatusOfTask statusOfTask = switch (status) {
            case "NEW" -> StatusOfTask.NEW;
            case "IN_PROGRESS" -> StatusOfTask.IN_PROGRESS;
            case "DONE" -> StatusOfTask.DONE;
            default -> null;
        };
        return new Task(str[2], str[3], statusOfTask);
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, StandardCharsets.UTF_8, true))) {
            if (fileIsEmptyCheck(fileName)) {
                String s = "id,type,name,status,description,epic";
                writer.write(s + "\n");
            }
            for (Task task : getAllTasks()) {
                writer.write(taskToString(task) + "\n");
            }
            for (Epic epic : getAllEpics()) {
                writer.write(taskToString(epic) + "\n");
            }
            for (Subtask subtask : getAllSubtasks()) {
                writer.write(taskToString(subtask) + "\n");
            }

        } catch (IOException e) {
            throw new ManagerSaveException("!!!!! Ошибка сохранения данных !!!!!!");
        }
    }

    @Override
    public void deleteAllTasks() throws IOException {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() throws IOException {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() throws IOException {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void createNewTask(Task task) throws IOException {
        super.createNewTask(task);
        save();
    }

    @Override
    public void createNewEpic(Epic epic) throws IOException {
        super.createNewEpic(epic);
        save();
    }

    @Override
    public void createNewSubtask(Subtask subtask) throws IOException {
        super.createNewSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task task) throws IOException {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) throws IOException {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) throws IOException {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteTaskById(int id) throws IOException {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) throws IOException {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) throws IOException {
        super.deleteSubtaskById(id);
        save();
    }
}
