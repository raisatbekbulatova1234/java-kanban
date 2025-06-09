package managers;

public class Node<Task> {
    public Task task;
    public Node<Task> next;
    public Node<Task> prev;

    public Node(Node<Task> prev, Task task, Node<Task> next) {
        this.task = task;
        this.next = null;
        this.prev = null;
    }
    /// я не могу найти, все переписала, не проходит, а время идет, помогите пожалуйста, сил уже нет на это все
    /*Error:  /home/runner/work/java-kanban/java-kanban/./src/managers/Node.java:3:19: Class type name 'Task' must match pattern '(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)'. [ClassTypeParameterName]
    Audit done.
    Checkstyle ends with 1 errors.
            Error: Process completed with exit code 1.

     */

}