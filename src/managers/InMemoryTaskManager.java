package managers;

import enums.StatusOfTask;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int counterOfTasks = 0;

    private Map<Integer, Task> taskHashMap = new HashMap<>();
    private Map<Integer, Epic> epicHashMap = new HashMap<>();
    private Map<Integer, Subtask> subtaskHashMap = new HashMap<>();

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
        task.setId(counterOfTasks);
        taskHashMap.put(task.getId(), task);

    }

    @Override
    public void createNewEpic(Epic epic) {
        epic.setId(epic.getId());
        epicHashMap.put(epic.getId(), epic);
    }

    @Override
    public void createNewSubtask(Subtask subtask) {
        subtask.setId(counterOfTasks);
        subtaskHashMap.put(subtask.getId(), subtask);
        int epicId = subtask.getEpicId();
        Epic epic = epicHashMap.get(epicId);
        epic.getListSubtask().add(subtask.getId());
        updateEpicStatus(epicId);
    }

    @Override
    public void updateTask(Task task) {
        if (taskHashMap.containsKey(task.getId())) {
            taskHashMap.put(task.getId(), task);
        } else {
            System.out.println("Такой задачи не существует! Проверь еще раз)");
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
        if (subtaskHashMap.containsKey(subtask.getId())) {
            subtaskHashMap.put(subtask.getId(), subtask);
        } else {
            System.out.println("Такой подзадачи не существует! Проверь еще раз)");
            return;
        }
        int epicId = subtask.getEpicId();
        updateEpicStatus(epicId);
    }

    @Override
    public void deleteTaskById(int id) {
        taskHashMap.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        epicHashMap.remove(id);
        for (Subtask subtask : subtaskHashMap.values()) {
            if (subtask.getEpicId() == id) {
                subtaskHashMap.remove(subtask.getId());
            }
        }

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
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Integer id : epicHashMap.get(epicId).getListSubtask()) {
            Subtask subtask = subtaskHashMap.get(id);
            subtasks.add(subtask);
        }
        return subtasks;
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
}





