package managers;

import enums.StatusOfTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T manager;
    
    protected Task createTask() {
        return new Task("Title", "Description", StatusOfTask.NEW, LocalDateTime.now(), Duration.ofMinutes(10000));
    }
    protected Epic createEpic() {

        return new Epic("Title", "Description", StatusOfTask.NEW, LocalDateTime.now().plusMonths(5), Duration.ofMinutes(20000));
    }
    protected Subtask createSubtask(Epic epic) {
        return new Subtask("Title", "Description", StatusOfTask.NEW, LocalDateTime.now().plusDays(13), Duration.ofMinutes(30000));
    }

    @Test
    void getCounterOfTasks() {
    }

    @Test
    void getAllTasks() {
    }

    @Test
    void getAllEpics() {
    }

    @Test
    void getAllSubtasks() {
    }

    @Test
    void deleteAllTasks() {
    }

    @Test
    void deleteAllEpics() {
    }

    @Test
    void deleteAllSubtasks() {
    }

    @Test
    void getTaskById() {
    }

    @Test
    void getEpicById() {
    }

    @Test
    void getSubtaskById() {
    }

    @Test
    void createNewTask() {
    }

    @Test
    void createNewEpic() {
    }

    @Test
    void createNewSubtask() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void updateEpic() {
    }

    @Test
    void updateSubtask() {
    }

    @Test
    void deleteTaskById() {
    }

    @Test
    void deleteEpicById() {
    }

    @Test
    void deleteSubtaskById() {
    }

    @Test
    void getAllSubtaskFromEpic() {
    }

    @Test
    void getHistory() {
    }

    @Test
    void getDuration() {
    }

    @Test
    void getStartTime() {
    }

    @Test
    void getEndTime() {
    }
}