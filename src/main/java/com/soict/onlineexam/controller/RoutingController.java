package com.ngovangiang.onlineexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoutingController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/student-home")
    public String getStudentHome() {
        return "student/home";
    }

    @GetMapping("/exam-info")
    public String getStudentExamInfo() {
        return "student/exam-info";
    }

    @GetMapping("/exam-question")
    public String getStudentExamQuestions() {
        return "student/exam-question";
    }

    @GetMapping("/result")
    public String getStudentResult() {
        return "student/result";
    }

    @GetMapping("/index")
    public String getTeacherHome() {
        return "teacher/index";
    }

    @GetMapping("/add-question")
    public String getTeacherAddQuestionsForm() {
        return "teacher/add-question";
    }

    @GetMapping("/add-topic")
    public String getAddTopicForm() {
        return "teacher/add-topic";
    }

    @GetMapping("/class")
    public String getTeacherClasses() {
        return "teacher/class";
    }

    @GetMapping("/class-list")
    public String getTeacherClassesList() {
        return "teacher/class-list";
    }

    @GetMapping("/exam")
    public String getTeacherExams() {
        return "teacher/exam";
    }

    @GetMapping("/question-list")
    public String getTeacherQuestionsList() {
        return "teacher/question-list";
    }

    @GetMapping("/subject")
    public String getTeacherSubjects() {
        return "teacher/subject";
    }
}
