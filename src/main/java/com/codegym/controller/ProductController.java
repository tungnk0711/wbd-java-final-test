package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.Product;
import com.codegym.service.CategoryService;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/products")
    public ModelAndView listProducts() {

        Iterable<Product> productList = productService.findAll();

        ModelAndView modelAndView = new ModelAndView("/product/list", "products", productList);
        return modelAndView;
    }

    @GetMapping("/search-product")
    public ModelAndView searchProducts(@RequestParam("product-name") String name) {

        List<Product> productList = productService.findByNameContaining(name);
        ModelAndView modelAndView = new ModelAndView("/product/search");
        modelAndView.addObject("products", productList);
        return modelAndView;
    }

    @GetMapping("/create-product")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());

        return modelAndView;
    }

    @RequestMapping(value = "/save-product", method = RequestMethod.POST)
    public ModelAndView saveProduct(@ModelAttribute("product") Product product, BindingResult result) {

        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        Product productObject = new Product(LocalDate.now(), product.getName(), product.getPrice(), product.getQuantity(), product.getDescription(), product.getCategory());
        productService.save(productObject);

        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("message", "New product created successfully");
        return modelAndView;
    }

    @GetMapping("/edit-product/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product != null) {
            ModelAndView modelAndView = new ModelAndView("/product/edit");
            modelAndView.addObject("product", product);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/product/error");
            return modelAndView;
        }
    }

    @PostMapping("/edit-product")
    public ModelAndView editProduct(@ModelAttribute("product") Product product, BindingResult result) {

        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        productService.save(product);

        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("message", "Product edited successfully");
        return modelAndView;

    }

    @GetMapping("/delete-product/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {

        Product product = productService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/product/delete");
        modelAndView.addObject("product", product);
        return modelAndView;

    }

    @GetMapping("/remove-product")
    public ModelAndView removeProduct(@RequestParam("id") Long id) {

        productService.remove(id);

        Product product = productService.findById(id);

        ModelAndView modelAndView = new ModelAndView("/product/delete");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

}
