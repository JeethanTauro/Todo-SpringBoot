package com.todo.To_Do.Controller;


import com.todo.To_Do.Entity.User;
import com.todo.To_Do.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/admin")
public class AdminUserController {

    @Autowired
    private UserServices userServices;
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        return userServices.getAll();
    }
    @GetMapping("{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName){
        return userServices.getByUserName(userName);
    }

    @DeleteMapping("{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName){
        return userServices.delete(userName);
    }

    @PutMapping("{userName}")
    public ResponseEntity<?> updateUser(@PathVariable String userName, @RequestBody User user){
        return userServices.update(userName , user);
    }
}
