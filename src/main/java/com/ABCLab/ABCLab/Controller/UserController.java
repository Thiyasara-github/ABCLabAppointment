package com.ABCLab.ABCLab.Controller;

import com.ABCLab.ABCLab.Model.LabService;
import com.ABCLab.ABCLab.Model.User;
import com.ABCLab.ABCLab.Repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
//@CrossOrigin(origins = "http://localhost:8081")
public class UserController {

    private final UserRepo userRepository;

    @Autowired
    public UserController(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/addusers")
    public User addUser(@RequestBody User user) {
        // Retrieve the maximum ID from the database
        Optional<User> maxIdUser = userRepository.findFirstByOrderByIdDesc();
        String maxId = maxIdUser.map(User::getId).orElse("0");

        // Increment the maximum ID by one to generate a new ID
        int newId = Integer.parseInt(maxId) + 1;
        user.setId(String.valueOf(newId));

        // Save the user with the generated ID
        return userRepository.save(user);
    }

    @GetMapping("/getusers")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> labServices = userRepository.findAll(); // Change UserRepo to userRepo

        if(labServices.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(labServices, HttpStatus.OK);
    }

    @PutMapping("/updateuser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User newUserDetails) {
        // Check if the user with the given id exists in the database
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            // Update user details
            existingUser.setName(newUserDetails.getName());
            existingUser.setEmail(newUserDetails.getEmail());
            existingUser.setPassword(newUserDetails.getPassword());
            existingUser.setUtype(newUserDetails.getUtype());

            // Save the updated user
            User updatedUser = userRepository.save(existingUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
