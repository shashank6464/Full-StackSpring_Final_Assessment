package com.signup.service.signupService.repository;

import com.signup.service.signupService.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface UserRepository extends MongoRepository<User, ObjectId> {

    @Query("{username:\"?0\"}")
    List<User> loginUser(String username);

}
