package dev.technotrove.TechnoTrove.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.technotrove.TechnoTrove.services.productInfo;
import dev.technotrove.TechnoTrove.services.productService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/products")
public class productController {

    @Autowired
    productService prodSer;
    
    @GetMapping("{prod_var_id}")
    public productInfo getProdInfo(@PathVariable Long prod_var_id) {
        return prodSer.get_prod_info(prod_var_id);
    }
    
}
