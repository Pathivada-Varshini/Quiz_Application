package com.example.spring_quiz.service;

import com.example.spring_quiz.dao.UserRepository;
import com.example.spring_quiz.models.User;
import com.example.spring_quiz.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    QuestionService questionService;

    public User registerUser(User user){
        return userRepo.save(user);
    }

    public User login(String email, String password){
        User user = userRepo.findByEmail(email);
        if(user != null && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }

    public User getUserById(int id){
        Optional<User> user = userRepo.findById(id);
        return user.orElse(null);
    }

    public User validateAnswer(int user_id, int question_id, String answer){
        User user = getUserById(user_id);
        if (user == null) {
            return null;
        }

        Question question = questionService.getQuestionById(question_id).orElse(null);
        if (question == null) {
            return null;
        }

        if(question.getAnswer().equals(answer)){
            user.setCurrent_score(user.getCurrent_score() + 1);
            userRepo.save(user);
        }

        if(question_id == 10){
            if(user.getCurrent_score() > user.getHighScore()){
                user.setHighScore(user.getCurrent_score());
            }
            user.setCurrent_score(0);
            userRepo.save(user);
        }

        return user;
    }

    public List<User> topUsers(){
        List<User> users = userRepo.findAll();
        users.sort((u1, u2) -> Integer.compare(u2.getHighScore(), u1.getHighScore()));
        if(users.size() > 3){
            return users.subList(0, 3);
        }
        return users;
    }
}
