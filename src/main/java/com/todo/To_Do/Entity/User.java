package com.todo.To_Do.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data // automatically uses lombok to create getters and setters
@NoArgsConstructor // because @data adds an argsConstructor
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private String userPassword;

    @DBRef
    private List<ToDo> toDoList = new ArrayList<>();

    List<Roles> roles;

}
