package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;
    protected abstract T createManager();

    @BeforeEach
    public void addDifferentTaskManager() {
        taskManager = createManager();
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
