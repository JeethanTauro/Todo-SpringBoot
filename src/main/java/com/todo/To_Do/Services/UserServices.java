package com.todo.To_Do.Services;


import com.todo.To_Do.Entity.Roles;
import com.todo.To_Do.Entity.User;
import com.todo.To_Do.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserServices {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<List<User>> getAll(){
        try{
            Iterable<User> iterable = userRepo.findAll();
            List<User> list = new ArrayList<>();
            for(User user : iterable){
                list.add(user);
            }
            return new ResponseEntity<>( list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    public ResponseEntity<?> saveNewUser(User user){
        try{
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            user.setRoles(Arrays.asList(Roles.USER));
            userRepo.save(user);
            return new ResponseEntity<>(user , HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    public ResponseEntity<?> delete(String userName){
        try{
            User user = userRepo.findByUserName(userName);
            if(user != null){
                userRepo.deleteByUserName(userName);
                return new ResponseEntity<>(user , HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> getByUserName(String userName){
        User user= userRepo.findByUserName(userName);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<User> update(String userName, User user){
       try{
           User oldUser = userRepo.findByUserName(userName);
           if(oldUser != null){
               if(!Objects.equals(oldUser.getUserName(), user.getUserName())){
                   oldUser.setUserName(user.getUserName());
               }
               if(!Objects.equals(oldUser.getUserPassword(), user.getUserPassword())){
                   oldUser.setUserPassword(user.getUserPassword());
               }
               oldUser.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
               userRepo.save(oldUser);
               return new ResponseEntity<>(oldUser,HttpStatus.OK);
           }
           //ok so i am not creating a new user when it isnt present because it could lead to some problem, the user might think he update but instead it created
           else{
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }

       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }
    // save user without encoding the encoded password again
    public ResponseEntity<User> saveUser(User user){
        try{
            userRepo.save(user);
            return new ResponseEntity<>(user , HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    //add an admin
    public ResponseEntity<User> saveAdmin(User user){
        try{
            user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
            user.setRoles(Arrays.asList(Roles.USER, Roles.ADMIN));
            userRepo.save(user);
            return new ResponseEntity<>(user , HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<User> promoteToAdmin(String userName){
        try{
            User oldUser = userRepo.findByUserName(userName);
            if(oldUser != null){
                if(!oldUser.getRoles().contains(Roles.ADMIN)){
                  oldUser.getRoles().add(Roles.ADMIN);
                }
                userRepo.save(oldUser);
                return new ResponseEntity<>(oldUser,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<User> demoteFromAdmin(String userName){
        User oldUser = userRepo.findByUserName(userName);
        if(oldUser!=null){
            List<Roles> roles = oldUser.getRoles();
            if(oldUser.getRoles().contains(Roles.ADMIN) && roles != null){
                oldUser.getRoles().remove(Roles.ADMIN);
                userRepo.save(oldUser);
                return new ResponseEntity<>(oldUser,HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
