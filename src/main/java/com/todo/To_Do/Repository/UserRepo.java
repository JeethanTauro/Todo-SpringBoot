package com.todo.To_Do.Repository;

import com.todo.To_Do.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User,String>  {
    public User findByUserName(String userName);
    public void deleteByUserName(String userName);
}
