package com.example.spring_quiz.controller;

import com.example.spring_quiz.models.User;
import com.example.spring_quiz.service.UserService;
import com.example.spring_quiz.utilities.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<BaseResponse<User>> registerUser(@RequestBody User user){
        BaseResponse<User> baseResponse = new BaseResponse<>();
        baseResponse.setData(userService.registerUser(user));
        baseResponse.setMessage("User registration successful");
        baseResponse.setStatus(HttpStatus.CREATED.value());
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<BaseResponse<User>> login(@RequestParam String email, @RequestParam String password){
        User user = userService.login(email, password);
        BaseResponse<User> baseResponse = new BaseResponse<>();
        baseResponse.setData(user);
        if(user == null){
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage("Incorrect credentials");
        } else {
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Login successful");
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/play")
    public ResponseEntity<BaseResponse<User>> validateAnswer(@RequestParam int user_id, @RequestParam int question_id, @RequestParam String answer){
        User user = userService.validateAnswer(user_id, question_id, answer);
        BaseResponse<User> baseResponse = new BaseResponse<>();
        baseResponse.setData(user);
        if(question_id == 10){
            baseResponse.setMessage("Thank you for participating!!! score: " + user.getCurrent_score());
            baseResponse.setStatus(HttpStatus.OK.value());
        } else {
            baseResponse.setMessage("Update score: " + user.getCurrent_score());
            baseResponse.setStatus(HttpStatus.OK.value());
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/dashboard")
    public ResponseEntity<BaseResponse<List<User>>> topUsers(){
        BaseResponse<List<User>> baseResponse = new BaseResponse<>();
        baseResponse.setData(userService.topUsers());
        baseResponse.setMessage("List of top users");
        baseResponse.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
