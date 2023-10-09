package com.example.exam_module_4.dto;

import com.example.exam_module_4.entity.Category;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotBlank;

public class ProductDto implements Validator {
    @NotBlank(message = "Ten khong duoc rong")

    private String name;
    @NotBlank(message = "Gia khong duoc rong")

    private String startPrice;
    private Category category;

    public ProductDto(String name, String startPrice, Category category) {
        this.name = name;
        this.startPrice = startPrice;
        this.category = category;
    }

    public ProductDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public Category getCategoryDto() {
        return category;
    }

    public void setCategoryDto(Category category) {
        this.category = category;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!(target instanceof ProductDto)) {
            return;
        }
        ProductDto productDto = (ProductDto) target;
        String name = productDto.getName();
        if(name.isEmpty()){
            errors.rejectValue("name","check.empty.name");
        } else if (!(name.length()>=5&&name.length()<=50)) {
            errors.rejectValue("name","check.length.name");
        }
    }
}
