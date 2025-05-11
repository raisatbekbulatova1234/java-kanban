package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import enums.StatusOfTask;
import tasks.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private TaskManager taskManager;
    int testId = 0;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void getHistoryShouldReturnOldTaskAfterUpdate() {
        Task task = new Task("Учеба", "Сдать 5-й проект.", StatusOfTask.DONE);
        //task.setId(++testId);
        taskManager.createNewTask(task);
        taskManager.getTaskById(task.getId());
        Task task1 = new Task("Учеба", "Добавить тесты к проекту", StatusOfTask.IN_PROGRESS);
        task1.setId(task.getId());
        taskManager.updateTask(task1);
        List<Task> tasks = taskManager.getHistory();
        Task oldTask = tasks.getFirst();
        assertEquals(task.getTitle(), oldTask.getTitle(), "В истории не сохранилась старая версия задачи");
        assertEquals(task.getDescription(), oldTask.getDescription(),
                "В истории не сохранилась старая версия задачи");

    }

    @Test
    public void getHistoryShouldReturnOldEpicAfterUpdate() {
        Epic epic = new Epic("Программирование", "Пройти спринт 5", new ArrayList<>(),
                StatusOfTask.NEW);
        taskManager.createNewEpic(epic);
        taskManager.getEpicById(epic.getId());
        Epic epic1 = new Epic("Новый эпик", "Новое описание", new ArrayList<>(),
                StatusOfTask.IN_PROGRESS);
        epic1.setId(epic.getId());
        taskManager.updateEpic(epic1);
        List<Task> epics = taskManager.getHistory();
        Task oldEpic = epics.getFirst();
        assertEquals(epic.getTitle(), oldEpic.getTitle(),
                "В истории не сохранилась старая версия эпика");
        assertEquals(epic.getDescription(), oldEpic.getDescription(),
                "В истории не сохранилась старая версия эпика");
    }

    @Test
    public void getHistoryShouldReturnOldSubtaskAfterUpdate() {
        Epic epic = new Epic("Программирование", "Пройти спринт 5", new ArrayList<>(),
                StatusOfTask.NEW);
        taskManager.createNewEpic(epic);
        Subtask subtask = new Subtask("Проект 4", "Отправить на ревью", epic.getId(),
                StatusOfTask.DONE);
        subtask.setId(++testId);
        taskManager.createNewSubtask(subtask);
        taskManager.getSubtaskById(subtask.getId());
        Subtask subtask1 = new Subtask("Новое название",
                "новое описание", epic.getId(), StatusOfTask.IN_PROGRESS);
        subtask1.setId(subtask.getId());
        List<Task> subtasks = taskManager.getHistory();
        Task oldSubtask = subtasks.getFirst();

        assertEquals(subtask.getTitle(), oldSubtask.getTitle(),
                "В истории не сохранилась старая версия эпика");
        assertEquals(subtask.getDescription(), oldSubtask.getDescription(),
                "В истории не сохранилась старая версия эпика");
    }

    @Test
    public void getHistoryShouldReturnListOf10Tasks() {
        for (int i = 0; i < 20; i++) {
            Task task = new Task("Учеба", STR."Решить задачу №\{i}", StatusOfTask.NEW);
            task.setId(i);
            taskManager.createNewTask(task);
        }

        List<Task> tasks = taskManager.getAllTasks();
        for (Task task : tasks) {
            taskManager.getTaskById(task.getId());
        }

        List<Task> list = taskManager.getHistory();
        assertEquals(10, list.size(), "Неверное количество элементов в истории ");
    }
}
