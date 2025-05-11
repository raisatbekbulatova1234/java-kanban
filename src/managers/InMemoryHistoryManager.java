package managers;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_SIZE = 10;

    private final List<Task> viewedTasks = new ArrayList<>();

    public void addTask(Task task) {
        if (viewedTasks.size() == MAX_SIZE) {
            viewedTasks.removeFirst();
        }
        viewedTasks.add(task);
    }

    @Override
    public List<Task> getHistory() {
        if (!viewedTasks.isEmpty()) {
            return viewedTasks;
        } else {
            System.out.println("Список просмотренных задач пуст!");
            return new ArrayList<>();
        }
    }
}
