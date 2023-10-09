package com.example.exam_module_4.controller;

import com.example.exam_module_4.dto.ProductDto;
import com.example.exam_module_4.entity.Product;
import com.example.exam_module_4.repository.CategoryRepository;
import com.example.exam_module_4.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequestMapping("product")
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping("list")
    public String showList(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sort") Optional<String> sort) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);
        String sortField = sort.orElse("name");
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by(sortField).ascending());
        Page<Product> products = productService.findAll(pageable);
        model.addAttribute("products", products);
        int totalPage = products.getTotalPages();
        if (totalPage > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "/list";
    }
    @GetMapping("create")
    public String showCreate(Model model){
        model.addAttribute("product",new ProductDto());
        model.addAttribute("categories",categoryRepository.findAll());
        return "/create";
    }
    @GetMapping("edit")
    public String doEdit(@RequestParam("id") Long id,Model model){
        model.addAttribute("product",productService.findById(id));
        model.addAttribute("categories",categoryRepository.findAll());
        return "update";
    }
    @PostMapping("edit")
    public String doUpdate(@Validated  @ModelAttribute("product") ProductDto productDto, @RequestParam("id") Long id,BindingResult bindingResult,Model model){
        new ProductDto().validate(productDto, bindingResult);
//        if(bindingResult.hasErrors()){
//            model.addAttribute("categories",categoryRepository.findAll());
//            return "/create";
//        }
        Product product = productService.findById(id);
        BeanUtils.copyProperties(productDto, product);
        productService.edit(product);
        return "redirect:list";
    }
    @PostMapping("create")
    public String doCreate(@Validated  @ModelAttribute("product") ProductDto productDto, BindingResult bindingResult,Model model){
        new ProductDto().validate(productDto, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("categories",categoryRepository.findAll());
            return "/create";
        }
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        productService.add(product);
        return "redirect:list";
    }
}
