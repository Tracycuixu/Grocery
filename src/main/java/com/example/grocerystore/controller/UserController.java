package com.example.grocerystore.controller;
import jakarta.servlet.http.HttpSession;
import com.example.grocerystore.entity.UserEntity;
import com.example.grocerystore.model.User;
import com.example.grocerystore.repository.UserRepository;
import com.example.grocerystore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {

        this.userService = userService;
        this.userRepository = userRepository;

    }

    @GetMapping("/")
    public String listClients(Model theModel){
        List<User> theUsers = userService.getAllUsers();
        theModel.addAttribute("users", theUsers);
        return "users/list-users";
    }

    @GetMapping("/ClientLogin")
    public String showClientLoginForm() {
        return "shopping/ClientLogin";
    }
    @PostMapping("/ClientLogin")
    public String AfterClientLogin(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user.get());
            session.setAttribute("userId", user.get().getId());
            model.addAttribute("loggedInUsername", user.get().getUsername());
            return "redirect:/products";
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "shopping/ClientLogin";
        }
    }

    @GetMapping("/list")
    public String listUsers(Model theModel, HttpSession session){
        List<User> theUsers = userService.getAllUsers();
        theModel.addAttribute("users", theUsers);
        Object loggedInAdmin = session.getAttribute("loggedInAdmin");
        theModel.addAttribute("loggedInAdmin", loggedInAdmin);

        return "listallCustomers";
    }
        @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model, HttpSession session) {
        UserEntity user = new UserEntity();
        model.addAttribute("user", user);
//        model.addAttribute("loggedInUsername", loggedInUsername);
            Object loggedInAdmin = session.getAttribute("loggedInAdmin");
            model.addAttribute("loggedInAdmin", loggedInAdmin);
        return "users/user-form";
    }


    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("userId") Long userId,Model model) {
// UserEntity user = userService.findById(userId);
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        return "users/user-form";
    }

    @PostMapping("/upsert")
    public String upsertUser(@Valid @ModelAttribute("user") User theUser, BindingResult result){
        if (result.hasErrors()) {
            return "users/user-form";
        }
        userService.save(theUser);
        return "redirect:/user/list";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("userId") Long theId){
//delete the user
        userService.deleteUser(theId);
        return "redirect:/user/list";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "users/user-login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user.get());
            return "redirect:/user/list";
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "/users/user-login";
        }
    }
    @ModelAttribute("loggedInUser")
    public UserEntity getLoggedInUser(HttpSession session) {
        return (UserEntity) session.getAttribute("loggedInUser");
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        return "redirect:/user/login";
    }

}