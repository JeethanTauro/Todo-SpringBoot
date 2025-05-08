package com.todo.To_Do.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "todo_entries")
@Data
@NoArgsConstructor
public class ToDo {
    @Id
    private String id;

    private String title;
    private String content;

}
