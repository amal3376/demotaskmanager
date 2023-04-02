package com.example.demotaskmanager.services;

import com.example.demotaskmanager.entities.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskService {

    private final List<Task> taskList;
    private AtomicInteger taskId = new AtomicInteger(0);

    public static class TaskNotFoundExeption extends IllegalStateException{
        public TaskNotFoundExeption(Integer id){
            super("  Task with "+id+" not found");
        }
    }

    public TaskService() {
        taskList = new ArrayList<>();
        taskList.add(new Task(taskId.incrementAndGet(),"task1", "description1", "2021-03-04"));
        taskList.add(new Task(taskId.incrementAndGet(),"task2", "description2", "2021-03-04"));
        taskList.add(new Task(taskId.incrementAndGet(),"task3", "description3", "2021-03-04"));
        taskList.add(new Task(taskId.incrementAndGet(),"task4", "description4", "2021-03-04"));
    }
    public List<Task> getTasks(){
        return taskList;
    }
    public Task createTask(
            String title,
            String description,
            String dueDate
    ){
        var newTask = new Task(taskId.incrementAndGet(), title, description,dueDate);
        taskList.add(newTask);
        return newTask;
    }

    public Task getTaskById(Integer id){
        return taskList.stream()
                .filter(task -> task.getId().equals(id)).findFirst()
                .orElseThrow(() -> new TaskNotFoundExeption(id));
//        for (int i = 0; i < taskList.size(); i++) {
//            if (taskList.get(i).getId().equals(id)){
//                return taskList.get(i);
//            }
//        }
//        return null;
    }
    public Task updateTask(
            Integer id,
            String title,
            String description,
            String dueDate
    ) {
        var task = getTaskById(id);
        if (title != null)task.setTitle(title);
        if (description!=null)task.setDescription(description);
        if (dueDate!=null)task.setDueDate(dueDate);
        return task;
    }
    public Task deleteTask(Integer id){
        var task = getTaskById(id);
        taskList.remove(task);
        return task;
    }
}
