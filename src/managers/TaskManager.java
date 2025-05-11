package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {

    void setCounterOfTasks();

   /* Map<Integer, Epic> getEpicHashMap();

    Map<Integer, Task> getTaskHashMap();

    Map<Integer, Subtask> getSubtaskHashMap();*/

    int getCounterOfTasks();

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    Task getTaskById(int id);

    Task getEpicById(int id);

    Task getSubtaskById(int id);

    void createNewTask(Task task);

    void createNewEpic(Epic epic);

    void createNewSubtask(Subtask subtask);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubtaskById(int id);

    List<Subtask> getAllSubtaskFromEpic(int epicId);

    List<Task> getHistory();
}
