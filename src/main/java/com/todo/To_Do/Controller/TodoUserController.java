package com.todo.To_Do.Controller;

import com.todo.To_Do.Entity.ToDo;
import com.todo.To_Do.Entity.User;
import com.todo.To_Do.Repository.UserRepo;
import com.todo.To_Do.Services.ToDoServices;
import com.todo.To_Do.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo/user")
public class TodoUserController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private ToDoServices toDoServices;
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public ResponseEntity<?> getUserByUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return userServices.getByUserName(userName);
    }
    // can update username and password
    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return userServices.update(userName , user);
    }
    // can add a todo_ after the user gets authenticated, he can add todo_ under his account
    @PostMapping()
    public ResponseEntity<?> addTodo(@RequestBody ToDo todo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return toDoServices.enter(userName,todo);
    }
    //can update todo_ of a username
    //now myId must be present in the List of todo_ of that user else it wont work right
    @PutMapping("{myId}")
    public ResponseEntity<?> updateToDo(@PathVariable String myId, @RequestBody ToDo newToDo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        if(checkId(userName,myId)){
            return toDoServices.update(userName,myId, newToDo);
        };
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // can delete todo_
    @DeleteMapping("{myId}")
    public ResponseEntity<?> deleteToDo(@PathVariable String myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        if(checkId(userName,myId)){
            return toDoServices.delete(userName,myId);
        };
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    // can get a todo_ by id
    @GetMapping("{myId}")
    public ResponseEntity<?> getToDoById(@PathVariable String myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        if(checkId(userName,myId)){
            return toDoServices.getById(userName,myId);
        };
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public boolean checkId(String userName,String myId){
        User user = userRepo.findByUserName(userName);
        List<ToDo> list = user.getToDoList().stream().filter(x->x.getId().equals(myId)).toList();
        if(list.isEmpty()){
            return false;
        }
        return true;
    }
}
