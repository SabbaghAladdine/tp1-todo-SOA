import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TodoList implements Serializable {
    static final String FILE = "todo.dat";

    List<Task> tasks = new ArrayList<>();
    int nextId = 1;

    // ---------- save / load (one local file) ----------
    static TodoList load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE))) {
            return (TodoList) in.readObject();
        } catch (Exception e) {
            return new TodoList();   // first run: empty list
        }
    }

    void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE))) {
            out.writeObject(this);
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }

    // ---------- actions ----------
    String add(String title) {
        if (title.isEmpty()) return "A title is required.";
        for (Task t : tasks)
            if (!t.done && t.title.equalsIgnoreCase(title)) return "This task already exists.";
        tasks.add(new Task(nextId++, title));
        save();
        return "Task added.";
    }

    String toggle(Task t) {
        t.done = !t.done;
        save();
        return t.done ? "Task marked as done." : "Task marked as not done.";
    }

    String delete(Task t) {
        tasks.remove(t);
        save();
        return "Task deleted.";
    }
}
