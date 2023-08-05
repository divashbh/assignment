package com.example.art_ordering.controller;


import com.example.art_ordering.global.GlobalData;
import com.example.art_ordering.service.CategoryService;
import com.example.art_ordering.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller

public class HomeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping({"/","/home"})
    public  String home(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "index";
    }
    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("category", categoryService.getAllCategory());
        model.addAttribute("product", productService.getAllProduct());
        model.addAttribute("cartCount",GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable int id) {
        model.addAttribute("category", categoryService.getAllCategory());
        model.addAttribute("product", productService.getAllProductsByCategory(id));
        model.addAttribute("cartCount",GlobalData.cart.size());
        return "shop";
    }
    @GetMapping("/shop/viewProduct/{id}")
    public String viewProduct(Model model, @PathVariable int id) {
        model.addAttribute("product", productService.getProductById(id).get());
        model.addAttribute("cartCount",GlobalData.cart.size());
        return "viewProduct";
    }



}
