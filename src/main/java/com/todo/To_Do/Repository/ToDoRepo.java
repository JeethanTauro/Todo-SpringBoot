package com.todo.To_Do.Repository;

import com.todo.To_Do.Entity.ToDo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepo extends CrudRepository<ToDo,String>  {
}
