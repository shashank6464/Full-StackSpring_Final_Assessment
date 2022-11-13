package com.signup.service.signupService.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "admin_table")
public class User {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private String username;

    private String password;

    private String password_confirm;

    private String email;

    public User(ObjectId id, String username, String password, String password_confirm) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.password_confirm = password_confirm;
    }

    public User(String username, String password, String password_confirm, String email) {
        this.username = username;
        this.password = password;
        this.password_confirm = password_confirm;
        this.email = email;
    }

    public User(String username, String password, String password_confirm) {
        this.username = username;
        this.password = password;
        this.password_confirm = password_confirm;
    }

}
