package com.example.grocerystore.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class ProdRestColler {
    @PostMapping("/passingcheckboxvalue")
    public String handleFormSubmit(@RequestParam(name = "hobby", required = false) List<String> hobbies) {
        for (String hobby : hobbies) {
            System.out.println(hobby);
        }
        // 对hobbies进行处理
        return "result=>see sout result.";
    }

    @PostMapping("/selectoptiondropdownmen")
    public String selectOptionSubmit(@RequestParam("cars") String car) {
        System.out.println(car);
        // 对hobbies进行处理
        return "result=>see sout result.";
    }
    @PostMapping("deleteselectedproductions_test")
    public String deleteselectedproductions_test(@RequestParam (name="checkbox_delete",required=false) List<String> check_deleted){

        for (String s : check_deleted) {
            System.out.println(s);
        }
        return "result => see sout";
    }
}
