package managers;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node<Task>> mapTasks = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;

    private void linkLast(Task element) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(oldTail, element, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
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
            for (Map.Entry entry : mapTasks.entrySet()) {
                tasks.add((Task) entry.getValue());
            }
            return tasks;
        } else {
            System.out.println("Список просмотренных задач пуст!");
            return new ArrayList<>();
        }
    }

    private static class Node<Task> {
        public Task task;
        public Node<Task> next;
        public Node<Task> prev;

        public Node(Node<Task> prev, Task task, Node<Task> next) {
            this.task = task;
            this.next = null;
            this.prev = null;
        }
    }
    //я не понимаю где тут ошибка

    /*  Error:  /home/runner/work/java-kanban/java-kanban/./src/managers/InMemoryHistoryManager.java:74:31: Class type name 'Task' must match pattern '(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)'. [ClassTypeParameterName]
    Checkstyle ends with 1 errors.
    Audit done.
    Error: Process completed with exit code 1.

    что надо исправить не вижу
     */
}
