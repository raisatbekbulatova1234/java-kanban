package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.util.List;

public interface TaskManager {

    int getCounterOfTasks();

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    void deleteAllTasks() throws IOException;

    void deleteAllEpics() throws IOException;

    void deleteAllSubtasks() throws IOException;

    Task getTaskById(int id);

    Task getEpicById(int id);

    Task getSubtaskById(int id);

    void createNewTask(Task task) throws IOException;

    void createNewEpic(Epic epic) throws IOException;

    void createNewSubtask(Subtask subtask) throws IOException;

    void updateTask(Task task) throws IOException;

    void updateEpic(Epic epic) throws IOException;

    void updateSubtask(Subtask subtask) throws IOException;

    void deleteTaskById(int id) throws IOException;

    void deleteEpicById(int id) throws IOException;

    void deleteSubtaskById(int id) throws IOException;

    List<Subtask> getAllSubtaskFromEpic(int epicId) throws IOException;

    List<Task> getHistory();
}
