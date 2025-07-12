package managers;

import enums.StatusOfTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    public static final Path path = Path.of("text.txt");
    File file = new File(String.valueOf(path));

    @BeforeEach
    public void beforeEach() {
        manager = Managers.getDefaultFileBacked(file);
    }

    @AfterEach
    public void afterEach() {
        try {
            Files.delete(path);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void shouldCorrectlySaveAndLoad() throws IOException {
        Task task = new Task("Учеба", "Сдать 5-й проект.", StatusOfTask.DONE, LocalDateTime.now(), Duration.ofMinutes(10000));
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofMinutes(10000));
        manager.createNewTask(task);

        Epic epic = new Epic("Программирование", "Пройти спринт 5", new ArrayList<>(),
                StatusOfTask.NEW);
        epic.setStartTime(LocalDateTime.now());
        epic.setDuration(Duration.ofMinutes(20000));
        manager.createNewEpic(epic);

        FileBackedTaskManager fileManager = new FileBackedTaskManager(file);
        fileManager.loadFromFile(file);

        assertEquals(List.of(task), manager.getAllTasks());
        assertEquals(List.of(epic), manager.getAllEpics());
    }


    @Test
    public void shouldSaveAndLoadEmptyTasksEpicsSubtasks() throws IOException {
        FileBackedTaskManager fileManager = new FileBackedTaskManager(file);
        fileManager.save();
        fileManager.loadFromFile(file);
        assertEquals(Collections.EMPTY_LIST, manager.getAllTasks());
        assertEquals(Collections.EMPTY_LIST, manager.getAllEpics());
        assertEquals(Collections.EMPTY_LIST, manager.getAllSubtasks());
    }

    @Test
    public void shouldSaveAndLoadEmptyHistory() throws IOException {
        FileBackedTaskManager fileManager = new FileBackedTaskManager(file);
        fileManager.save();
        fileManager.loadFromFile(file);
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }


}