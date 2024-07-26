package com.example.spring_quiz.service;

import com.example.spring_quiz.dao.QuestionRepository;
import com.example.spring_quiz.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepo;

    public List<Question> addQuestions(List<Question> questions){
        return questionRepo.saveAll(questions);
    }

    public Optional<Question> getQuestionById(int id){
        return questionRepo.findById(id);
    }
}
