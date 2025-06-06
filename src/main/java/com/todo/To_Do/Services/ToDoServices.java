package com.todo.To_Do.Services;


import com.todo.To_Do.Entity.ToDo;
import com.todo.To_Do.Entity.User;
import com.todo.To_Do.Repository.ToDoRepo;
import com.todo.To_Do.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ToDoServices {
    @Autowired
    private ToDoRepo toDoRepo;
    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<List<ToDo>> getAll(String userName){
        try{
            User user = userRepo.findByUserName(userName);
            List<ToDo> list = user.getToDoList();
            return new ResponseEntity<>( list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @Transactional
    public ResponseEntity<?> enter(String userName , ToDo todo){
        try{
            ToDo saved = toDoRepo.save(todo);
            User user = userRepo.findByUserName(userName);
            if(user == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            user.getToDoList().add(saved);
            userServices.saveUser(user);
            return new ResponseEntity<>(todo , HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @Transactional
    public ResponseEntity<?> delete(String userName,String id){
        try{
            Optional<ToDo> optional = toDoRepo.findById(id);
            User user = userRepo.findByUserName(userName);
            if(optional.isPresent()){
                toDoRepo.deleteById(id);
                user.getToDoList().removeIf(x->x.getId().equals(id));
                userServices.saveUser(user);
                return new ResponseEntity<>(optional.get() , HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> getById(String userName,String id){
        User user = userRepo.findByUserName(userName);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<ToDo> optional = toDoRepo.findById(id);
        if(optional.isPresent()){
            return new ResponseEntity<>(optional.get() , HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    public ResponseEntity<ToDo> update(String userName,String id, ToDo todo){
       try{
           User user = userRepo.findByUserName(userName);
           Optional<ToDo> optional = toDoRepo.findById(id);
           if(optional.isPresent()){
               ToDo oldToDo = optional.get();
               if(!Objects.equals(oldToDo.getContent(), todo.getContent())){
                   oldToDo.setContent(todo.getContent());
               }
               if(!Objects.equals(oldToDo.getTitle(), todo.getTitle())){
                   oldToDo.setTitle(todo.getTitle());
               }
               if(!Objects.equals(oldToDo.isCompleted(),todo.isCompleted())){
                   oldToDo.setCompleted(todo.isCompleted());
               }
               ToDo saved = toDoRepo.save(oldToDo);
               user.getToDoList().removeIf(x -> x.getId().equals(saved.getId()));
               user.getToDoList().add(saved);
               userServices.saveUser(user);
               return new ResponseEntity<>(oldToDo,HttpStatus.OK);
           }
           // I have decided not to create a new
           // todo_data because it might create some issues as
           // the user might think he update but instead it
           // created it
           else{
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }

       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }
}
