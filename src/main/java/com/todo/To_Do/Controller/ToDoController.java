package com.todo.To_Do.Controller;


import com.todo.To_Do.Entity.ToDo;
import com.todo.To_Do.Services.ToDoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/edit")
public class ToDoController {

    @Autowired
    private ToDoServices toDoServices;

    @GetMapping
    public List<ToDo> getAllToDo(){
        return toDoServices.getAll();
    }
    @GetMapping("id/{myId}")
    public ToDo getToDoById(@PathVariable String myId){
        return toDoServices.getById(myId);
    }
    @PostMapping
    public boolean enterToDo(@RequestBody ToDo toDo){
        toDoServices.enter(toDo);
        return true;
    }
    @DeleteMapping("id/{myId}")
    public boolean deleteToDo(@PathVariable String myId){
        toDoServices.delete(myId);
        return true;
    }
    @PutMapping("id/{myId}")
    public boolean updateToDo(@PathVariable String myId, @RequestBody ToDo newToDo){
        toDoServices.update(myId,newToDo);
        return true;
    }
}
