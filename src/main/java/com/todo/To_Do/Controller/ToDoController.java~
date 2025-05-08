package com.todo.To_Do.Controller;


import com.todo.To_Do.Entity.ToDo;
import com.todo.To_Do.Services.ToDoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/edit")
public class ToDoController {

    @Autowired
    private ToDoServices toDoServices;
    @GetMapping
    public ResponseEntity<List<ToDo>> getAllToDo(){
        return toDoServices.getAll();
    }
    @GetMapping("id/{myId}")
    public ResponseEntity<?> getToDoById(@PathVariable String myId){
        return toDoServices.getById(myId);
    }
    @PostMapping
    public ResponseEntity<?> enterToDo(@RequestBody ToDo toDo){
        return toDoServices.enter(toDo);
    }
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteToDo(@PathVariable String myId){
        return toDoServices.delete(myId);
    }
    //here i am not putting the id param beacuse it is impractical that the user should give the id
    @PutMapping
    public ResponseEntity<?> updateToDo( @RequestBody ToDo newToDo){
        return toDoServices.update(newToDo.getId(), newToDo);
    }
}
