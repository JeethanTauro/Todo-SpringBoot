package com.todo.To_Do.Controller;


import com.todo.To_Do.Entity.ToDo;
import com.todo.To_Do.Services.ToDoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo/admin") //changed from /edit to /todo
public class AdminToDoController {

    @Autowired
    private ToDoServices toDoServices;
    @GetMapping("{userName}")
    public ResponseEntity<List<ToDo>> getAllToDoOfUser(@PathVariable String userName){
        return toDoServices.getAll(userName);
    }
    @GetMapping("{userName}/id/{myId}")
    public ResponseEntity<?> getToDoById(@PathVariable String userName,@PathVariable String myId){
        return toDoServices.getById(userName,myId);
    }

    @DeleteMapping("{userName}/{myId}")
    public ResponseEntity<?> deleteToDo(@PathVariable String userName , @PathVariable String myId){
        return toDoServices.delete(userName,myId);
    }
    // we cannot take id from the body unless we specify it in the json body itself so give it as params
    @PutMapping("{userName}/{myId}")
    public ResponseEntity<?> updateToDo(@PathVariable String userName,@PathVariable String myId, @RequestBody ToDo newToDo){
        return toDoServices.update(userName,myId, newToDo);
    }
}
