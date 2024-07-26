package com.example.spring_quiz.controller;

import com.example.spring_quiz.models.Question;
import com.example.spring_quiz.service.QuestionService;
import com.example.spring_quiz.utilities.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @PostMapping
    public ResponseEntity<BaseResponse<List<Question>>> addQuestions(@RequestBody List<Question> questions) {
        BaseResponse<List<Question>> response = new BaseResponse<>();
        response.setData(questionService.addQuestions(questions));
        response.setMessage("Questions added successfully");
        response.setStatus(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Question>> getQuestionById(@PathVariable int id){
        BaseResponse<Question> response = new BaseResponse<>();
        Optional<Question> question = questionService.getQuestionById(id);
        if (question.isPresent()) {
            response.setData(question.get());
            response.setMessage("Question with id: " + id + " fetched successfully");
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setMessage("Question with id: " + id + " not found");
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
        return new ResponseEntity<>(response, response.getStatus() == HttpStatus.OK.value() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
