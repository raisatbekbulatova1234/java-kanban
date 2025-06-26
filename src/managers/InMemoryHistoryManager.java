package managers;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node<Task>> mapTasks = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;

    private void linkLast(Task element) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node(oldTail, element, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        mapTasks.put(element.getId(), newNode);
    }

    private void removeNode(Node<Task> node) {
        if (node == head) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (node == tail) {
            tail = node.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;

        }
        mapTasks.remove(node);
    }

    @Override
    public void add(Task task) {
        if (mapTasks.containsKey(task.getId())) {
            remove(task.getId());
        }
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        removeNode(mapTasks.get(id));
    }

    @Override
    public List<Task> getHistory() {
        if (!mapTasks.isEmpty()) {
            List<Task> tasks = new ArrayList<>();
            for (Map.Entry<Integer, Node<Task>> entry : mapTasks.entrySet()) {
                tasks.add(entry.getValue().task);
            }
            return tasks;
        } else {
            System.out.println("Список просмотренных задач пуст!");
            return new ArrayList<>();
        }
    }
}
