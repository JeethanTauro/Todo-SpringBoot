package com.todo.To_Do.Controller;


import com.todo.To_Do.Entity.User;
import com.todo.To_Do.Services.ToDoServices;
import com.todo.To_Do.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    UserServices userServices;
    @Autowired
    ToDoServices toDoServices;
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user){
        return userServices.saveNewUser(user);
    }

}
