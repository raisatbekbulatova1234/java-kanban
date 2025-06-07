package managers;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_SIZE = 10;

    private final Map<Integer, Node<Task>> mapTasks = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;

    public class Node<Task> {
        public Task data;
        public Node<Task> next;
        public Node<Task> prev;

        public Node(Node<Task> prev, Task data, Node<Task> next) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    public void linkLast(Task element) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(oldTail, element, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node<Task> node = head;
        while (node != null) {
            tasks.add(node.data);
            node = node.next;
        }
        return tasks;
    }

    public void removeNode(Node<Task> node) {
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
    }

    @Override
    public void add(Task task) {
        if (mapTasks.containsKey(task.getId())) {
            remove(task.getId());
            mapTasks.remove(task.getId());
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
            for (Map.Entry entry : mapTasks.entrySet()) {
                tasks.add((Task) entry.getValue());
            }
            return tasks;
        } else {
            System.out.println("Список просмотренных задач пуст!");
            return new ArrayList<>();
        }
    }
}
