package com.example.art_ordering.controller;

import com.example.art_ordering.entity.Role;
import com.example.art_ordering.entity.User;
import com.example.art_ordering.global.GlobalData;
import com.example.art_ordering.repository.RoleRepository;
import com.example.art_ordering.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/login")
    public String login() {
        GlobalData.cart.clear();
        return "login";
    }

    @GetMapping("/register")
    public String registerGet(Model model) {
        model.addAttribute("user", new User()); // Add an empty User object to the model for form binding
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));

        // Check if the Role with ID 2 exists, if not, create a new Role
        Role role = roleRepository.findById(2).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setId(2);
            newRole.setName("ROLE_USER");
            return roleRepository.save(newRole);
        });

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user); // Save the user object directly
        request.login(user.getEmail(), password);
        return "redirect:/";
    }
}
