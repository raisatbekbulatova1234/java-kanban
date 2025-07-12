package managers;

import enums.StatusOfTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryHistoryManagerTest {

    private TaskManager taskManager;
    private HistoryManager historyManager;
    private int id = 0;

    public int generateId() {
        return ++id;
    }

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    public void getHistoryShouldReturnOldTaskAfterUpdate() throws IOException {
        Task task = new Task("Учеба", "Сдать 5-й проект.", StatusOfTask.DONE);
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofMinutes(10000));
        taskManager.createNewTask(task);
        taskManager.getTaskById(task.getId());
        Task task1 = new Task("Учеба", "Добавить тесты к проекту", StatusOfTask.IN_PROGRESS);
        task1.setId(task.getId());
        task1.setStartTime(task.getStartTime());
        task1.setDuration(task.getDuration());
        taskManager.updateTask(task1);
        List<Task> tasks = taskManager.getHistory();
        Task oldTask = tasks.getFirst();
        assertEquals(task.getTitle(), oldTask.getTitle(), "В истории не сохранилась старая версия задачи");
        assertEquals(task.getDescription(), oldTask.getDescription(), "В истории не сохранилась старая версия задачи");

    }

    @Test
    public void getHistoryShouldReturnOldEpicAfterUpdate() throws IOException {
        Epic epic = new Epic("Программирование", "Пройти спринт 5", new ArrayList<>(), StatusOfTask.NEW);
        epic.setStartTime(LocalDateTime.now());
        epic.setDuration(Duration.ofMinutes(10000));
        taskManager.createNewEpic(epic);
        taskManager.getEpicById(epic.getId());
        Epic epic1 = new Epic("Новый эпик", "Новое описание", new ArrayList<>(), StatusOfTask.IN_PROGRESS);
        epic1.setId(epic.getId());
        taskManager.updateEpic(epic1);
        List<Task> epics = taskManager.getHistory();
        Task oldEpic = epics.getFirst();
        assertEquals(epic.getTitle(), oldEpic.getTitle(), "В истории не сохранилась старая версия эпика");
        assertEquals(epic.getDescription(), oldEpic.getDescription(), "В истории не сохранилась старая версия эпика");
    }

    @Test
    public void getHistoryShouldReturnOldSubtaskAfterUpdate() throws IOException {
        Epic epic = new Epic("Программирование", "Пройти спринт 5", new ArrayList<>(), StatusOfTask.NEW);
        epic.setStartTime(LocalDateTime.now());
        epic.setDuration(Duration.ofMinutes(10000));
        taskManager.createNewEpic(epic);
        Subtask subtask = new Subtask("Проект 4", "Отправить на ревью", 0, StatusOfTask.DONE);
        subtask.setStartTime(LocalDateTime.now());
        subtask.setDuration(Duration.ofMinutes(10000));
        taskManager.createNewSubtask(subtask);
        taskManager.getSubtaskById(1);
        Subtask subtask1 = new Subtask("Новое название", "новое описание", epic.getId(), StatusOfTask.IN_PROGRESS);
        subtask1.setId(1);
        subtask1.setStartTime(subtask.getStartTime());
        subtask1.setDuration(subtask.getDuration());
        taskManager.updateSubtask(subtask1);
        List<Task> subtasks = taskManager.getHistory();
        Task oldSubtask = subtasks.getFirst();

        assertEquals(subtask.getTitle(), oldSubtask.getTitle(), "В истории не сохранилась старая версия эпика");
        assertEquals(subtask.getDescription(), oldSubtask.getDescription(), "В истории не сохранилась старая версия эпика");
    }

    @Test
    public void getHistoryShouldReturnListOf10Tasks() throws IOException {
        for (int i = 0; i < 20; i++) {
            Task task = new Task("Учеба", "Решить задачу № " + i, StatusOfTask.NEW);
            task.setStartTime(LocalDateTime.now().plusMonths(i));
            task.setDuration(Duration.ofMinutes(10000));
            taskManager.createNewTask(task);
        }

        List<Task> tasks = taskManager.getAllTasks();
        for (Task task : tasks) {
            taskManager.getTaskById(task.getId());
        }

        List<Task> list = taskManager.getHistory();
        assertEquals(20, list.size(), "Неверное количество элементов в истории ");
    }

    @Test
    public void shouldAddTasksToHistory() {
        Task task1 = new Task("Учеба", "Решить задачу", StatusOfTask.NEW, LocalDateTime.now().plusMonths(5), Duration.ofMinutes(10000));
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        Task task2 = new Task("Учеба", "Сдать проект", StatusOfTask.NEW, LocalDateTime.now().plusMonths(5), Duration.ofMinutes(10000));
        int newTaskId2 = generateId();
        task2.setId(newTaskId2);
        Task task3 = new Task("Учеба", "Получить диплом", StatusOfTask.NEW, LocalDateTime.now().plusMonths(14), Duration.ofMinutes(10000));
        int newTaskId3 = generateId();
        task3.setId(newTaskId3);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        assertEquals(List.of(task1, task2, task3), historyManager.getHistory());
    }

    @Test
    public void shouldRemoveTask() {
        Task task1 = new Task("Учеба", "Решить задачу", StatusOfTask.NEW, LocalDateTime.now().plusMonths(5), Duration.ofMinutes(10000));
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        Task task2 = new Task("Учеба", "Сдать проект", StatusOfTask.NEW, LocalDateTime.now().plusMonths(5), Duration.ofMinutes(10000));
        int newTaskId2 = generateId();
        task2.setId(newTaskId2);
        Task task3 = new Task("Учеба", "Получить диплом", StatusOfTask.NEW, LocalDateTime.now().plusMonths(14), Duration.ofMinutes(10000));
        int newTaskId3 = generateId();
        task3.setId(newTaskId3);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task2.getId());
        assertEquals(List.of(task1, task3), historyManager.getHistory());
    }

    @Test
    public void shouldRemoveOnlyOneTask() {
        Task task1 = new Task("Учеба", "Решить задачу", StatusOfTask.NEW, LocalDateTime.now().plusMonths(5), Duration.ofMinutes(10000));
        int newTaskId = generateId();
        task1.setId(newTaskId);
        historyManager.add(task1);
        historyManager.remove(task1.getId());
        assertEquals(Collections.EMPTY_LIST, historyManager.getHistory());
    }

    @Test
    public void shouldHistoryIsEmpty() {
        Task task1 = new Task("Учеба", "Решить задачу", StatusOfTask.NEW, LocalDateTime.now().plusMonths(5), Duration.ofMinutes(10000));
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        Task task2 = new Task("Учеба", "Сдать проект", StatusOfTask.NEW, LocalDateTime.now().plusMonths(5), Duration.ofMinutes(10000));
        int newTaskId2 = generateId();
        task2.setId(newTaskId2);
        Task task3 = new Task("Учеба", "Получить диплом", StatusOfTask.NEW, LocalDateTime.now().plusMonths(14), Duration.ofMinutes(10000));
        int newTaskId3 = generateId();
        task3.setId(newTaskId3);
        historyManager.remove(task1.getId());
        historyManager.remove(task2.getId());
        historyManager.remove(task3.getId());
        assertEquals(Collections.EMPTY_LIST, historyManager.getHistory());
    }

    @Test
    public void shouldNotRemoveTaskWithBadId() {
        Task task1 = new Task("Учеба", "Решить задачу", StatusOfTask.NEW, LocalDateTime.now().plusMonths(5), Duration.ofMinutes(10000));
        int newTaskId = generateId();
        task1.setId(newTaskId);
        historyManager.add(task1);
        historyManager.remove(0);
        assertEquals(List.of(task1), historyManager.getHistory());
    }

}