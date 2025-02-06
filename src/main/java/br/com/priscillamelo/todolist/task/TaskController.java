package br.com.priscillamelo.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;

    @GetMapping
    public List<TaskModel> getTasks(HttpServletRequest request) {
        UUID idUser = (UUID) request.getAttribute("idUser");
        System.out.println(idUser);
        List<TaskModel> listTasks = this.taskRepository.findByIdUser(idUser);
        return listTasks;
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        UUID idUser = (UUID) request.getAttribute("idUser");
        System.out.println(idUser);
        taskModel.setIdUser(idUser);
        LocalDateTime currentDate = LocalDateTime.now();
        boolean validStartDate = currentDate.isBefore(taskModel.getStartAt());
        boolean validEndDate = currentDate.isBefore(taskModel.getEndAt());
        boolean validDate = taskModel.getEndAt().isAfter(taskModel.getStartAt());
        if ((validStartDate) && (validEndDate) && (validDate)) {
            var task = this.taskRepository.save(taskModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(task.toString());
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body("Data inválida, confira as datas de início e término da tarefa!");
    }

}
