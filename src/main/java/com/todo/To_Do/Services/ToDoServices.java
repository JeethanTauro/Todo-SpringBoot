package com.todo.To_Do.Services;


import com.todo.To_Do.Entity.ToDo;
import com.todo.To_Do.Repository.ToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ToDoServices {
    @Autowired
    private ToDoRepo toDoRepo;

    public List<ToDo> getAll(){
       Iterable<ToDo> iterable = toDoRepo.findAll();
       List<ToDo> list = new ArrayList<>();
       for(ToDo todo : iterable){
           list.add(todo);
       }
       return list;
    }
    public void enter(ToDo todo){
        toDoRepo.save(todo);
    }
    public void delete(String id){
        toDoRepo.deleteById(id);
    }
    public ToDo getById(String id){
        Optional<ToDo> optional = toDoRepo.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        else {
            return null;
        }
    }
    public void update(String id, ToDo todo){
        Optional<ToDo> optional = toDoRepo.findById(id);
        if(optional.isPresent()){
            ToDo oldToDo = optional.get();
            if(!Objects.equals(oldToDo.getContent(), todo.getContent())){
                oldToDo.setContent(todo.getContent());
            }
            if(!Objects.equals(oldToDo.getTitle(), todo.getTitle())){
                oldToDo.setTitle(todo.getTitle());
            }
            enter(oldToDo);
        }
        else{
            enter(todo);
        }
    }
}
