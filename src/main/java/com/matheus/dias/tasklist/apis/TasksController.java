package com.matheus.dias.tasklist.apis;

import com.matheus.dias.tasklist.models.Tasks;
import com.matheus.dias.tasklist.models.enums.TaskStatus;
import com.matheus.dias.tasklist.repositories.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@Transactional(propagation = Propagation.SUPPORTS)
public class TasksController {

    @Autowired
    private TasksRepository repository;

    @GetMapping
    public List<Tasks> findAll() {
        return repository.findAll();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Tasks> findOne(@PathVariable("id") Long id) {
        Optional<Tasks> task;
        try {
            return new ResponseEntity<Tasks>(repository.findById(id).get(), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Tasks> create(@RequestBody Tasks task) {
        return ResponseEntity.ok(repository.save(task));
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Tasks task) {
        return ResponseEntity.ok(repository.save(task));
    }

    @PostMapping("/undo/{id}")
    public ResponseEntity undone(@PathVariable("id") Long id) {
        Tasks task = repository.findById(id).get();

        if (Objects.isNull(task)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        task.setStatus(TaskStatus.PENDING);
        return ResponseEntity.ok(repository.save(task));
    }

    @PostMapping("/do/{id}")
    public ResponseEntity done(@PathVariable("id") Long id) {
        Tasks task = repository.findById(id).get();
        if (Objects.isNull(task)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        task.setStatus(TaskStatus.DONE);
        return ResponseEntity.ok(repository.save(task));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Tasks> remove(@PathVariable("id") Long id) {
        Tasks task = repository.findById(id).get();

        if (Objects.isNull(task)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repository.delete(task);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
