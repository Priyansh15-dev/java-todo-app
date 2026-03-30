import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TodoListApp {

    public static void main(String[] args) {

        System.out.println("Welcome to my To-Do List App");

        Scanner input = new Scanner(System.in);

        // storing tasks using ArrayList of Task objects
        ArrayList<Task> tasks = new ArrayList<Task>();

        // load previous tasks from file
        loadTasks(tasks);

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("=== To-Do Menu ===");
            System.out.println("1. View tasks");
            System.out.println("2. Add task");
            System.out.println("3. Delete task");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = input.nextLine();

            if (choice.equals("1")) {
                viewTasks(tasks);

            } else if (choice.equals("2")) {
                addTask(input, tasks);

            } else if (choice.equals("3")) {
                deleteTask(input, tasks);

            } else if (choice.equals("4")) {
                saveTasks(tasks);
                System.out.println("Exiting program...");
                running = false;

            } else {
                System.out.println("Wrong choice, try again.");
            }
        }

        input.close();
    }

    // display all tasks
    private static void viewTasks(ArrayList<Task> tasks) {
        System.out.println();
        System.out.println("Your tasks:");

        if (tasks.size() == 0) {
            System.out.println("No tasks found.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).getDescription());
        }

        System.out.println("Total tasks: " + tasks.size());
    }

    // adding a new task
    private static void addTask(Scanner input, ArrayList<Task> tasks) {
        System.out.print("Enter task: ");
        String taskText = input.nextLine();

        if (taskText.trim().length() == 0) {
            System.out.println("Task cannot be empty.");
            return;
        }

        Task newTask = new Task(taskText.trim());
        tasks.add(newTask);

        saveTasks(tasks);

        System.out.println("Task added successfully.");
    }

    // deleting a task
    private static void deleteTask(Scanner input, ArrayList<Task> tasks) {

        if (tasks.size() == 0) {
            System.out.println("No tasks to delete.");
            return;
        }

        viewTasks(tasks);
        System.out.print("Enter task number to delete: ");

        String numText = input.nextLine();

        try {
            int num = Integer.parseInt(numText);

            if (num < 1 || num > tasks.size()) {
                System.out.println("Invalid task number.");
                return;
            }

            Task removedTask = tasks.remove(num - 1);

            saveTasks(tasks);

            System.out.println("Removed: " + removedTask.getDescription());

        } catch (Exception e) {
            System.out.println("Please enter a valid number.");
        }
    }

    // load tasks from file
    private static void loadTasks(ArrayList<Task> tasks) {
        try {
            FileReader reader = new FileReader("tasks.txt");
            Scanner fileScanner = new Scanner(reader);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                if (line.trim().length() > 0) {
                    tasks.add(new Task(line.trim()));
                }
            }

            fileScanner.close();

        } catch (IOException e) {

        }
    }

    // save tasks to file
    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            FileWriter writer = new FileWriter("tasks.txt", false);

            for (int i = 0; i < tasks.size(); i++) {
                writer.write(tasks.get(i).getDescription());
                writer.write(System.lineSeparator());
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }
}


// simple Task class (OOP concept)
class Task {
    private String description;

    public Task(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}