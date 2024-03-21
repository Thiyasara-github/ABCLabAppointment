package com.ABCLab.ABCLab.Repository;

import com.ABCLab.ABCLab.Model.Payment;
import com.ABCLab.ABCLab.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findFirstByOrderByIdDesc();
}
