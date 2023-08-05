package com.example.art_ordering.controller;
import com.example.art_ordering.dto.productDTO;
import com.example.art_ordering.entity.Category;
import com.example.art_ordering.entity.Product;
import com.example.art_ordering.service.CategoryService;
import com.example.art_ordering.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {
    public  String uploadDir=System.getProperty("user.dir")+ "/src/main/resources/static/productImages";
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @GetMapping("/admin")
    public String adminHome(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCat(Model model){
        model.addAttribute("categories",categoryService.getAllCategory());
        return "categories";
    }
    @GetMapping("/admin/categories/add")
    public  String addCate(Model model){
        model.addAttribute("categories",new Category());
        return "categoriesAdd";
    }
    @PostMapping ("/admin/categories/add")
    public  String postCate(@ModelAttribute("categories")Category category){
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @PostMapping("/admin/categories/delete/{id}")
    public String deleteCate(@PathVariable int id) {
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }


    @GetMapping("/admin/categories/update/{id}")
    public String updateCate(@PathVariable int id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("categories", category.get());
            return "categoriesAdd";
        } else {
            return "404";
        }
    }

    //    -------------------------------Product Section-------------------------------
    @GetMapping ("/admin/product")
    public String getProdducts(Model model) {
        model.addAttribute("products",productService.getAllProduct());
        return "product";
    }
    @GetMapping ("/admin/product/add")
    public String addProducts(Model model) {
        model.addAttribute("productDTO",new productDTO());
        model.addAttribute("categories",categoryService.getAllCategory());
        return "productAdd";
    }
    @PostMapping("/admin/product/add")
    public String productAddPost(@ModelAttribute("ProductDTO")productDTO ProductDTO,
                                 @RequestParam("productImage")MultipartFile file,
                                 @RequestParam("imgName")String imgName)throws IOException {
        Product product=new Product();
        product.setId(ProductDTO.getId());
        product.setName(ProductDTO.getName());
        product.setCategory(categoryService.getCategoryById(ProductDTO.getCategoryId()).get());
        product.setPrice(ProductDTO.getPrice());
        product.setDescription(ProductDTO.getDescription());
        String imageUUID;
        if (!file.isEmpty()){
            imageUUID=file.getOriginalFilename();
            Path fileAndPath= Paths.get(uploadDir,imageUUID);
            Files.write(fileAndPath,file.getBytes());
        }else {
            imageUUID=imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);


        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.removeProudctById(id);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProductGet(@PathVariable Long id,Model model){
        Product product=productService.getProductById(id).get();
        productDTO productDTO=new productDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrice());;
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());
        model.addAttribute("categories",categoryService.getAllCategory());
        model.addAttribute("productDTO",productDTO);

        return "productAdd";
    }

}
