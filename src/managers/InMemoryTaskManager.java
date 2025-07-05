package managers;

import enums.StatusOfTask;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private int counterOfTasks = 0;

    private Map<Integer, Task> taskHashMap = new HashMap<>();
    private Map<Integer, Epic> epicHashMap = new HashMap<>();
    private Map<Integer, Subtask> subtaskHashMap = new HashMap<>();

    private TreeSet<Task> prioritizedTaskTreeSet;


    private final HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    private void setCounterOfTasks() {
        this.counterOfTasks++;
    }

    @Override
    public int getCounterOfTasks() {
        return counterOfTasks;
    }

    @Override
    public List<Task> getAllTasks() {
        System.out.println("Вот список всех задач :");
        return new ArrayList<>(taskHashMap.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        System.out.println("Вот список всех эпиков :");
        return new ArrayList<>(epicHashMap.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        System.out.println("Вот список всех подзадач :");
        return new ArrayList<>(subtaskHashMap.values());
    }

    @Override
    public void deleteAllTasks() {
        taskHashMap.clear();
    }

    @Override
    public void deleteAllEpics() {
        epicHashMap.clear();
        subtaskHashMap.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epicHashMap.values()) {
            epic.getListSubtask().clear();
            updateEpicStatus(epic.getId());
            subtaskHashMap.clear();
        }
    }

    @Override
    public Task getTaskById(int id) {
        if (taskHashMap.containsKey(id)) {
            inMemoryHistoryManager.add(taskHashMap.get(id));
            return taskHashMap.get(id);
        }
        System.out.println("Задачи по id - " + id + " не обнаружено!");
        return null;
    }

    @Override
    public Task getEpicById(int id) {
        if (epicHashMap.containsKey(id)) {
            inMemoryHistoryManager.add(epicHashMap.get(id));
            return epicHashMap.get(id);
        }
        System.out.println("Эпика по id - " + id + " не обнаружено!");
        return null;
    }

    @Override
    public Task getSubtaskById(int id) {
        if (subtaskHashMap.containsKey(id)) {
            inMemoryHistoryManager.add(subtaskHashMap.get(id));
            return subtaskHashMap.get(id);
        }
        System.out.println("Сабтаски по id - " + id + " не обнаружено!");
        return null;
    }


    @Override
    public void createNewTask(Task task) {
        List<Task> taskList = new ArrayList<>(taskHashMap.values());
        if (!isOverlappingWithAnyTask(task, taskList)) {
            task.setId(counterOfTasks);
            taskHashMap.put(task.getId(), task);
            counterOfTasks++;
        } else {
            System.out.println("Ошибка: задача пересекается с другой задачей.");
        }
    }

    @Override
    public void createNewEpic(Epic epic) {
        epic.setId(epic.getId());
        epicHashMap.put(epic.getId(), epic);
        counterOfTasks++;
    }

    @Override
    public void createNewSubtask(Subtask subtask) {
        List<Subtask> subtasks = new ArrayList<>(subtaskHashMap.values());
        if (!isOverlappingWithAnyTask(subtask, subtasks)) {
            subtask.setId(counterOfTasks);
            subtaskHashMap.put(subtask.getId(), subtask);
            int epicId = subtask.getEpicId();
            Epic epic = epicHashMap.get(epicId);
            epic.getListSubtask().add(subtask.getId());
            updateEpicStatus(epicId);
            counterOfTasks++;
        } else {
            System.out.println("Ошибка: подзадача пересекается с другой подзадачей.");
        }
    }

    @Override
    public void updateTask(Task task) {
        List<Task> taskList = new ArrayList<>(taskHashMap.values());
        if (!isOverlappingWithAnyTask(task, taskList)) {
            if (taskHashMap.containsKey(task.getId())) {
                taskHashMap.put(task.getId(), task);
            } else {
                System.out.println("Такой задачи не существует! Проверь еще раз)");
            }
        } else {
            System.out.println("Ошибка: задача пересекается с другой задачей.");
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epicHashMap.containsKey(epic.getId())) {
            epicHashMap.put(epic.getId(), epic);
        } else {
            System.out.println("Такого эпика не существует! Проверь еще раз)");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        List<Subtask> subtasks = new ArrayList<>(subtaskHashMap.values());
        if (!isOverlappingWithAnyTask(subtask, subtasks)) {
            if (subtaskHashMap.containsKey(subtask.getId())) {
                subtaskHashMap.put(subtask.getId(), subtask);
            } else {
                System.out.println("Такой подзадачи не существует! Проверь еще раз)");
                return;
            }
            int epicId = subtask.getEpicId();
            updateEpicStatus(epicId);
        } else {
            System.out.println("Ошибка: задача пересекается с другой задачей.");
        }
    }

    @Override
    public void deleteTaskById(int id) {
        taskHashMap.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        epicHashMap.remove(id);
        subtaskHashMap.values().stream()
                .filter(subtask -> subtask.getEpicId() == id)
                .forEach(subtask -> subtaskHashMap.remove(subtask.getId()));


    }

    @Override
    public void deleteSubtaskById(int id) {
        int epicId = subtaskHashMap.get(id).getEpicId();
        Epic epic = epicHashMap.get(epicId);
        epic.getListSubtask().remove((Integer) id);
        subtaskHashMap.remove(id);
        updateEpicStatus(epicId);

    }

    @Override
    public List<Subtask> getAllSubtaskFromEpic(int epicId) {
        return subtaskHashMap.values().stream()
                .filter(subtask -> subtask.getEpicId() == epicId)
                .collect(Collectors.toList());
    }

    private void updateEpicStatus(int epicId) {
        int counterNew = 0;
        int counterDone = 0;

        Epic epic = epicHashMap.get(epicId);
        if (epic.getListSubtask().isEmpty()) {
            epic.setStatusOfTask(StatusOfTask.DONE);
        } else
            for (int idSubtaskFromEpicSubtasks : epic.getListSubtask()) {
                for (Subtask subtask : subtaskHashMap.values()) {

                    if (subtask.getId() == idSubtaskFromEpicSubtasks) {

                        if (subtask.getStatusOfTask().equals(StatusOfTask.DONE)) {
                            counterDone++;
                        } else if (subtask.getStatusOfTask().equals(StatusOfTask.NEW)) {
                            counterNew++;
                        }
                    } else
                        System.out.println("Что-то пошло не так!");

                }
                if (counterDone == epic.getListSubtask().size()) {
                    epic.setStatusOfTask(StatusOfTask.DONE);
                } else if (counterNew == epic.getListSubtask().size()) {
                    epic.setStatusOfTask(StatusOfTask.NEW);

                } else
                    epic.setStatusOfTask(StatusOfTask.IN_PROGRESS);

            }
    }

    @Override
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    public TreeSet<Task> getPrioritizedTaskTreeSet() {
        getPrioritizedTasks();
        return prioritizedTaskTreeSet;
    }

    @Override
    public Duration getDuration(Epic epic) {
        List<Subtask> subtasks = getAllSubtaskFromEpic(epic.getId());// Получаем список подзадach

        Duration totalDuration = subtasks.stream()
                .map(Task::getDuration)
                .reduce(Duration.ZERO, Duration::plus); // Суммируем продолжительности всех подзадach
        return totalDuration;
    }


    @Override
    public LocalDateTime getStartTime(Epic epic) {
        List<Subtask> subtasks = getAllSubtaskFromEpic(epic.getId()); // Получаем список подзадach

        LocalDateTime maxStartTime = subtasks.stream()
                .map(Task::getStartTime)
                .max(Comparator.naturalOrder())
                .orElse(null); // Находим максимальное время окончания

        return maxStartTime;
    }

    @Override
    public LocalDateTime getEndTime(Epic epic) {
        List<Subtask> subtasks = getAllSubtaskFromEpic(epic.getId()); // Получаем список подзадach

        LocalDateTime maxEndTime = subtasks.stream()
                .map(Task::getEndTime)
                .max(Comparator.naturalOrder())
                .orElse(null); // Находим максимальное время окончания

        return maxEndTime;
    }

    public List<Task> getPrioritizedTasks() {

        List<Subtask> subtasks = getAllSubtasks();
        List<Task> tasks = getAllTasks();
        tasks.addAll(subtasks);
        tasks.removeIf(task -> task.getStartTime() == null);

        List<Task> prioritizedTasksList = tasks.stream()
                .sorted(Comparator.comparing(Task::compareTo))// Сортируем по приоритету
                .collect(Collectors.toList());

        prioritizedTaskTreeSet = new TreeSet<>(prioritizedTasksList);
        return prioritizedTasksList;
//    нужна подсказка, не пойму что не так делаю тут
    }

    public boolean isOverlapping(Task task1, Task task2) {
        // Проверяем, не пересекается ли интервал первой задачи с интервалом второй задачи
        if (task1.getStartTime().isBefore(task2.getEndTime()) &&
                task1.getEndTime().isAfter(task2.getStartTime())) {
            return true; // Задачи пересекаются
        } else {
            return false; // Задачи не пересекаются
        }
    }

    public boolean isOverlappingWithAnyTask(Task task, List<? extends Task> tasks) {
        for (Task otherTask : tasks) {
            if (isOverlapping(task, otherTask)) {
                return true; // Задача пересекается с другой задачей из списка
            }
        }
        return false; // Задача не пересекается ни с одной другой задачей из списка
    }


}






