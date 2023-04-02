package com.example.demotaskmanager.controllers;

import com.example.demotaskmanager.dtos.ErrorResponse;
import com.example.demotaskmanager.entities.Task;
import com.example.demotaskmanager.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TaskController{
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private TaskService taskService;
    @GetMapping("/tasks")
    ResponseEntity<List<Task>> getTask(){
        return ResponseEntity.ok(taskService.getTasks());
    }
    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@RequestBody Task task){
        var newTask = taskService.createTask(task.getTitle(), task.getDescription(), task.getDueDate());
        return ResponseEntity.created(URI.create("/tasks/" + newTask.getId())).body(newTask);
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> getTask(@PathVariable("id") Integer id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @DeleteMapping("/tasks/{id}")
    ResponseEntity<Task> deleteTask(@PathVariable("id") Integer id){
        return ResponseEntity.accepted().body(taskService.deleteTask(id));
    }
    @PatchMapping("/tasks/{id}")
    ResponseEntity<Task> updateTask(@PathVariable("id") Integer id, @RequestBody Task task){
        var updateTask = taskService.updateTask(
                id,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate());

        return ResponseEntity.accepted().body(updateTask);
    }

    @ExceptionHandler(TaskService.TaskNotFoundExeption.class)
    ResponseEntity<ErrorResponse> handleErrors(TaskService.TaskNotFoundExeption e){
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
