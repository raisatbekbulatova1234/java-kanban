package managers;

import enums.StatusOfTask;
import enums.TypeOfTasks;
import exceptions.ManagerSaveException;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private static String fileName;
    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
        this.fileName = file.getName();
    }


    public File getFile() {
        return file;
    }


    public String getFileName() {
        return fileName;
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

        TypeOfTasks type = TypeOfTasks.valueOf(str[1]);
        if (type.equals(TypeOfTasks.TASK)) {
            return new Task(str[2], str[3], statusOfTask);
        }
        if (type.equals(TypeOfTasks.EPIC)) {
            return new Epic(str[2], str[3], new ArrayList<>(), statusOfTask);
        }
        if (type.equals(TypeOfTasks.SUBTASK)) {
            return new Subtask(str[2], str[3], 0, statusOfTask);
        } else
            return null;
    }


    static FileBackedTaskManager loadFromFile(File file) throws IOException {

        String path = Files.readString(file.toPath());
        File newFile = new File(path);
        return new FileBackedTaskManager(newFile);
    }


    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, StandardCharsets.UTF_8, true))) {
            if (file.length() == 0) {
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
            System.out.println(e.getMessage());
            throw new ManagerSaveException("!!!!! Ошибка сохранения данных !!!!!!");
        }
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void createNewTask(Task task) {
        super.createNewTask(task);
        save();
    }

    @Override
    public void createNewEpic(Epic epic) {
        super.createNewEpic(epic);
        save();
    }

    @Override
    public void createNewSubtask(Subtask subtask) {
        super.createNewSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }
}
