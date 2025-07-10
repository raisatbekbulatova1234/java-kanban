package managers;

import enums.StatusOfTask;
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

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {


    protected T manager;

    protected Task createTask() {
        return new Task("Учеба", "Решить задачу",
                StatusOfTask.NEW, LocalDateTime.now().plusMonths(5), Duration.ofMinutes(10000));
    }

    protected Epic createEpic() {
        return new Epic("Программирование", "Пройти спринт 5",
                StatusOfTask.NEW, LocalDateTime.now().plusMonths(2), Duration.ofMinutes(20000));
    }

    protected Subtask createSubtask() {
        return new Subtask("Почитать про тестирование", "глубже изучить логику тестирования",
                StatusOfTask.NEW, LocalDateTime.now().plusMonths(14), Duration.ofMinutes(20000));
    }

    @Test
    public void shouldCreateTask() throws IOException {
        Task task = createTask();
        manager.createNewTask(task);
        List<Task> tasks = manager.getAllTasks();
        assertNotNull(task.getStatusOfTask());
        assertEquals(StatusOfTask.NEW, task.getStatusOfTask());
        assertEquals(List.of(task), tasks);
    }

    @Test
    public void shouldCreateEpic() throws IOException {
        Epic epic = createEpic();
        epic.setListSubtask(new ArrayList<>());
        manager.createNewEpic(epic);
        List<Epic> epics = manager.getAllEpics();
        assertNotNull(epic.getStatusOfTask());
        assertEquals(StatusOfTask.NEW, epic.getStatusOfTask());
        assertEquals(Collections.EMPTY_LIST, epic.getListSubtask());
        assertEquals(List.of(epic), epics);
    }

    @Test
    public void shouldCreateSubtask() throws IOException {
        Epic epic = createEpic();
        epic.setListSubtask(new ArrayList<>());
        manager.createNewEpic(epic);
        Subtask subtask = createSubtask();
        manager.createNewSubtask(subtask);
        List<Subtask> subtasks = manager.getAllSubtasks();
        assertNotNull(subtask.getStatusOfTask());
        assertEquals(epic.getId(), subtask.getEpicId());
        assertEquals(StatusOfTask.NEW, subtask.getStatusOfTask());
        assertEquals(List.of(subtask), subtasks);
        assertEquals(List.of(subtask.getId()), epic.getListSubtask());
    }

    @Test
    public void shouldUpdateTaskStatusToInProgress() throws IOException {
        Task task = createTask();
        manager.createNewTask(task);
        task.setStatusOfTask(StatusOfTask.IN_PROGRESS);
        manager.updateTask(task);
        assertEquals(StatusOfTask.IN_PROGRESS, manager.getTaskById(task.getId()).getStatusOfTask());
    }

    @Test
    public void shouldUpdateEpicStatusToInProgress() throws IOException {
        Epic epic = createEpic();
        manager.createNewEpic(epic);
        epic.setStatusOfTask(StatusOfTask.IN_PROGRESS);
        assertEquals(StatusOfTask.IN_PROGRESS, manager.getEpicById(epic.getId()).getStatusOfTask());
    }

    @Test
    public void shouldUpdateSubtaskStatusToInProgress() throws IOException {
        Epic epic = createEpic();
        epic.setListSubtask(new ArrayList<>());
        manager.createNewEpic(epic);
        Subtask subtask = createSubtask();
        manager.createNewSubtask(subtask);
        subtask.setStatusOfTask(StatusOfTask.IN_PROGRESS);
        manager.updateSubtask(subtask);

        assertEquals(StatusOfTask.IN_PROGRESS, manager.getSubtaskById(subtask.getId()).getStatusOfTask());
        assertEquals(StatusOfTask.IN_PROGRESS, manager.getEpicById(epic.getId()).getStatusOfTask());
    }

    @Test
    public void shouldUpdateTaskStatusToInDone() throws IOException {
        Task task = createTask();
        manager.createNewTask(task);
        task.setStatusOfTask(StatusOfTask.DONE);
        manager.updateTask(task);
        assertEquals(StatusOfTask.DONE, manager.getTaskById(task.getId()).getStatusOfTask());
    }

    @Test
    public void shouldUpdateEpicStatusToInDone() throws IOException {
        Epic epic = createEpic();
        manager.createNewEpic(epic);
        epic.setStatusOfTask(StatusOfTask.DONE);
        assertEquals(StatusOfTask.DONE, manager.getEpicById(epic.getId()).getStatusOfTask());
    }

    @Test
    public void shouldUpdateSubtaskStatusToInDone() throws IOException {
        Epic epic = createEpic();
        epic.setListSubtask(new ArrayList<>());
        manager.createNewEpic(epic);
        Subtask subtask = createSubtask();
        manager.createNewSubtask(subtask);
        subtask.setStatusOfTask(StatusOfTask.DONE);
        manager.updateSubtask(subtask);
        assertEquals(StatusOfTask.DONE, manager.getSubtaskById(subtask.getId()).getStatusOfTask());
        assertEquals(StatusOfTask.DONE, manager.getEpicById(epic.getId()).getStatusOfTask());
    }

    @Test
    public void shouldNotUpdateTaskIfNull() throws IOException {
        Task task = createTask();
        manager.createNewTask(task);
        manager.updateTask(null);
        assertEquals(task, manager.getTaskById(task.getId()));
    }

    @Test
    public void shouldNotUpdateEpicIfNull() throws IOException {
        Epic epic = createEpic();
        manager.createNewEpic(epic);
        manager.updateEpic(null);
        assertEquals(epic, manager.getEpicById(epic.getId()));
    }

    @Test
    public void shouldNotUpdateSubtaskIfNull() throws IOException {
        Epic epic = createEpic();
        epic.setListSubtask(new ArrayList<>());
        manager.createNewEpic(epic);
        Subtask subtask = createSubtask();
        manager.createNewSubtask(subtask);
        manager.updateSubtask(null);
        assertEquals(subtask, manager.getSubtaskById(subtask.getId()));
    }

    @Test
    public void shouldDeleteAllTasks() throws IOException {
        Task task = createTask();
        manager.createNewTask(task);
        manager.deleteAllTasks();
        assertEquals(Collections.EMPTY_LIST, manager.getAllTasks());
    }

    @Test
    public void shouldDeleteAllEpics() throws IOException {
        Epic epic = createEpic();
        manager.createNewEpic(epic);
        manager.deleteAllEpics();
        assertEquals(Collections.EMPTY_LIST, manager.getAllEpics());
    }

    @Test
    public void shouldDeleteAllSubtasks() throws IOException {
        Epic epic = createEpic();
        epic.setListSubtask(new ArrayList<>());
        manager.createNewEpic(epic);
        Subtask subtask = createSubtask();
        manager.createNewSubtask(subtask);
        manager.deleteAllSubtasks();
        assertTrue(epic.getListSubtask().isEmpty());
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    public void shouldDeleteAllSubtasksByEpic() throws IOException {
        Epic epic = createEpic();
        epic.setListSubtask(new ArrayList<>());
        manager.createNewEpic(epic);
        Subtask subtask = createSubtask();
        manager.createNewSubtask(subtask);
        manager.deleteAllSubtasksByEpic(epic);

        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    public void shouldDeleteTaskById() throws IOException {
        Task task = createTask();
        manager.createNewTask(task);
        manager.deleteTaskById(task.getId());
        assertEquals(Collections.EMPTY_LIST, manager.getAllTasks());
    }

    @Test
    public void shouldDeleteEpicById() throws IOException {
        Epic epic = createEpic();
        manager.createNewEpic(epic);
        manager.deleteEpicById(epic.getId());
        assertEquals(Collections.EMPTY_LIST, manager.getAllEpics());
    }

    @Test
    public void shouldNotDeleteTaskIfBadId() throws IOException {
        Task task = createTask();
        manager.createNewTask(task);
        manager.deleteTaskById(999);
        assertEquals(List.of(task), manager.getAllTasks());
    }

    @Test
    public void shouldNotDeleteEpicIfBadId() throws IOException {
        Epic epic = createEpic();
        manager.createNewEpic(epic);
        manager.deleteEpicById(999);
        assertEquals(List.of(epic), manager.getAllEpics());
    }

    @Test
    public void shouldNotDeleteSubtaskIfBadId() throws IOException {
        Epic epic = createEpic();
        epic.setListSubtask(new ArrayList<>());
        manager.createNewEpic(epic);
        Subtask subtask = createSubtask();
        manager.createNewSubtask(subtask);
        manager.deleteSubtaskById(999);
        assertEquals(List.of(subtask), manager.getAllSubtasks());
    }

    @Test
    public void shouldDoNothingIfTaskHashMapIsEmpty() throws IOException {
        manager.deleteAllTasks();
        manager.deleteTaskById(999);
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    public void shouldDoNothingIfEpicHashMapIsEmpty() throws IOException {
        manager.deleteAllEpics();
        manager.deleteEpicById(999);
        assertEquals(0, manager.getAllEpics().size());
    }

    @Test
    public void shouldDoNothingIfSubtaskHashMapIsEmpty() throws IOException {
        manager.deleteAllSubtasks();
        manager.deleteSubtaskById(999);
        assertEquals(0, manager.getAllSubtasks().size());
    }

    @Test
    void shouldReturnEmptyListWhenGetSubtaskByEpicIdIsEmpty() throws IOException {
        Epic epic = createEpic();
        manager.createNewEpic(epic);
        List<Subtask> subtasks = manager.getAllSubtaskFromEpic(epic.getId());
        assertTrue(subtasks.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListTasksIfNoTasks() {
        assertTrue(manager.getAllTasks().isEmpty());
    }

    @Test
    public void shouldReturnEmptyListEpicsIfNoEpics() {
        assertTrue(manager.getAllEpics().isEmpty());
    }

    @Test
    public void shouldReturnEmptyListSubtasksIfNoSubtasks() {
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    public void shouldReturnNullIfTaskDoesNotExist() {
        assertNull(manager.getTaskById(999));
    }

    @Test
    public void shouldReturnNullIfEpicDoesNotExist() {
        assertNull(manager.getEpicById(999));
    }

    @Test
    public void shouldReturnNullIfSubtaskDoesNotExist() {
        assertNull(manager.getSubtaskById(999));
    }

    @Test
    public void shouldReturnEmptyHistory() {
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

    @Test
    public void shouldReturnEmptyHistoryIfTasksNotExist() {
        manager.getTaskById(999);
        manager.getSubtaskById(999);
        manager.getEpicById(999);
        assertTrue(manager.getHistory().isEmpty());
    }

    @Test
    public void shouldReturnHistoryWithTasks() throws IOException {
        Epic epic = createEpic();
        epic.setListSubtask(new ArrayList<>());
        manager.createNewEpic(epic);
        Subtask subtask = createSubtask();
        manager.createNewSubtask(subtask);
        manager.getEpicById(epic.getId());
        manager.getSubtaskById(subtask.getId());
        List<Task> list = manager.getHistory();
        assertEquals(2, list.size());
        assertTrue(list.contains(subtask));
        assertTrue(list.contains(epic));
    }

}